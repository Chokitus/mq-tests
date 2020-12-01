package br.edu.ufabc.chokitus.mq.instances.kafka.blocking.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaProperty {
	TOPIC_LIST("topic_list"), //
	SERVER_ADDRESS("server_addresses"), //
	CLIENT_ID("client_id"), //
	KEY_SERIALIZER("key_serializer"), //
	VALUE_SERIALIZER("value_serializer"), //
	CONSUMER_GROUP_ID("consumer_group_id"), //
	KEY_DESERIALIZER("key_deserializer"), //
	VALUE_DESERIALIZER("value_deserializer"), //
	MAX_POLL_RECORDS("max_poll_records"), //
	AUTO_COMMIT("auto_commit"), //
	OFFSET_RESET("offset_reset");

	private final String value;
}
