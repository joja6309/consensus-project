package com.consensus.kafka.configuration;

import java.util.HashMap;

public class KafkaConfiguration {

    public static HashMap<String,Object> setProps( String bootServer, String groupId,HashMap<String,Object> extraPairs){

        HashMap<String,Object> kafkaProps =  new HashMap<String,Object>();
        kafkaProps.put("bootstrap.servers",bootServer);
        kafkaProps.put("group.id", groupId);
        kafkaProps.put("auto.offset.reset", "earliest");
        kafkaProps.putAll(extraPairs);

        return kafkaProps;

    }
}
