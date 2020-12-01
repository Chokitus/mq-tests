package br.edu.ufabc.chokitus.mq.instances.activemq.blocking.factory;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.message.ActiveMQMessage;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client.ActiveMQProducer;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.config.ActiveMQProperty;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client.ActiveMQReceiver;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ServerLocator;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActiveMQWrapperFactory extends AbstractWrapperFactory<ActiveMQReceiver, ActiveMQProducer, ActiveMQMessage, ActiveMQClientFactory> {

	private ServerLocator locator;

	/**
	 * Construtor básico para o <b>ActiveMQ</b>. Obrigatório a passagem dos seguintes parâmetros:
	 * <ul>
	 * <li>{@link br.edu.ufabc.chokitus.mq.instances.activemq.blocking.config.ActiveMQProperty#LOCATOR_URL}</li>
	 * </ul>
	 *
	 * @param properties
	 *
	 * @throws MessagingException
	 * @see AbstractWrapperFactory
	 */
	public ActiveMQWrapperFactory(final ConfigurationProperties properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected ActiveMQClientFactory createClientFactory(final ConfigurationProperties clientFactoryProperties) throws Exception {
		locator =
						ActiveMQClient.createServerLocator(clientFactoryProperties.getProp(ActiveMQProperty.LOCATOR_URL.getValue()));

		return new ActiveMQClientFactory(clientFactoryProperties, locator.createSessionFactory());
	}

	@Override
	protected void startConsumerImpl(final ActiveMQReceiver client,
	                                 final ConfigurationProperties clientStartProperties,
	                                 final ActiveMQClientFactory clientFactory) throws Exception {
		startClient(client);
	}

	@Override
	protected void startProducerImpl(final ActiveMQProducer client,
	                                 final ConfigurationProperties clientStartProperties,
	                                 final ActiveMQClientFactory clientFactory) throws Exception {
		createQueue(client, clientStartProperties);
		startClient(client);
	}

	@Override
	protected ActiveMQMessage createMessageForProducerImpl(final byte[] body,
	                                                       final String destination,
	                                                       final ActiveMQProducer producer,
	                                                       final ConfigurationProperties messageProperties,
	                                                       final ActiveMQClientFactory clientFactory) throws Exception {
		return createMessageImpl(body, producer, messageProperties);
	}

	@Override
	protected void closeImpl() throws Exception {
		locator.close();
	}

	private void createQueue(final ActiveMQProducer client,
	                         final ConfigurationProperties clientStartProperties) throws ActiveMQException {
		try {
			final SimpleString queueAddress =
							SimpleString.toSimpleString(clientStartProperties.getProp(ActiveMQProperty.QUEUE_ADDRESS
							                                                                                            .getValue()));
			final SimpleString queueName =
							SimpleString.toSimpleString(clientStartProperties.getProp(ActiveMQProperty.QUEUE_NAME
							                                                                                         .getValue()));
			final boolean durable = clientStartProperties.getProp(ActiveMQProperty.DURABLE_QUEUE.getValue());
			final RoutingType type =
							RoutingType.valueOf(clientStartProperties.getProp(ActiveMQProperty.QUEUE_ROUTING_TYPE.getValue()));
			client.getSession().createQueue(queueAddress, type, queueName, durable);
		} catch (final Exception e) {
			// Ignore
		}
	}

	private ActiveMQMessage createMessageImpl(final byte[] body,
	                                          final br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client.ActiveMQClient client,
	                                          final ConfigurationProperties messageProperties) {

		final ClientMessage message = client.getSession()
		                                    .createMessage(messageProperties.getProp(ActiveMQProperty.DURABLE_MESSAGE.getValue()));
		message.getBodyBuffer().writeBytes(body);
		return new ActiveMQMessage(message);
	}

	private void startClient(final br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client.ActiveMQClient client) throws ActiveMQException {
		client.getSession().start();
	}

}
