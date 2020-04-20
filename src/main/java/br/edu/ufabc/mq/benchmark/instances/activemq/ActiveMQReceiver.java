package br.edu.ufabc.mq.benchmark.instances.activemq;

import java.util.Map;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientMessage;

import br.edu.ufabc.mq.client.MessagingReceiver;

public class ActiveMQReceiver extends MessagingReceiver<ClientConsumer, ActiveMQMessage> {

	public ActiveMQReceiver(final ClientConsumer client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws ActiveMQException {
		client.close();
	}

	@Override
	public void startImpl(final Map<String, Object> properties) {
		throw new UnsupportedOperationException("Um cliente do ActiveMQ deve ser iniciado através de seu objeto Session");
	}

	@Override
	protected ActiveMQMessage receiveImpl(final String property) throws ActiveMQException {
		final ClientMessage message = client.receive();
		return new ActiveMQMessage(message);
	}

}
