import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.assertj.KafkaConditions.partition;
import static org.springframework.kafka.test.assertj.KafkaConditions.timestamp;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.consensus.kafka.KafkaProducerImpl;
import com.consensus.utils.domain.ConsensusIteration;
import com.consensus.utils.serializer.ConsensusIterationSerializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.ProducerListenerAdapter;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaProducerImplTest {
    private static final String INT_KEY_TOPIC = "intKeyTopic";

    private static final String STRING_KEY_TOPIC = "stringKeyTopic";

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, INT_KEY_TOPIC, STRING_KEY_TOPIC);

    private static Consumer<Integer, String> consumer;

    @BeforeClass
    public static void setUp() throws Exception {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false", embeddedKafka);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, INT_KEY_TOPIC);
    }

    @AfterClass
    public static void tearDown() {
        consumer.close();
    }



    @Test
    public void testSendSingleRecord() throws Exception {
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        DefaultKafkaProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf, true);

        template.setDefaultTopic(INT_KEY_TOPIC);

        template.sendDefault("foo");
        assertThat(KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC)).has(value("foo"));

        Map<MetricName, ? extends Metric> metrics = template.execute(Producer::metrics);
        assertThat(metrics).isNotNull();
        metrics = template.metrics();
        assertThat(metrics).isNotNull();
        List<PartitionInfo> partitions = template.partitionsFor(INT_KEY_TOPIC);
        assertThat(partitions).isNotNull();
        assertThat(partitions).hasSize(2);
        pf.destroy();

    }
    @Test
    public void testSendSingleConsensusIterationRecord() throws Exception {


        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        senderProps.put("key.serializer",ConsensusIterationSerializer.class.getName());
        senderProps.put("value.serializer",ConsensusIterationSerializer.class.getName());

        DefaultKafkaProducerFactory<Integer,ConsensusIteration> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer,ConsensusIteration> template = new KafkaTemplate<>(pf);


        template.setDefaultTopic(INT_KEY_TOPIC);

        ConsensusIteration iteration = new ConsensusIteration();
        iteration.id = "id1";
        iteration.name = "name1";
        iteration.setMessage("message1");
        iteration.setCount(1);

        ConsensusIteration iteration2 = new ConsensusIteration();
        iteration2.id = "id2";
        iteration2.name = "name2";
        iteration2.setMessage("message2");
        iteration2.setCount(2);

        template.sendDefault(iteration);
        ConsumerRecord<?,?> record = KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC);

        assertThat(record.value().toString().contains("name2")).isNotEqualTo(true);
        assertThat(record.value().toString().contains("message1")).isEqualTo(true);
        assertThat(record.value().toString().contains("id1")).isEqualTo(true);

        template.sendDefault(iteration2);
        ConsumerRecord<?,?> record2 = KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC);

        assertThat(record2.value().toString().contains("message2")).isEqualTo(true);
        assertThat(record2.value().toString().contains("id2"));
        assertThat(record2.value().toString().contains("id1")).isEqualTo(false);


        pf.destroy();

    }
    @Test
    public void testKafkaProducerImpl(){



        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        senderProps.put("key.serializer",ConsensusIterationSerializer.class.getName());
        senderProps.put("value.serializer",ConsensusIterationSerializer.class.getName());

        DefaultKafkaProducerFactory<Integer,ConsensusIteration> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer,ConsensusIteration> template = new KafkaTemplate<>(pf);

        KafkaProducerImpl producer =
                spy(new KafkaProducerImpl(template,Set.of("topic1")));


        producer.sendMessage(new ConsensusIteration());

        Mockito.verify(producer, Mockito.times(1)).sendMessage(any(ConsensusIteration.class));


    }


    @Test
    public void testTemplate() throws Exception {
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        DefaultKafkaProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf, true);

        template.setDefaultTopic(INT_KEY_TOPIC);

        template.sendDefault("foo");
        assertThat(KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC)).has(value("foo"));

        template.sendDefault(0, 2, "bar");
        ConsumerRecord<Integer, String> received = KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC);
        assertThat(received).has(key(2));
        assertThat(received).has(partition(0));
        assertThat(received).has(value("bar"));

        template.send(INT_KEY_TOPIC, 0, 2, "baz");
        received = KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC);
        assertThat(received).has(key(2));
        assertThat(received).has(partition(0));
        assertThat(received).has(value("baz"));

        template.send(INT_KEY_TOPIC, 0, null, "qux");
        received = KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC);
        assertThat(received).has(key((Integer) null));
        assertThat(received).has(partition(0));
        assertThat(received).has(value("qux"));

        template.send(MessageBuilder.withPayload("fiz")
                .setHeader(KafkaHeaders.TOPIC, INT_KEY_TOPIC)
                .setHeader(KafkaHeaders.PARTITION_ID, 0)
                .setHeader(KafkaHeaders.MESSAGE_KEY, 2)
                .build());
        received = KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC);
        assertThat(received).has(key(2));
        assertThat(received).has(partition(0));
        assertThat(received).has(value("fiz"));

        template.send(MessageBuilder.withPayload("buz")
                .setHeader(KafkaHeaders.PARTITION_ID, 0)
                .setHeader(KafkaHeaders.MESSAGE_KEY, 2)
                .build());
        received = KafkaTestUtils.getSingleRecord(consumer, INT_KEY_TOPIC);
        assertThat(received).has(key(2));
        assertThat(received).has(partition(0));
        assertThat(received).has(value("buz"));

    }
}
