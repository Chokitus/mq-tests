package br.edu.ufabc.mq.benchmark.instances.activemq;

import org.apache.activemq.artemis.api.core.client.ClientMessage;

import br.edu.ufabc.mq.message.AbstractMessage;

public class ActiveMQMessage extends AbstractMessage<ClientMessage> {

	protected ActiveMQMessage(final ClientMessage message) {
		super(message);
	}

	@Override
	protected String getBodyImpl() {
		return message.getBodyBuffer().readString();
	}
}
