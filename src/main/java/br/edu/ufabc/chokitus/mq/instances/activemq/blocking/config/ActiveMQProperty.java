package br.edu.ufabc.chokitus.mq.instances.activemq.blocking.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActiveMQProperty {
	LOCATOR_URL("locator_url"), //
	DURABLE_MESSAGE("durable_message"), //
	DURABLE_QUEUE("durable_queue"), //
	QUEUE_NAME("queue_name"), //
	QUEUE_ADDRESS("queue_address"), //
	QUEUE_ROUTING_TYPE("queue_routing_type"), //
	USERNAME("username"), //
	PASSWORD("password"), //
	ACK_BATCH("ack_batch_size");

	private final String value;
}
