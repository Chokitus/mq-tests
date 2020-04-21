package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.util.Map;

import br.edu.ufabc.mq.message.AbstractMessage;

public class RabbitMQMessage extends AbstractMessage<String> {

	public RabbitMQMessage(final String message, final String destination, final Map<String, Object> properties) {
		super(message, destination, properties);
	}

	@Override
	protected String getBodyImpl() {
		return message;
	}

}
