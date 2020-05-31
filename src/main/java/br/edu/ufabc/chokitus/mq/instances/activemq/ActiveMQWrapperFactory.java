package br.edu.ufabc.chokitus.mq.instances.activemq;

import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ServerLocator;

import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActiveMQWrapperFactory
		extends AbstractWrapperFactory<ActiveMQConsumer, ActiveMQProducer, ActiveMQMessage, ActiveMQClientFactory> {

	private ServerLocator locator;

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

		locator = ActiveMQClient
				.createServerLocator((String) clientFactoryProperties.get(ActiveMQProperty.LOCATOR_URL.toString()));

		return new ActiveMQClientFactory(clientProperties, locator.createSessionFactory());
	}

	@Override
	protected void startConsumerImpl(final ActiveMQConsumer client, final Map<String, Object> clientStartProperties,
			final ActiveMQClientFactory clientFactory) throws Exception {
		startClient(client);
	}

	@Override
	protected void startProducerImpl(final ActiveMQProducer client, final Map<String, Object> clientStartProperties,
			final ActiveMQClientFactory clientFactory) throws Exception {
		startClient(client);
	}

	@Override
	protected ActiveMQMessage createMessageForProducerImpl(final byte[] body, final String destination, final ActiveMQProducer producer,
			final Map<String, Object> messageProperties, final ActiveMQClientFactory clientFactory) throws Exception {
		return createMessageImpl(body, destination, producer, messageProperties);
	}

	private ActiveMQMessage createMessageImpl(final byte[] body, final String destination,
			final br.edu.ufabc.chokitus.mq.instances.activemq.ActiveMQClient client,
			final Map<String, Object> messageProperties) throws Exception {
		final ClientMessage message = client.getSession().createMessage(
				(boolean) messageProperties.get(messageProperties.get(ActiveMQProperty.DURABLE_MESSAGE.toString())));
		return new ActiveMQMessage(message);
	}

	private void startClient(final br.edu.ufabc.chokitus.mq.instances.activemq.ActiveMQClient client)
			throws ActiveMQException {
		client.getSession().start();
	}

	@Override
	protected void closeImpl() throws Exception {
		locator.close();
	}

}
