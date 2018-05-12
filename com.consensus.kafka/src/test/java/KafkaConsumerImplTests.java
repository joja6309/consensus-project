import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.spy;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;


import com.consensus.kafka.KafkaConsumerContainerImpl;
import com.consensus.kafka.KafkaProducerImpl;
import com.consensus.kafka.configuration.KafkaConfiguration;
import com.consensus.utils.base.CmdLineParser;
import com.consensus.utils.domain.ConsensusIteration;
import com.consensus.utils.serializer.ConsensusIterationDeserializer;
import com.consensus.utils.serializer.ConsensusIterationSerializer;

import kafka.common.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.FileAppender;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


import org.mockito.Mockito;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaConsumerImplTests {




    private static final Logger LOGGER = Logger.getLogger(KafkaConsumerImplTests.class.getName());


    public void doSomething() {
        LOGGER.info("something happened");
    }
    static{
        LOGGER.setUseParentHandlers(false);
        final CustomRecordFormatter formatter = new CustomRecordFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
    }
//    @Before
//    public void setup(){
//        LOGGER.setUseParentHandlers(false);
//        CustomRecordFormatter formatter = new CustomRecordFormatter();
//        ConsoleHandler consoleHandler = new ConsoleHandler();
//        consoleHandler.setFormatter(formatter);
//        LOGGER.addHandler(consoleHandler);
//
//    }



    private static String topic1 = "testTopic1";

    private static String topic2 = "testTopic2";

    private static String topic3 = "testTopic3";

    private static String topic4 = "testTopic4";

    private static String topic5 = "testTopic5";

    private static String topic6 = "testTopic6";

    private static String topic7 = "testTopic7";

    private static String topic8 = "testTopic8";

    private static String topic9 = "testTopic9";

    private static String topic10 = "testTopic10";

    private static String topic11 = "testTopic11";

    private static String topic12 = "testTopic12";

    private static String topic13 = "testTopic13";

    private static String topic14 = "testTopic14";

    private static String topic15 = "testTopic15";

    private static String topic16 = "testTopic16";

    private static String topic17 = "testTopic17";

    private static String topic18 = "testTopic18";

    private static String topic19 = "testTopic19";




    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, topic1, topic2, topic3, topic4, topic5,
            topic6, topic7, topic8, topic9, topic10, topic11, topic12, topic13, topic14, topic15, topic16, topic17,
            topic18, topic19);
    @ClassRule
    public static KafkaEmbedded embeddedKafka2Partitions = new KafkaEmbedded(2, true, topic1, topic2, topic3, topic4, topic5,
            topic6, topic7, topic8, topic9, topic10, topic11, topic12, topic13, topic14, topic15, topic16, topic17,
            topic18, topic19);
    @ClassRule
    public static KafkaEmbedded embeddedKafka3;

    public void sendTestMessage(String topic,ConsensusIteration iteration){

        KafkaTemplate template = getKafkaTemplate(ConsensusIterationSerializer.class.getName(),ConsensusIterationSerializer.class.getName());
        template.setDefaultTopic(topic);
        //template.sendDefault(iteration);

        ListenableFuture<SendResult<String, String>> future = template.sendDefault(iteration);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOGGER.info("success");
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("failed");
            }
        });
    }
