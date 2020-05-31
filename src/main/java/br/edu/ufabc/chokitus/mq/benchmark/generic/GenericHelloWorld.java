package br.edu.ufabc.chokitus.mq.benchmark.generic;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.chokitus.mq.client.AbstractConsumer;
import br.edu.ufabc.chokitus.mq.client.AbstractProducer;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.activemq.ActiveMQWrapperFactory;
import br.edu.ufabc.chokitus.mq.message.AbstractMessage;
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
		try (final AbstractConsumer<?, ?> consumer = wrapperFactory.getClientFactory()
				.createConsumer(receiverProperties)) {

			wrapperFactory.startConsumer(consumer, receiverStartProperties);

			final AbstractMessage<?> message = consumer.receive("destination");
			final byte[] text = message.getBody();

			log.info("Found data with size: {}", text.length);
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

			final AbstractMessage<?> message = wrapperFactory.createMessageForProducer("body".getBytes(), "destination",
					producer, messageProperties);
			producer.send(message);
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

}
