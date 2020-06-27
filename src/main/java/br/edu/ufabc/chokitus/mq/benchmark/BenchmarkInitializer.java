package br.edu.ufabc.chokitus.mq.benchmark;

import java.util.function.Function;

import br.edu.ufabc.chokitus.mq.benchmark.instances.GenericHelloWorld;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BenchmarkInitializer {
	HELLO_WORLD(config -> new GenericHelloWorld(config));

	private final Function<BenchmarkConfiguration, AbstractBenchmark> benchmarkFactory;

	public AbstractBenchmark buildBenchmark(final BenchmarkConfiguration config) {
		return benchmarkFactory.apply(config);
	}
}
