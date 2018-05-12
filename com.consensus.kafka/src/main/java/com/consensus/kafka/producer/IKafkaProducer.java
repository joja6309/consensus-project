package com.consensus.kafka.producer;
import com.consensus.utils.domain.ConsensusIteration;

import java.util.List;

public interface IKafkaProducer {

    void sendMessage(ConsensusIteration consensusIteration);
    void sendMessages(List<ConsensusIteration> consensusIterations);

}
