package br.edu.ufabc.chokitus.mq.benchmark.instances;

import java.util.Map;

import br.edu.ufabc.chokitus.mq.benchmark.AbstractBenchmark;
import br.edu.ufabc.chokitus.mq.benchmark.BenchmarkConfiguration;
import br.edu.ufabc.chokitus.mq.client.AbstractConsumer;
import br.edu.ufabc.chokitus.mq.client.AbstractProducer;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.activemq.ActiveMQWrapperFactory;
import br.edu.ufabc.chokitus.mq.message.AbstractMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericHelloWorld extends AbstractBenchmark {

	private static final String DESTINATION = "test-queue";

	public GenericHelloWorld(final BenchmarkConfiguration config) {
		super(config);
	}

	@Override
	public void doBenchmark() {
		final Map<String, Object> properties = config.getGeneralProperties();
		final Map<String, Object> producerProperties = config.getProducerProperties();
		final Map<String, Object> consumerProperties = config.getConsumerProperties();
		final Map<String, Object> consumerStartProperties = config.getConsumerStartProperties();
		final Map<String, Object> producerStartProperties = config.getProducerStartProperties();
		final Map<String, Object> messageProperties = config.getMessageProperties();

		try (final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory = new ActiveMQWrapperFactory(properties)) {
			doProducer(producerProperties, producerStartProperties, messageProperties, wrapperFactory);
			doConsumer(consumerProperties, consumerStartProperties, wrapperFactory);
		}
	}

	private void doConsumer(final Map<String, Object> consumerProperties,
			final Map<String, Object> consumerStartProperties,
			final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory) {
		try (final AbstractConsumer<?, ?> consumer = wrapperFactory.getClientFactory().createConsumer(consumerProperties)) {
			wrapperFactory.startConsumer(consumer, consumerStartProperties);

			final AbstractMessage<?> message = consumer.receive(DESTINATION);
			final byte[] text = message.getBody();

			log.info("Found data with size: {}", text.length);
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

	private void doProducer(final Map<String, Object> producerProperties,
			final Map<String, Object> producerStartProperties, final Map<String, Object> messageProperties,
			final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory) {

		try (final AbstractProducer<?, ?> producer = wrapperFactory.getClientFactory()
				.createProducer(producerProperties)) {
			wrapperFactory.startProducer(producer, producerStartProperties);

			final AbstractMessage<?> message = wrapperFactory.createMessageForProducer("body".getBytes(), DESTINATION, producer, messageProperties);
			producer.send(message);
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

}
