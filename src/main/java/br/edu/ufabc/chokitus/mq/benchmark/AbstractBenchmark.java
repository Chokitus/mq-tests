package br.edu.ufabc.chokitus.mq.benchmark;

import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public abstract class AbstractBenchmark {

	protected final BenchmarkConfiguration config;

	public void doBenchmark() {
		final var properties = config.getGeneralProperties();
		final var producerProperties = config.getProducerProperties();
		final var consumerProperties = config.getConsumerProperties();
		final var consumerStartProperties = config.getConsumerStartProperties();
		final var producerStartProperties = config.getProducerStartProperties();
		final var messageProperties = config.getMessageProperties();

		log.info("[{}]: All properties loaded, initializing WrapperFactory with generalProperties=[{}]...",
		         getInstance(),
		         properties);
		try (final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory = config.getMqInstance().getFactory(properties)) {
			log.info("[{}]: WrapperFactory initialized! [{}]", getInstance(), wrapperFactory);
			doProducer(producerProperties, producerStartProperties, messageProperties, wrapperFactory);
			doConsumer(consumerProperties, consumerStartProperties, wrapperFactory);
		} catch (final Exception e) {
			log.error("Error during execution!", e);
		}
		log.info("[{}]: Benchmark is over!", getInstance());
	}

	protected abstract void doConsumer(final ConfigurationProperties consumerProperties,
	                                   final ConfigurationProperties consumerStartProperties,
	                                   final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory);

	protected abstract void doProducer(final ConfigurationProperties producerProperties,
	                                   final ConfigurationProperties producerStartProperties,
	                                   final ConfigurationProperties messageProperties,
	                                   final AbstractWrapperFactory<?, ?, ?, ?> wrapperFactory);

	public InstanceInitializer getInstance() {
		return config.getMqInstance();
	}
}
