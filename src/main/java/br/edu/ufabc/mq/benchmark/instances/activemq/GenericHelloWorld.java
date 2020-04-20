package br.edu.ufabc.mq.benchmark.instances.activemq;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.mq.client.MessagingProducer;
import br.edu.ufabc.mq.client.MessagingReceiver;
import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.mq.message.AbstractMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericHelloWorld {

	public static void main(final String[] args) throws MessagingException {

		final Map<String, Object> properties = new HashMap<>();
		final Map<String, Object> producerProperties = new HashMap<>();
		final Map<String, Object> receiverProperties = new HashMap<>();
		final Map<String, Object> receiverStartProperties = new HashMap<>();
		final Map<String, Object> producerStartProperties = new HashMap<>();

		try (final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory = new ActiveMQWrapperFactory(properties)) {
			doProducer(producerProperties, producerStartProperties, wrapperFactory);
			doReceiver(receiverProperties, receiverStartProperties, wrapperFactory);
		}

	}

	private static void doReceiver(final Map<String, Object> receiverProperties,
			final Map<String, Object> receiverStartProperties, final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory)
					throws MessagingException {
		try (final MessagingReceiver<?, ?> receiver = wrapperFactory.getClientFactory()
				.createReceiver(receiverProperties)) {
			wrapperFactory.start(receiver, receiverStartProperties);

			final AbstractMessage<?> message = receiver.receive("TODO");
			final String text = message.getBody();

			log.info(text);
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

	private static void doProducer(final Map<String, Object> producerProperties,
			final Map<String, Object> producerStartProperties, final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory)
					throws MessagingException {
		final Map<String, Object> messageProperties = new HashMap<>();
		try (final MessagingProducer<?, ?> producer = wrapperFactory.getClientFactory()
				.createProducer(producerProperties)) {
			wrapperFactory.start(producer, producerStartProperties);

			final AbstractMessage<?> message = wrapperFactory.createMessage("body", messageProperties);
			producer.send(message);
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

}
