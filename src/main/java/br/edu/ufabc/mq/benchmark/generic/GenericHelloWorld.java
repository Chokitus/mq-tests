package br.edu.ufabc.mq.benchmark.generic;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.mq.benchmark.instances.activemq.ActiveMQWrapperFactory;
import br.edu.ufabc.mq.client.AbstractConsumer;
import br.edu.ufabc.mq.client.AbstractProducer;
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
		final Map<String, Object> messageProperties = new HashMap<>();

		try (final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory = new ActiveMQWrapperFactory(properties)) {
			doProducer(producerProperties, producerStartProperties, messageProperties, wrapperFactory);
			doReceiver(receiverProperties, receiverStartProperties, wrapperFactory);
		}

	}

	private static void doReceiver(final Map<String, Object> receiverProperties,
			final Map<String, Object> receiverStartProperties, final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory)
					throws MessagingException {
		try (final AbstractConsumer<?, ?> receiver = wrapperFactory.getClientFactory()
				.createReceiver(receiverProperties)) {

			wrapperFactory.startConsumer(receiver, receiverStartProperties);

			final AbstractMessage<?> message = receiver.receive("destination");
			final String text = message.getBody();

			log.info(text);
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

	private static void doProducer(final Map<String, Object> producerProperties,
			final Map<String, Object> producerStartProperties, final Map<String, Object> messageProperties,
			final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory) throws MessagingException {

		try (final AbstractProducer<?, ?> producer = wrapperFactory.getClientFactory()
				.createProducer(producerProperties)) {
			wrapperFactory.startProducer(producer, producerStartProperties);
			wrapperFactory.startProducer(producer, producerStartProperties);

			final AbstractMessage<?> message = wrapperFactory.createMessageForProducer("body", "destination", producer, messageProperties);
			producer.send(message);
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

}
