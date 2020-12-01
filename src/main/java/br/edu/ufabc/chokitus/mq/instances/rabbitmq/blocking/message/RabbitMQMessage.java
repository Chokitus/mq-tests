package br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.message;

import java.util.Map;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.message.AbstractMessage;

public class RabbitMQMessage extends AbstractMessage<byte[]> {

	public RabbitMQMessage(final byte[] message, final String destination, final ConfigurationProperties properties) {
		super(message, destination, properties);
	}

	@Override
	protected byte[] getBodyImpl() {
		return message;
	}

}
