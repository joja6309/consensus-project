package com.consensus.kafka.consumer;

import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;

public interface IKafkaConsumerContainer extends Runnable {

    void run();

    KafkaMessageListenerContainer<Integer, String> createContainer(ContainerProperties containerProps, HashMap<String,Object> kafkaProps);

}
