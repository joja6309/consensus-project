package com.consensus.kafka;

import com.consensus.kafka.configuration.KafkaConfiguration;
import com.consensus.kafka.producer.IKafkaProducer;
import com.consensus.utils.domain.ConsensusIteration;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.OFF;

public class KafkaProducerImpl implements IKafkaProducer {


    KafkaTemplate kafkaTemplate;

    public Set<String> producerTopics;

    private Integer sentCount = 0;

    private static System.Logger kafkaProducerImpl = System.getLogger("KafkaProducerImpl");

    public KafkaProducerImpl
            (HashMap<String,Object> additionalProps,Set<String> topics,String bootServer, String groupId){
//        kafkaProducerImpl.log(INFO,String.format("Producer Topics %s",topics));

        HashMap<String,Object> props = KafkaConfiguration.setProps(bootServer, groupId, additionalProps);
        kafkaTemplate = createTemplate(props);
        producerTopics = topics;
    }

    public KafkaProducerImpl(KafkaTemplate template,Set<String> topics){
        kafkaProducerImpl.log(INFO,String.format("Producer Topics %s",topics));

        kafkaTemplate = template;
        producerTopics = topics;
    }
    private KafkaTemplate<Integer, String> createTemplate(Map<String,Object> props) {
        ProducerFactory<Integer, String> pf =
                new DefaultKafkaProducerFactory<Integer, String>(props);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        return template;
    }

    public void sendMessage(ConsensusIteration consensusIteration){
        for(String topic: producerTopics){
            try{
                consensusIteration.setCount(sentCount++);
//                kafkaProducerImpl.log(OFF,String.format("Topic %s | Message %s",topic, consensusIteration.toString()));
                kafkaTemplate.send(new ProducerRecord<String,ConsensusIteration>(topic, consensusIteration));
            }
            catch(Exception e){
                kafkaProducerImpl.log(ERROR,e);

            }


        }

    }
    public void sendMessages(List<ConsensusIteration> consensusIteration){

    }

}
