package br.edu.ufabc.mq.benchmark.instances.activemq;

import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;

import br.edu.ufabc.mq.client.Startable;
import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;

public class ActiveMQWrapperFactory
extends AbstractWrapperFactory<ActiveMQReceiver, ActiveMQProducer, ActiveMQMessage, ActiveMQClientFactory> {

	/**
	 * Construtor básico para o <b>ActiveMQ</b>. Obrigatório a passagem dos
	 * seguintes parâmetros:
	 * <ul>
	 * <li>{@link ActiveMQProperty#LOCATOR_URL}</li>
	 * </ul>
	 *
	 * @param properties
	 * @throws MessagingException
	 * @see AbstractWrapperFactory
	 */
	public ActiveMQWrapperFactory(final Map<String, Object> properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected ActiveMQClientFactory createClientFactory(final Map<String, Object> clientFactoryProperties)
			throws Exception {
		final Map<String, Object> clientProperties = new HashMap<>();

		final ServerLocator locator = ActiveMQClient
				.createServerLocator((String) clientFactoryProperties.get(ActiveMQProperty.LOCATOR_URL.toString()));
		clientProperties.put(ActiveMQProperty.LOCATOR.toString(), locator);
		final ClientSessionFactory sessionFactory = locator.createSessionFactory();
		clientProperties.put(ActiveMQProperty.SESSION_FACTORY.toString(), sessionFactory);

		return new ActiveMQClientFactory(clientProperties);
	}

	@Override
	protected void startImpl(final Startable client, final Map<String, Object> clientStartProperties,
			final ActiveMQClientFactory clientFactory) throws Exception {
		final ClientSession session = getSession(clientFactory);
		session.start();
	}

	@Override
	protected ActiveMQMessage createMessageImpl(final String body, final Map<String, Object> messageProperties,
			final ActiveMQClientFactory clientFactory) throws Exception {
		getSession(clientFactory).createMessage(
				(boolean) messageProperties.get(messageProperties.get(ActiveMQProperty.DURABLE_MESSAGE.toString())));
		return null;
	}

	private ClientSession getSession(final ActiveMQClientFactory clientFactory) throws ActiveMQException {
		ClientSession session = (ClientSession) clientFactory.getClientFactoryProperties()
				.get(ActiveMQProperty.SESSION.toString());
		if(session == null) {
			session = createNewSession(clientFactory);
		}
		return session;
	}

	private ClientSession createNewSession(final ActiveMQClientFactory clientFactory) throws ActiveMQException {
		final ClientSessionFactory factory = (ClientSessionFactory) clientFactory.getClientFactoryProperties()
				.get(ActiveMQProperty.SESSION_FACTORY.toString());
		final ClientSession session = factory.createSession();
		clientFactory.getClientFactoryProperties().put(ActiveMQProperty.SESSION.toString(), session);
		return session;
	}

	@Override
	protected void closeImpl() throws Exception {
		final ClientSession session = (ClientSession) clientFactory.getClientFactoryProperties()
				.get(ActiveMQProperty.SESSION.toString());
		if(session != null) {
			session.close();
		}
		final ClientSessionFactory factory = (ClientSessionFactory) clientFactory.getClientFactoryProperties()
				.get(ActiveMQProperty.SESSION_FACTORY.toString());
		if(factory != null) {
			factory.close();
		}
		final ServerLocator locator = (ServerLocator) clientFactory.getClientFactoryProperties()
				.get(ActiveMQProperty.LOCATOR.toString());
		if(locator != null) {
			locator.close();
		}
	}
}
