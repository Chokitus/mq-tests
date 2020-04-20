package br.edu.ufabc.mq.benchmark.instances.activemq;

import java.util.Map;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;

import br.edu.ufabc.mq.factory.AbstractClientFactory;

public class ActiveMQClientFactory extends AbstractClientFactory<ActiveMQReceiver, ActiveMQProducer> {

	public ActiveMQClientFactory(final Map<String, Object> clientFactoryProperties) {
		super(clientFactoryProperties);
	}

	@Override
	protected ActiveMQReceiver createReceiverImpl(final Map<String, Object> receiverProperties) throws ActiveMQException {
		final ClientSession session = (ClientSession) clientFactoryProperties.get(ActiveMQProperty.SESSION.toString());
		final ClientConsumer client = session.createConsumer((String) receiverProperties.get(ActiveMQProperty.QUEUE_NAME.toString()));
		return new ActiveMQReceiver(client, receiverProperties);
	}

	@Override
	protected ActiveMQProducer createProducerImpl(final Map<String, Object> producerProperties) throws ActiveMQException {
		final ClientSession session = (ClientSession) clientFactoryProperties.get(ActiveMQProperty.SESSION.toString());
		final ClientProducer client = session.createProducer((String) producerProperties.get(ActiveMQProperty.QUEUE_NAME.toString()));
		return new ActiveMQProducer(client, producerProperties);
	}

}
