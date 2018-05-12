package com.consensus.kafka.main;

import com.consensus.utils.base.CmdLineParser;
import com.consensus.utils.domain.ConsensusIteration;
import com.consensus.kafka.KafkaProducerImpl;
import com.consensus.utils.serializer.ConsensusIterationSerializer;

import java.util.HashMap;



public class ProducerMain {

    private static System.Logger producerLogger  = System.getLogger("ProducerLogger");

    public static void main(String[] args) {

        if(args.length > 0){
            CmdLineParser parsed = new CmdLineParser(args);
            HashMap<String,Object> prodProps = new HashMap<String,Object>();

            prodProps.put("key.serializer",ConsensusIterationSerializer.class.getName());
            prodProps.put("value.serializer",ConsensusIterationSerializer.class.getName());

            KafkaProducerImpl producer = new
                    KafkaProducerImpl(prodProps,parsed.producerTopics,parsed.bootServer,parsed.groupId);

            sendMessage(producer,parsed.message);

        }

        else{
            producerLogger.log(System.Logger.Level.INFO,"No Args");
        }


    }

    private static void sendMessage(KafkaProducerImpl producer,String message){

        ConsensusIteration iteration = new ConsensusIteration();

        iteration.setMessage(message);
        iteration.hops.add("producer1");
        producer.sendMessage(iteration);

    }
}

