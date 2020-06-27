package br.edu.ufabc.chokitus.mq.benchmark;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractBenchmark {

	protected final BenchmarkConfiguration config;

	public abstract void doBenchmark();

	public InstanceInitializer getInstance() {
		return config.getMqInstance();
	}
}
