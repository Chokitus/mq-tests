package br.edu.ufabc.chokitus.mq.benchmark.instances.single_message;

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
import java.util.concurrent.CountDownLatch;

@Slf4j
public class NumberedSequentialBenchmark extends AbstractBenchmark {

	private static final String DESTINATION = "test-queue";
	private static final String NUM_MESSAGES = "num_messages";

	private final int numMessages;
	private final CountDownLatch latch;

	public NumberedSequentialBenchmark(final BenchmarkConfiguration config) {
		super(config);
		this.numMessages = config.getBenchmarkProperties().getProp(NUM_MESSAGES);
		latch = new CountDownLatch(numMessages);
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

			while (latch.getCount() != 0) {
				log.info("[{}]: Receiving message...", getInstance());
				final AbstractMessage<?> message = consumer.receive(DESTINATION);
				if (!message.isEmpty()) {
					final byte[] text = message.getBody();
					log.info("[{}]: Received message: {}", getInstance(), new String(text));
					latch.countDown();
					log.info("[{}]: Remaining messages: {}", getInstance(), latch.getCount());
				}
			}
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

			for (int i = 0; i < numMessages; i++) {
				log.info("[{}]: Creating message for producer with properties=[{}]...", getInstance(), messageProperties);
				final AbstractMessage<?> message = wrapperFactory.createMessageForProducer(LocalDateTime.now()
				                                                                                        .toString()
				                                                                                        .getBytes(),
				                                                                           DESTINATION,
				                                                                           producer,
				                                                                           messageProperties);

				log.info("[{}]: Sending message by producer...", getInstance());
				producer.send(message);
				log.info("[{}]: Message sent by producer...", getInstance());
			}
		} catch (final Exception e) {
			throw new MessagingException(e);
		}
		log.info("[{}]: Producer sequence finished!", getInstance());
	}

}
