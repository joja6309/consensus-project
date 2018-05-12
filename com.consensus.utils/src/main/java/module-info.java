module com.consensus.utils {
    requires jackson.databind;
    requires kafka.clients;
//    requires spring.kafka;

    exports com.consensus.utils.domain;
    exports com.consensus.utils.serializer;
    exports com.consensus.utils.base;
//    exports org.springframework.kafka.listener;

}