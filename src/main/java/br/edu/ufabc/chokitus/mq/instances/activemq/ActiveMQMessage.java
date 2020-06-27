package br.edu.ufabc.chokitus.mq.instances.activemq;

import org.apache.activemq.artemis.api.core.client.ClientMessage;

import br.edu.ufabc.chokitus.mq.message.AbstractMessage;

public class ActiveMQMessage extends AbstractMessage<ClientMessage> {


	public ActiveMQMessage(final ClientMessage message) {
		super(message, null, null);
	}

	/**
	 * TODO: Find better serialization format
	 */
	@Override
	protected byte[] getBodyImpl() {
		final byte[] bytes = new byte[message.getBodySize()];
		message.getBodyBuffer().readBytes(bytes);
		return bytes;
	}
}
