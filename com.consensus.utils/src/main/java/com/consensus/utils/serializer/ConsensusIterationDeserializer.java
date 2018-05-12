package com.consensus.utils.serializer;
import com.consensus.utils.domain.ConsensusIteration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ConsensusIterationDeserializer implements Deserializer<ConsensusIteration> {
    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {

    }

    @Override
    public ConsensusIteration deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        ConsensusIteration user = null;
        try {
            user = mapper.readValue(arg1, ConsensusIteration.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return user;
    }
}
