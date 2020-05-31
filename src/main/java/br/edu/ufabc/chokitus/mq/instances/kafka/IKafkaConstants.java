package br.edu.ufabc.chokitus.mq.instances.kafka;

public interface IKafkaConstants {
    String KAFKA_BROKERS = "localhost:9092,localhost:9093";
    Integer MESSAGE_COUNT=1000;
    String CLIENT_ID="client1";
    String TOPIC_NAME="grebgrob";
    String GROUP_ID_CONFIG="consumerGroup1";
    Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
    String OFFSET_RESET_LATEST="latest";
    String OFFSET_RESET_EARLIER="earliest";
    Integer MAX_POLL_RECORDS=1;
}