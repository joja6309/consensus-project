package com.consensus.kafka;

import com.consensus.kafka.configuration.KafkaConfiguration;
import com.consensus.kafka.consumer.IKafkaConsumerContainer;
import com.consensus.utils.domain.ConsensusIteration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.*;
import java.util.concurrent.CountDownLatch;


public class KafkaConsumerContainerImpl implements IKafkaConsumerContainer,Runnable {

    public  KafkaMessageListenerContainer<Integer, String> container;
    private ContainerProperties containerProperties;
    private HashMap<String,Object> configurationProperties;
    public Set<ConsensusIteration> messages = new HashSet<>();
    public KafkaProducerImpl producer;
    private static System.Logger kafkaConsumerContainerImpl = System.getLogger(" KafkaConsumerContainerImpl");
    public Set<String> consumerTopics;
    public String containerName;


    public Set<ConsensusIteration> getMessages() {
        return messages;
    }

    public void setMessages(Set<ConsensusIteration> messages) {
        this.messages = messages;
    }


    public KafkaMessageListenerContainer<Integer, String> getContainer() {
        return container;
    }

    public void setContainer(KafkaMessageListenerContainer<Integer, String> container) {
        this.container = container;
    }

    public KafkaConsumerContainerImpl(ContainerProperties containerProps, Set<String> topics,KafkaProducerImpl producerImpl){

        topics.forEach(topic -> System.out.print(String.format("consumer topics: %s ",topic)));
        containerProperties  = containerProps;
        producer = producerImpl;


    }
    public KafkaConsumerContainerImpl(HashMap<String,Object> additionalProps, Set<String> topics, String bootServer, String groupId,KafkaProducerImpl producerImpl,String nodeName){

        configurationProperties  = KafkaConfiguration.setProps(bootServer, groupId, additionalProps);
        topics.forEach(topic -> System.out.print(String.format("consumer topics: %s ",topic)));
        String[] topicsArray = topics.toArray(new String[0]);
        consumerTopics = Set.of(topicsArray);
        containerProperties  = new ContainerProperties(topicsArray);
        producer = producerImpl;
        containerName = nodeName;


    }
    public void  setMessageListener(MessageListener<?,?> messageListener,HashMap<String,Object> configurationProperties) {

        containerProperties.setMessageListener(messageListener);
        container = createContainer(containerProperties,configurationProperties);
        container.setBeanName("container-" + UUID.randomUUID().toString().substring(0,6));
    }

    public void setMessageListener(){
        try {


            containerProperties.setMessageListener(new MessageListener<Integer, ConsensusIteration>() {
                @Override
                public void onMessage(ConsumerRecord<Integer, ConsensusIteration> record) {

                    try {

//                        kafkaConsumerContainerImpl.log(System.Logger.Level.INFO
//                                , String.format(
//                                        "\n" +
//                                                "========================="
//                                                + "\n" +
//                                                "Topic %s | Hops %s | Message %s"
//                                                + "\n" +
//                                                "========================="
//                                        , record.topic()
//                                        , record.value().getHops().toString()
//                                        , record.value().message
//                                ));
                        messages.add(record.value());
                        record.value().hops.add(container.getBeanName());
                        producer.sendMessage(record.value());

                    } catch (Exception e) {
                        System.out.print(e);
                    }


                }
            });
            container = createContainer(containerProperties,configurationProperties);
            container.setBeanName("container-" + UUID.randomUUID().toString().substring(0,6));
        }catch (Exception e) {
            System.out.print(e);
        }
    }



    public KafkaMessageListenerContainer<Integer, String>
                    createContainer(ContainerProperties containerProps,HashMap<String,Object> kafkaProps) {
        DefaultKafkaConsumerFactory<Integer, String> cf =
                new DefaultKafkaConsumerFactory<Integer, String>(kafkaProps);
        KafkaMessageListenerContainer<Integer, String> container =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        return container;
    }


    public void run(){
        container.start();
    }

    public void stop(){container.stop();}
}
