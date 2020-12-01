package br.edu.ufabc.chokitus.mq.instances.activemq.blocking.factory;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client.ActiveMQProducer;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.config.ActiveMQProperty;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client.ActiveMQReceiver;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;

public class ActiveMQClientFactory extends AbstractClientFactory<ActiveMQReceiver, ActiveMQProducer> {

	private final ClientSessionFactory sessionFactory;
	private final List<ClientSession> sessions = new ArrayList<>();

	public ActiveMQClientFactory(final ConfigurationProperties clientFactoryProperties, final ClientSessionFactory sessionFactory) {
		super(clientFactoryProperties);
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected ActiveMQReceiver createConsumerImpl(final ConfigurationProperties receiverProperties) throws ActiveMQException {
		final ClientSession session = getSession();
		final ClientConsumer client = session.createConsumer(receiverProperties.<String>getProp(ActiveMQProperty.QUEUE_NAME.getValue()));
		return new ActiveMQReceiver(client, session, receiverProperties);
	}

	@Override
	protected ActiveMQProducer createProducerImpl(final ConfigurationProperties producerProperties) throws ActiveMQException {
		final ClientSession session = getSession();
		final ClientProducer client = session.createProducer(producerProperties.<String>getProp(ActiveMQProperty.QUEUE_NAME.getValue()));
		return new ActiveMQProducer(client, session, producerProperties);
	}


	protected ClientSession getSession() throws ActiveMQException {
		if (sessions.isEmpty()) {
			sessions.add(createNewSession());
		}
		return sessions.get(0);
	}

	private ClientSession createNewSession() throws ActiveMQException {
		final String username = clientFactoryProperties.getProp(ActiveMQProperty.USERNAME.getValue());
		final String password = clientFactoryProperties.getProp(ActiveMQProperty.PASSWORD.getValue());
		final boolean xa = false; // For now
		final boolean autoCommitSends = true; // For now
		final boolean autoCommitAcks = true; // For now
		final boolean preAcknowledge = false; // For now
		final int ackBatchSize = clientFactoryProperties.getAsInteger(ActiveMQProperty.ACK_BATCH.getValue());

		return sessionFactory.createSession(username, password, xa, autoCommitSends, autoCommitAcks, preAcknowledge, ackBatchSize);
	}

	@Override
	protected void closeImpl() throws ActiveMQException {
		for (final ClientSession clientSession : sessions) {
			clientSession.close();
		}
		sessionFactory.close();
	}

}
