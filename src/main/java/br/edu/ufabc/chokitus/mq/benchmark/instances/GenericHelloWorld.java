package br.edu.ufabc.chokitus.mq.benchmark.instances;

import br.edu.ufabc.chokitus.mq.benchmark.AbstractBenchmark;
import br.edu.ufabc.chokitus.mq.benchmark.BenchmarkConfiguration;
import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;
import br.edu.ufabc.chokitus.mq.client.AbstractProducer;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.chokitus.mq.message.AbstractMessage;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class GenericHelloWorld extends AbstractBenchmark {

	private static final String DESTINATION = "test-queue";

	public GenericHelloWorld(final BenchmarkConfiguration config) {
		super(config);
	}

	@Override
	protected void doConsumer(final ConfigurationProperties consumerProperties,
	                          final ConfigurationProperties consumerStartProperties,
	                          final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory) {
		log.info("[{}]: Initializing Consumer with properties=[{}]...", getInstance(), consumerProperties);
		try (final AbstractReceiver<?, ?> consumer =
						     wrapperFactory.getClientFactory().createConsumer(consumerProperties)) {
			log.info("[{}]: Starting Consumer with properties=[{}]...", getInstance(), consumerProperties);
			wrapperFactory.startConsumer(consumer, consumerStartProperties);

			log.info("[{}]: Receiving message...", getInstance());
			final AbstractMessage<?> message = consumer.receive(DESTINATION);
			final byte[] text = message.getBody();

			log.info("[{}]: Found data: {}", getInstance(), new String(text));
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
		log.info("[{}]: Consumer sequence finished!", getInstance());
	}

	@Override
	protected void doProducer(final ConfigurationProperties producerProperties,
	                          final ConfigurationProperties producerStartProperties,
	                          final ConfigurationProperties messageProperties,
	                          final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory) {

		log.info("[{}]: Initializing Producer with properties=[{}]...", getInstance(), producerProperties);
		try (final AbstractProducer<?, ?> producer =
						     wrapperFactory.getClientFactory().createProducer(producerProperties)) {
			log.info("[{}]: Starting Producer with properties=[{}]...", getInstance(), producerStartProperties);
			wrapperFactory.startProducer(producer, producerStartProperties);

			log.info("[{}]: Creating message for producer with properties=[{}]...", getInstance(), messageProperties);
			final AbstractMessage<?> message = wrapperFactory.createMessageForProducer(LocalDateTime.now()
			                                                                                        .toString()
			                                                                                        .getBytes(),
			                                                                           DESTINATION,
			                                                                           producer,
			                                                                           messageProperties);

			log.info("[{}]: Sending message for producer...", getInstance());
			producer.send(message);
			log.info("[{}]: Message sent for producer...", getInstance());
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
		log.info("[{}]: Producer sequence finished!", getInstance());
	}

}
