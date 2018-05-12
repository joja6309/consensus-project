package com.consensus.node;

import com.consensus.kafka.KafkaConsumerContainerImpl;
import com.consensus.kafka.KafkaProducerImpl;
import com.consensus.utils.base.ThreadRunner;

//import com.consensus.kafka.KafkaConsumerContainerImpl;
//import com.consensus.kafka.KafkaProducerImpl;
//
//import com.consensus.kafka.KafkaProducerImpl;
//import com.consensus.utils.base.ThreadRunner;
public class ConsensusNode implements Runnable{


    public KafkaProducerImpl producer;

    public KafkaConsumerContainerImpl consumer;


    public KafkaProducerImpl getProducer() {
        return producer;
    }

    public void setProducer(KafkaProducerImpl producer) {
        this.producer = producer;
    }

    public KafkaConsumerContainerImpl getConsumer() {
        return consumer;
    }

    public void setConsumer(KafkaConsumerContainerImpl consumer) {
        this.consumer = consumer;
    }

    public void setMessageListener(){
        consumer.setMessageListener();

    }

        public ConsensusNode(KafkaProducerImpl producer, KafkaConsumerContainerImpl consumer){

            this.producer = producer;
            this.consumer = consumer;
            setMessageListener();

        }

    @Override
    public void run() {
        ThreadRunner.runThread(new Thread(consumer),"Consumer Thread");

    }
}