//    public void send(String topic, LifecycleEvent message) {
//        // the KafkaTemplate provides asynchronous send methods returning a Future
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);
//
//        // register a callback with the listener to receive the result of the send asynchronously
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                LOGGER.info("sent message='{}' with offset={}", message,
//                        result.getRecordMetadata().offset());
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                LOGGER.error("unable to send message='{}'", message, ex);
//            }
//        });
//    }


    public KafkaTemplate<Integer,ConsensusIteration> getKafkaTemplate(String keySerializer, String valueSerializer){
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        senderProps.put("key.serializer",keySerializer);
        senderProps.put("value.serializer",valueSerializer);
        DefaultKafkaProducerFactory<Integer,ConsensusIteration> pf = new DefaultKafkaProducerFactory<>(senderProps);
        return new KafkaTemplate<>(pf);
    }
    @Test
    public void testAutoCommit() throws Exception {
//        this.logger.info("Start auto");
        LOGGER.info("Start auto");
        Map<String, Object> props = KafkaTestUtils.consumerProps("test1", "true", embeddedKafka);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProps = new ContainerProperties(topic1);
        containerProps.setLogContainerConfig(true);

        final CountDownLatch latch = new CountDownLatch(4);
        final Set<String> listenerThreadNames = new ConcurrentSkipListSet<>();
        containerProps.setMessageListener((MessageListener<Integer, String>) message -> {
            LOGGER.info(("auto: " + message));
            listenerThreadNames.add(Thread.currentThread().getName());
            latch.countDown();
        });

        ConcurrentMessageListenerContainer<Integer, String> container =
                new ConcurrentMessageListenerContainer<>(cf, containerProps);
        container.setConcurrency(2);
        container.setBeanName("testAuto");
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
        assertThat(container.getAssignedPartitions()).hasSize(2);

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic1);
        template.sendDefault(0, "foo");
        template.sendDefault(2, "bar");
        template.sendDefault(0, "baz");
        template.sendDefault(2, "qux");
        template.flush();
        assertThat(latch.await(60, TimeUnit.SECONDS)).isTrue();
        for (String threadName : listenerThreadNames) {
            assertThat(threadName).contains("-C-");
        }
        @SuppressWarnings("unchecked")
        List<KafkaMessageListenerContainer<Integer, String>> containers = KafkaTestUtils.getPropertyValue(container,
                "containers", List.class);
        assertThat(containers).hasSize(2);
        for (int i = 0; i < 2; i++) {
            assertThat(KafkaTestUtils.getPropertyValue(containers.get(i), "listenerConsumer.acks", Collection.class)
                    .size()).isEqualTo(0);
        }
        assertThat(container.metrics()).isNotNull();
        container.stop();
        LOGGER.info("Stop auto");

//        this.logger.info("Stop auto");
    }
    @Test
    public void testAfterListenCommit() throws Exception {
        LOGGER.info("Start manual");
        Map<String, Object> props = KafkaTestUtils.consumerProps("test2", "false", embeddedKafka);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProps = new ContainerProperties(topic2);

        final CountDownLatch latch = new CountDownLatch(4);
        containerProps.setMessageListener((MessageListener<Integer, String>) message -> {
            LOGGER.info("manual: " + message);
            latch.countDown();
        });

        ConcurrentMessageListenerContainer<Integer, String> container =
                new ConcurrentMessageListenerContainer<>(cf, containerProps);
        container.setConcurrency(2);
        container.setBeanName("testBatch");
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic2);
        template.sendDefault(0, "foo");
        template.sendDefault(2, "bar");
        template.sendDefault(0, "baz");
        template.sendDefault(2, "qux");
        template.flush();
        assertThat(latch.await(60, TimeUnit.SECONDS)).isTrue();
        container.stop();
        LOGGER.info("Stop manual");

    }


    @Test
    public void testKafkaConsumerImpl(){

        String consumerArgs = "-Ctopics=topic1 -Ptopics=topic2  -BootServer=0.0.0.0:9092 -GroupId=NodeGroup1";


        CmdLineParser parsedConsumerArgs = new CmdLineParser(consumerArgs.split(" "));


        HashMap<String,Object> consProps = new HashMap<String,Object>();


        consProps.put("key.deserializer",ConsensusIterationDeserializer.class.getName());
        consProps.put("value.deserializer",ConsensusIterationDeserializer.class.getName());
        KafkaTemplate template = getKafkaTemplate(ConsensusIterationSerializer.class.getName(),ConsensusIterationSerializer.class.getName());

        KafkaProducerImpl producer =
                spy(new KafkaProducerImpl(template,Set.of("topic2")));
        KafkaConsumerContainerImpl consumer =
                new KafkaConsumerContainerImpl(consProps,parsedConsumerArgs.consumerTopics, parsedConsumerArgs.bootServer,parsedConsumerArgs.groupId,producer,"containerName");
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

    }
    private KafkaMessageListenerContainer<Integer, String> spyOnContainer(KafkaMessageListenerContainer<Integer, String> container,
                                                                          final CountDownLatch stubbingComplete) {
        KafkaMessageListenerContainer<Integer, String> spy = spy(container);
        willAnswer(i -> {
            if (stubbingComplete.getCount() > 0 && Thread.currentThread().getName().endsWith("-C-1")) {
                try {
                    stubbingComplete.await(10, TimeUnit.SECONDS);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return i.callRealMethod();
        }).given(spy).isRunning();
        return spy;
    }
    @Test
    public void testMessageListenerContainer() throws Exception{
        Map<String, Object> props = KafkaTestUtils.consumerProps("lt1", "false", embeddedKafka);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProps = new ContainerProperties(topic4);

        final CountDownLatch latch = new CountDownLatch(1);
        final BitSet bits = new BitSet(1);
        containerProps.setMessageListener((MessageListener<Integer, String>) m -> {

            LOGGER.info("lt1");
            synchronized (bits) {
                bits.set(0);
            }
            latch.countDown();
        });

        KafkaMessageListenerContainer<Integer, String> container1 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container1.setBeanName("lt1");
        container1.start();

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        DefaultKafkaProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic4);
        template.sendDefault(0, 0, "foo");
        template.flush();
        pf.destroy();

        assertThat(latch.await(30, TimeUnit.SECONDS)).isTrue();
        assertThat(bits.cardinality()).isEqualTo(1);

        container1.stop();

    }

    @Test
    public void testMessageListenerContainerOnMessage() throws Exception{

        Map<String, Object> props = KafkaTestUtils.consumerProps("lt1", "false", embeddedKafka);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProps = new ContainerProperties(topic4);

        final CountDownLatch latch = new CountDownLatch(1);
        final List<String> hops = new ArrayList<>();

        containerProps.setMessageListener(new MessageListener<Integer, String>() {
               @Override
                public void onMessage(ConsumerRecord<Integer, String> record) {
                   try{
                       LOGGER.info("lt1");
                       hops.add("hop");
                       latch.countDown();
                       } catch (Exception e) {
                            System.out.print(e);
                       }
               }
            });

        KafkaMessageListenerContainer<Integer, String> container1 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container1.setBeanName("lt1");
        container1.start();

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        DefaultKafkaProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic4);
        template.sendDefault(0, 0, "foo");
        template.flush();
        pf.destroy();

        assertThat(latch.await(30, TimeUnit.SECONDS)).isTrue();
        assertThat(hops.size() == 1 ).isTrue();
        assertThat(hops.contains("hop")).isTrue();

        container1.stop();

    }
    private KafkaTemplate<Integer, String> createTemplate(Map<String,Object> props) {
        ProducerFactory<Integer, String> pf =
                new DefaultKafkaProducerFactory<Integer, String>(props);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        return template;
    }
    @Test
    public void testOnMessageConsensusIteration() throws Exception{

        Map<String, Object> props = KafkaTestUtils.consumerProps("lt1", "false", embeddedKafka);
        props.put("key.deserializer",ConsensusIterationDeserializer.class.getName());
        props.put("value.deserializer",ConsensusIterationDeserializer.class.getName());

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<Integer, ConsensusIteration> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProps = new ContainerProperties(topic4);


        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        senderProps.put("key.serializer",ConsensusIterationSerializer.class.getName());
        senderProps.put("value.deserializer",ConsensusIterationDeserializer.class.getName());

        KafkaTemplate template = getKafkaTemplate(ConsensusIterationSerializer.class.getName(),ConsensusIterationSerializer.class.getName());


        final CountDownLatch latch = new CountDownLatch(1);
        final List<ConsensusIteration> hops = new ArrayList<>();



        containerProps.setMessageListener(new MessageListener<Integer,ConsensusIteration>() {
            @Override
            public void onMessage(ConsumerRecord<Integer,ConsensusIteration> record) {
                try{
                    LOGGER.info("lt1");
                    hops.add(record.value());
                    latch.countDown();
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        });

        KafkaMessageListenerContainer<Integer,ConsensusIteration> container1 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container1.setBeanName("lt1");
        container1.start();

        ConsensusIteration iteration = new ConsensusIteration();
        iteration.setMessage("message");
        iteration.hops.add("producer1");
        iteration.count = 1;

        sendTestMessage(topic4,iteration);

        assertThat(latch.await(30, TimeUnit.SECONDS)).isTrue();
        assertThat(hops.size() == 1 ).isTrue();
        assertThat(hops.contains(iteration)).isFalse();

        container1.stop();

    }
    @Test
    public void testOnMessageKafkaConsumerContainerImpl() throws Exception{

        String group = "lt1";
        HashMap props = new HashMap();
        props.put("key.deserializer",ConsensusIterationDeserializer.class.getName());
        props.put("value.deserializer",ConsensusIterationDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        HashMap<String,Object> configurationProperties  = KafkaConfiguration.setProps(embeddedKafka.getBrokersAsString(), group,props);

        final CountDownLatch latch = new CountDownLatch(1);
        final List<ConsensusIteration> hops = new ArrayList<>();
        KafkaTemplate template = getKafkaTemplate(ConsensusIterationSerializer.class.getName(),ConsensusIterationSerializer.class.getName());

        KafkaProducerImpl producer =
                spy(new KafkaProducerImpl(template,Set.of("topic2")));


        KafkaConsumerContainerImpl containerImpl = spy(new KafkaConsumerContainerImpl(props,Set.of(topic4),embeddedKafka.getBrokersAsString(),group,producer,"containerName"));

        containerImpl.setMessageListener(new MessageListener<Integer,ConsensusIteration>() {
            @Override
            public void onMessage(ConsumerRecord<Integer,ConsensusIteration> record) {
                try{
                    LOGGER.info("lt1");
                    hops.add(record.value());
                    latch.countDown();

                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        },configurationProperties);



        containerImpl.run();

        ConsensusIteration iteration = new ConsensusIteration();
        iteration.setMessage("message");
        iteration.hops.add("producer1");
        iteration.count = 1;

        sendTestMessage(topic4,iteration);

        assertThat(latch.await(30, TimeUnit.SECONDS)).isTrue();
        assertThat(hops.size() == 1 ).isTrue();
        assertThat(hops.contains(iteration)).isTrue();

        containerImpl.stop();


    }
    @Test
    public void testSetMessageKafkaConsumerContainerImpl() throws Exception{

        String group = "lt1";
        HashMap props = new HashMap();
        props.put("key.deserializer",ConsensusIterationDeserializer.class.getName());
        props.put("value.deserializer",ConsensusIterationDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        HashMap<String,Object> configurationProperties  = KafkaConfiguration.setProps(embeddedKafka.getBrokersAsString(), group,props);

        final CountDownLatch latch = new CountDownLatch(1);
        final List<ConsensusIteration> hops = new ArrayList<>();


        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        senderProps.put("key.serializer",ConsensusIterationSerializer.class.getName());
        senderProps.put("value.serializer",ConsensusIterationSerializer.class.getName());

        DefaultKafkaProducerFactory<Integer,ConsensusIteration> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer,ConsensusIteration> template = new KafkaTemplate<>(pf);

        KafkaProducerImpl producer =
                spy(new KafkaProducerImpl(template,Set.of("topic1")));


        final KafkaConsumerContainerImpl containerImpl = spy(new KafkaConsumerContainerImpl(props,Set.of(topic4),embeddedKafka.getBrokersAsString(),group,producer,"containerName"));


        containerImpl.setMessageListener();
        containerImpl.run();

        ContainerTestUtils.waitForAssignment(containerImpl.getContainer(), embeddedKafka.getPartitionsPerTopic());


        ConsensusIteration iteration = new ConsensusIteration();
        iteration.setMessage("message");
        iteration.hops.add("producer1");
        iteration.count = 1;

        sendTestMessage(topic4,iteration);

        int messageCount = 0;
        while(messageCount < 1 ){
            messageCount = containerImpl.getMessages().size();
            Thread.sleep(10);
        }

        assertThat(iteration).isEqualToComparingFieldByField(iteration);
        Mockito.verify(producer, Mockito.times(1)).sendMessage(any(ConsensusIteration.class));
        containerImpl.stop();


    }
    public KafkaConsumerContainerImpl getKafkaConsumerContainerImpl(Set<String> topics, String group,KafkaProducerImpl producer,String containerName){

        HashMap props = new HashMap();
        props.put("key.deserializer",ConsensusIterationDeserializer.class.getName());
        props.put("value.deserializer",ConsensusIterationDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        HashMap<String,Object> configurationProperties  = KafkaConfiguration.setProps(embeddedKafka.getBrokersAsString(), group,props);
        KafkaConsumerContainerImpl container = new KafkaConsumerContainerImpl(props,topics,embeddedKafka.getBrokersAsString(),group,producer,containerName);
        container.setMessageListener();

        return  spy(container);

    }



    @Test
    public void testListenerTypes() throws Exception {
        Map<String, Object> props = KafkaTestUtils.consumerProps("lt1", "false", embeddedKafka);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProps = new ContainerProperties(topic4);

        final CountDownLatch latch = new CountDownLatch(8);
        final BitSet bits = new BitSet(8);
        containerProps.setMessageListener((MessageListener<Integer, String>) m -> {

            LOGGER.info("lt1");
            synchronized (bits) {
                bits.set(0);
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container1 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container1.setBeanName("lt1");
        container1.start();

        props.put("group.id", "lt2");
        cf = new DefaultKafkaConsumerFactory<>(props);
        containerProps.setMessageListener((AcknowledgingMessageListener<Integer, String>) (m, a) -> {
            LOGGER.info("lt2");

            synchronized (bits) {
                bits.set(1);
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container2 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container2.setBeanName("lt2");
        container2.start();

        props.put("group.id", "lt3");
        cf = new DefaultKafkaConsumerFactory<>(props);
        containerProps.setMessageListener((ConsumerAwareMessageListener<Integer, String>) (m, c) -> {
            LOGGER.info("lt3");
            synchronized (bits) {
                bits.set(2);
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container3 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container3.setBeanName("lt3");
        container3.start();

        props.put("group.id", "lt4");
        cf = new DefaultKafkaConsumerFactory<>(props);
        containerProps.setMessageListener((AcknowledgingConsumerAwareMessageListener<Integer, String>) (m, a, c) -> {
            LOGGER.info("lt4");
            synchronized (bits) {
                bits.set(3);
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container4 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container4.setBeanName("lt4");
        container4.start();

        props.put("group.id", "lt5");
        cf = new DefaultKafkaConsumerFactory<>(props);
        containerProps.setMessageListener((BatchMessageListener<Integer, String>) m -> {
            LOGGER.info("lt5");
            synchronized (bits) {
                bits.set(4);
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container5 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container5.setBeanName("lt5");
        container5.start();

        props.put("group.id", "lt6");
        cf = new DefaultKafkaConsumerFactory<>(props);
        containerProps.setMessageListener((BatchAcknowledgingMessageListener<Integer, String>) (m, a) -> {
            LOGGER.info("lt6");
            synchronized (bits) {
                bits.set(5);
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container6 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container6.setBeanName("lt6");
        container6.start();

        props.put("group.id", "lt7");
        cf = new DefaultKafkaConsumerFactory<>(props);
        containerProps.setMessageListener((BatchConsumerAwareMessageListener<Integer, String>) (m, c) -> {
            LOGGER.info("lt7");
            synchronized (bits) {
                bits.set(6);
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container7 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container7.setBeanName("lt7");
        container7.start();

        props.put("group.id", "lt8");
        cf = new DefaultKafkaConsumerFactory<>(props);
        containerProps
                .setMessageListener((BatchAcknowledgingConsumerAwareMessageListener<Integer, String>) (m, a, c) -> {
                    LOGGER.info("lt8");
                    synchronized (bits) {
                        bits.set(7);
                    }
                    latch.countDown();
                });
        KafkaMessageListenerContainer<Integer, String> container8 =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container8.setBeanName("lt8");
        container8.start();

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        DefaultKafkaProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic4);
        template.sendDefault(0, 0, "foo");
        template.flush();
        pf.destroy();

        assertThat(latch.await(30, TimeUnit.SECONDS)).isTrue();
        assertThat(bits.cardinality()).isEqualTo(8);

        container1.stop();
        container2.stop();
        container3.stop();
        container4.stop();
        container5.stop();
        container6.stop();
        container7.stop();
        container8.stop();
    }



    private Consumer<?, ?> spyOnConsumer(KafkaMessageListenerContainer<Integer, String> container) {
        Consumer<?, ?> consumer = spy(
                KafkaTestUtils.getPropertyValue(container, "listenerConsumer.consumer", Consumer.class));
        new DirectFieldAccessor(KafkaTestUtils.getPropertyValue(container, "listenerConsumer"))
                .setPropertyValue("consumer", consumer);
        return consumer;
    }
    @Test
    public void oneTopicTwoNodes(){

        KafkaTemplate template = getKafkaTemplate(ConsensusIterationSerializer.class.getName(),ConsensusIterationSerializer.class.getName());
        KafkaProducerImpl producer =
                spy(new KafkaProducerImpl(template,Set.of("topic2")));

        KafkaConsumerContainerImpl containerImpl1 = getKafkaConsumerContainerImpl(Set.of(topic4),"group1",producer,"containerName");
        KafkaConsumerContainerImpl containerImpl2 = getKafkaConsumerContainerImpl(Set.of(topic4),"group2",producer,"containerName");
        List<KafkaConsumerContainerImpl> containers = new ArrayList<>();

        containers.add(containerImpl1);
        containers.add(containerImpl2);

        containers.forEach(cont ->{
            try{
                cont.run();
                ContainerTestUtils.waitForAssignment(cont.getContainer(), embeddedKafka.getPartitionsPerTopic());

            }catch(Exception e){
                System.out.println(e);
            }
        });



        ConsensusIteration iteration = new ConsensusIteration();
        iteration.setMessage("message");
        iteration.hops.add("producer1");
        iteration.count = 1;

        sendTestMessage(topic4,iteration);

        while(containers.stream().anyMatch(cont -> cont.getMessages().size() == 0)){
            try {
                Thread.sleep(10);
            }
            catch (Exception e){

            }
        }
        LOGGER.info("========================");
        LOGGER.info("oneProducerTwoConsumers");
        LOGGER.info("========================");
        containers.forEach(cont -> {

            LOGGER.info(String.format("container bean %s",cont.getContainer().getBeanName()));
            LOGGER.info(String.format("producer topics %s",cont.producer.producerTopics.toString()));
            LOGGER.info(String.format("consumer topics %s",cont.consumerTopics.toString()));

            assertThat(iteration).isEqualToComparingFieldByField(iteration);
            Mockito.verify(cont.producer, Mockito.atLeastOnce()).sendMessage(any(ConsensusIteration.class));
            LOGGER.info("------------------------------");
        });
        containers.forEach(container -> container.stop());
    }
    public class NodeConfig {
        public String name;
        public Set<String> producerTopics;
        public Set<String> consumerTopics;
        public String group;
        public NodeConfig(Set<String> consumerT,Set<String> producerT,String containerGroup,String containerName){
            producerTopics = producerT;
            consumerTopics = consumerT;
            group = containerGroup;
            name = containerName;

        }

    }
    public List<KafkaConsumerContainerImpl> getConsensusGraph(NodeConfig[] nodes){

        KafkaTemplate template = getKafkaTemplate(ConsensusIterationSerializer.class.getName(),ConsensusIterationSerializer.class.getName());
        return Arrays.asList(nodes).stream().map(node ->
                        getKafkaConsumerContainerImpl((node.consumerTopics),node.group,
                                new KafkaProducerImpl(template,node.producerTopics),node.name))
                .collect(Collectors.toList());

    }
    public static void waitForSingleContainerAssignment(Object container, int partitions)
            throws Exception {
        int n = 0;
        int count = 0;
        Method getAssignedPartitions = getAssignedPartitionsMethod(container.getClass());
        while (n++ < 600 && count < partitions) {
            count = 0;
            Collection<?> assignedPartitions = (Collection<?>) getAssignedPartitions.invoke(container);
            if (assignedPartitions != null) {
                count = assignedPartitions.size();
                break;

            }
            if (count < partitions) {
                Thread.sleep(100);
            }
        }
        assertThat(count).isGreaterThan(0);
    }
    private static Method getAssignedPartitionsMethod(Class<?> clazz) {
        final AtomicReference<Method> theMethod = new AtomicReference<Method>();
        ReflectionUtils.doWithMethods(clazz, new ReflectionUtils.MethodCallback() {

            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                theMethod.set(method);
            }
        }, new ReflectionUtils.MethodFilter() {

            @Override
            public boolean matches(Method method) {
                return method.getName().equals("getAssignedPartitions") && method.getParameterTypes().length == 0;
            }
        });
        assertThat(theMethod.get()).isNotNull();
        return theMethod.get();
    }

    @Test
    public void degreeThreeNodes(){
        /**
         *
         *
         * N1 T2 N4
         * T1 T3
         * N2   N3
         *
         * Degree 3 Nodes:
         *
         *  N1:
         *   - P: T2,T3,T1
         *   - C: T2,T3,T1
         *
         *  N2:
         *   - P: T2
         *   - C: T2
         *
         *  N2:
         *   - P: T1
         *   - C: T1
         *
         *  N3:
         *   - P: T3
         *   - C: T3
         */



        NodeConfig[] nodeConfig = {
                new NodeConfig(Set.of(topic1,topic2,topic3,topic4),Set.of(topic1,topic2,topic3),"group1","container1"),
                new NodeConfig(Set.of(topic1,topic2,topic3),Set.of(topic1),"group2","container2"),
                new NodeConfig(Set.of(topic1,topic2,topic3),Set.of(topic2),"group3","container3"),
                new NodeConfig(Set.of(topic1,topic2,topic3),Set.of(topic3),"group4","container4")
        };

        List<KafkaConsumerContainerImpl> containers = getConsensusGraph(nodeConfig);

        containers.forEach(cont ->{
            try{
                cont.run();
                waitForSingleContainerAssignment(cont.getContainer(),  embeddedKafka.getPartitionsPerTopic());
            }catch(Exception e){
                System.out.println(e);
            }
        });



        ConsensusIteration iteration = new ConsensusIteration();
        iteration.setMessage("message");
        iteration.hops.add("producer1");
        iteration.count = 1;

        sendTestMessage(topic4,iteration);

        while(containers.stream().anyMatch(cont -> cont.getMessages().size() == 0)){
            try {
                Thread.sleep(20);
            }
            catch (Exception e){

            }
        }
        LOGGER.info("========================");
        LOGGER.info("oneProducerTwoConsumers");
        LOGGER.info("------------------------");

        containers.forEach(cont -> {


            LOGGER.info(String.format("%s",cont.containerName));
            LOGGER.info(String.format("producer topics %s",cont.producer.producerTopics.toString()));
            LOGGER.info(String.format("consumer topics %s",cont.consumerTopics.toString()));
            LOGGER.info(String.format("messages  size %s",cont.getMessages().size()));

            assertThat(cont.messages.size()).isGreaterThan(0);
            LOGGER.info("------------------------");

        });
        containers.forEach(container -> container.stop());
    }
    @Test
    public void degreeFiveNodes(){
        /**
         *
         *
         * N1 T2 N4 T6 N5
         * T1 T3 T4 T5
         * N2    N3   N6
         *
         * Degree 3 Nodes:
         *
         *  N4:
         *  - P: T2,T3,T4,T5,T6
         *  - C: T2,T3,T4,T5,T6
         *
         *  N1:
         *   - P: T2,T3,T1
         *   - C: T2,T3,T1
         *
         *  N2:
         *   - P: T2
         *   - C: T2
         *
         *  N2:
         *   - P: T1
         *   - C: T1
         *
         *  N3:
         *   - P: T3
         *   - C: T3
         */



        NodeConfig[] nodeConfig = {
                new NodeConfig(Set.of(topic1,topic2,topic3,topic4),Set.of(topic1,topic2,topic3),"group1","container1"),
                new NodeConfig(Set.of(topic1,topic2,topic3),Set.of(topic1),"group2","container2"),
                new NodeConfig(Set.of(topic1,topic2,topic3),Set.of(topic2),"group3","container3"),
                new NodeConfig(Set.of(topic1,topic2,topic3),Set.of(topic3),"group4","container4")
        };

        List<KafkaConsumerContainerImpl> containers = getConsensusGraph(nodeConfig);

        containers.forEach(cont ->{
            try{
                cont.run();
                waitForSingleContainerAssignment(cont.getContainer(),  embeddedKafka.getPartitionsPerTopic());
            }catch(Exception e){
                System.out.println(e);
            }
        });



        ConsensusIteration iteration = new ConsensusIteration();
        iteration.setMessage("message");
        iteration.hops.add("producer1");
        iteration.count = 1;

        sendTestMessage(topic4,iteration);

        while(containers.stream().anyMatch(cont -> cont.getMessages().size() == 0)){
            try {
                Thread.sleep(20);
            }
            catch (Exception e){

            }
        }
        LOGGER.info("========================");
        LOGGER.info("oneProducerTwoConsumers");
        LOGGER.info("------------------------");

        containers.forEach(cont -> {


            LOGGER.info(String.format("%s",cont.containerName));
            LOGGER.info(String.format("producer topics %s",cont.producer.producerTopics.toString()));
            LOGGER.info(String.format("consumer topics %s",cont.consumerTopics.toString()));
            LOGGER.info(String.format("messages  size %s",cont.getMessages().size()));

            assertThat(cont.messages.size()).isGreaterThan(0);
            LOGGER.info("------------------------");

        });
        containers.forEach(container -> container.stop());
    }


}
