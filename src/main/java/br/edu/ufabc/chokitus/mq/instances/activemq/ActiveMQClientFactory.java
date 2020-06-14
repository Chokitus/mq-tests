package br.edu.ufabc.chokitus.mq.instances.activemq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;

public class ActiveMQClientFactory extends AbstractClientFactory<ActiveMQConsumer, ActiveMQProducer> {

	private final ClientSessionFactory sessionFactory;
	private final List<ClientSession> sessions = new ArrayList<>();

	public ActiveMQClientFactory(final Map<String, Object> clientFactoryProperties, final ClientSessionFactory sessionFactory) {
		super(clientFactoryProperties);
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected ActiveMQConsumer createConsumerImpl(final Map<String, Object> receiverProperties) throws ActiveMQException {
		final ClientSession session = getSession();
		final ClientConsumer client = session.createConsumer((String) receiverProperties.get(ActiveMQProperty.QUEUE_NAME.getValue()));
		return new ActiveMQConsumer(client, session, receiverProperties);
	}

	@Override
	protected ActiveMQProducer createProducerImpl(final Map<String, Object> producerProperties) throws ActiveMQException {
		final ClientSession session = getSession();
		final ClientProducer client = session.createProducer((String) producerProperties.get(ActiveMQProperty.QUEUE_NAME.getValue()));
		return new ActiveMQProducer(client, session, producerProperties);
	}


	protected ClientSession getSession() throws ActiveMQException {
		if (sessions.isEmpty()) {
			sessions.add(createNewSession());
		}
		return sessions.get(0);
	}

	private ClientSession createNewSession() throws ActiveMQException {
		final String username = (String) clientFactoryProperties.get(ActiveMQProperty.USERNAME.getValue());
		final String password = (String) clientFactoryProperties.get(ActiveMQProperty.PASSWORD.getValue());
		final boolean xa = false; // For now
		final boolean autoCommitSends = true; // For now
		final boolean autoCommitAcks = true; // For now
		final boolean preAcknowledge = false; // For now
		final int ackBatchSize = (int) clientFactoryProperties.get(ActiveMQProperty.ACK_BATCH.getValue());

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
