package br.edu.ufabc.chokitus.mq.benchmark;

import java.util.function.Function;

import br.edu.ufabc.chokitus.mq.benchmark.instances.GenericHelloWorld;
import br.edu.ufabc.chokitus.mq.benchmark.instances.single_message.MultiConsumerBenchmark;
import br.edu.ufabc.chokitus.mq.benchmark.instances.single_message.MultiProducerAndConsumerBenchmark;
import br.edu.ufabc.chokitus.mq.benchmark.instances.single_message.MultiProducerBenchmark;
import br.edu.ufabc.chokitus.mq.benchmark.instances.single_message.NumberedParallelBenchmark;
import br.edu.ufabc.chokitus.mq.benchmark.instances.single_message.NumberedSequentialBenchmark;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BenchmarkInitializer {
	HELLO_WORLD(GenericHelloWorld::new),
	NUMBERED_SEQUENTIAL(NumberedSequentialBenchmark::new),
	MULTI_CONSUMER(MultiConsumerBenchmark::new),
	MULTI_PRODUCER(MultiProducerBenchmark::new),
	MULTI_PRODUCER_CONSUMER(MultiProducerAndConsumerBenchmark::new),
	NUMBERED_PARALLEL(NumberedParallelBenchmark::new);

	private final Function<BenchmarkConfiguration, AbstractBenchmark> benchmarkFactory;

	public AbstractBenchmark buildBenchmark(final BenchmarkConfiguration config) {
		return benchmarkFactory.apply(config);
	}
}
