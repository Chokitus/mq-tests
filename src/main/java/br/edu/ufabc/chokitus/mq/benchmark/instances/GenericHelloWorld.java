package br.edu.ufabc.chokitus.mq.benchmark.instances;

import java.util.Map;

import br.edu.ufabc.chokitus.mq.benchmark.AbstractBenchmark;
import br.edu.ufabc.chokitus.mq.benchmark.BenchmarkConfiguration;
import br.edu.ufabc.chokitus.mq.client.AbstractConsumer;
import br.edu.ufabc.chokitus.mq.client.AbstractProducer;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
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

		log.info("[{}]: All properties loaded, initializing WrapperFactory with generalProperties=[{}]...", getInstance(), properties);
		try (final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory = config.getMqInstance().getFactory(properties)) {
			log.info("[{}]: WrapperFactory initialized! [{}]", getInstance(), wrapperFactory);
			doProducer(producerProperties, producerStartProperties, messageProperties, wrapperFactory);
			doConsumer(consumerProperties, consumerStartProperties, wrapperFactory);
			log.info("[{}]: Consumer sequence finished!", getInstance());
		} catch (final Exception e) {
			log.error("Error during execution!", e);
		}
		log.info("[{}]: Benchmark is over!", getInstance());
	}

	private void doConsumer(final Map<String, Object> consumerProperties,
			final Map<String, Object> consumerStartProperties,
			final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory) {
		log.info("[{}]: Initializing Consumer with properties=[{}]...", getInstance(), consumerProperties);
		try (final AbstractConsumer<?, ?> consumer = wrapperFactory.getClientFactory().createConsumer(consumerProperties)) {
			log.info("[{}]: Starting Consumer with properties=[{}]...", getInstance(), consumerProperties);
			wrapperFactory.startConsumer(consumer, consumerStartProperties);

			log.info("[{}]: Receiving message...", getInstance());
			final AbstractMessage<?> message = consumer.receive(DESTINATION);
			final byte[] text = message.getBody();

			log.info("[{}]: Found data: {}",getInstance(), new String(text));
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
	}

	private void doProducer(final Map<String, Object> producerProperties,
			final Map<String, Object> producerStartProperties, final Map<String, Object> messageProperties,
			final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory) {

		log.info("[{}]: Initializing Producer with properties=[{}]...", getInstance(), producerProperties);
		try (final AbstractProducer<?, ?> producer = wrapperFactory.getClientFactory()
				.createProducer(producerProperties)) {
			log.info("[{}]: Starting Producer with properties=[{}]...", getInstance(), producerStartProperties);
			wrapperFactory.startProducer(producer, producerStartProperties);

			log.info("[{}]: Creating message for producer with properties=[{}]...", getInstance(), messageProperties);
			final AbstractMessage<?> message = wrapperFactory.createMessageForProducer("Bom dia!".getBytes(), DESTINATION, producer, messageProperties);

			log.info("[{}]: Sending message for producer...", getInstance());
			producer.send(message);
			log.info("[{}]: Message sent for producer...", getInstance());
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
		log.info("[{}]: Producer sequence finished!", getInstance());
	}

}
