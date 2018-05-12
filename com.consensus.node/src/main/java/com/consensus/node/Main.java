package com.consensus.node;
//import com.consensus.grid.GridGraphGeneratorImpl;
//import com.consensus.kafka.KafkaConsumerContainerImpl;
//import com.consensus.kafka.KafkaProducerImpl;
//import com.consensus.utils.base.CmdLineParser;
//import com.consensus.utils.base.ThreadRunner;
//import com.consensus.grid.GridGraphGeneratorImpl;
import com.consensus.utils.serializer.ConsensusIterationDeserializer;
import com.consensus.utils.serializer.ConsensusIterationSerializer;
//
//import java.util.HashMap;
//import java.util.UUID;

import com.consensus.kafka.KafkaConsumerContainerImpl;
import com.consensus.kafka.KafkaProducerImpl;
import com.consensus.utils.base.CmdLineParser;
import com.consensus.utils.base.ThreadRunner;
import com.consensus.utils.serializer.ConsensusIterationSerializer;

import java.util.HashMap;
import java.util.UUID;

public class Main {

    private static System.Logger mainLogger = System.getLogger("AppMain");

    public  KafkaConsumerContainerImpl consumer;

    public  KafkaProducerImpl producer;

    public static void main(String[] args) {

//        GridGraphGeneratorImpl g = new GridGraphGeneratorImpl();

//        (%1$tc) [%4$s] ~%2$s~%nMessage: "%5$s"%n
        System.setProperty("java.util.logging.SimpleFormatter.format","%1 %2 %3 %4 %5");

        if(args.length > 0){

            CmdLineParser parsed = new CmdLineParser(args);

            HashMap<String,Object> consProps = new HashMap<String,Object>();
            HashMap<String,Object> prodProps = new HashMap<String,Object>();

            prodProps.put("key.serializer",ConsensusIterationSerializer.class.getName());
            prodProps.put("value.serializer",ConsensusIterationSerializer.class.getName());

            KafkaProducerImpl producer = new
                    KafkaProducerImpl(prodProps,parsed.producerTopics,parsed.bootServer,parsed.groupId);

            consProps.put("key.deserializer",ConsensusIterationDeserializer.class.getName());
            consProps.put("value.deserializer",ConsensusIterationDeserializer.class.getName());


            KafkaConsumerContainerImpl consumer =
                    new KafkaConsumerContainerImpl(consProps,parsed.consumerTopics,parsed.bootServer,parsed.groupId,producer,"containerName");

            consumer.setMessageListener();

            ConsensusNode node = new ConsensusNode(producer,consumer);

            ThreadRunner.runThread(new Thread(node),"Consensus ConsensusIteration" + UUID.randomUUID().toString());
        }

        else{
            mainLogger.log(System.Logger.Level.INFO,"No Args");
        }


        return;

    }

//    }
}
