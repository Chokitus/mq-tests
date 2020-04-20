package br.edu.ufabc.mq.benchmark.instances.activemq;

import java.util.Map;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientProducer;

import br.edu.ufabc.mq.client.MessagingProducer;

public class ActiveMQProducer extends MessagingProducer<ClientProducer, ActiveMQMessage> {

	public ActiveMQProducer(final ClientProducer client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws ActiveMQException {
		client.close();
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
		throw new UnsupportedOperationException("Um cliente do ActiveMQ deve ser iniciado através de seu objeto Session");
	}

	@Override
	protected ActiveMQMessage sendImpl(final ActiveMQMessage message) throws ActiveMQException {
		client.send(message.getMessage());
		return null;
	}

}
