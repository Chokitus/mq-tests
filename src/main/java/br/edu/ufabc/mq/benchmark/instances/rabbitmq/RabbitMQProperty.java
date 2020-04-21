package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RabbitMQProperty {
	HOST("host"),
	PORT("port"),
	DEFAULT_EXCHANGE(""),
	QUEUE_PROPERTY("queue"),
	ARGUMENTS_PROPERTY("args"),
	DURABLE_PROPERTY("durable"),
	EXCLUSIVE_PROPERTY("exclusive"),
	AUTO_DELETE_PROPERTY("autodelete"),
	CONNECTION_FACTORY("connection_factory");

	private final String value;
}
