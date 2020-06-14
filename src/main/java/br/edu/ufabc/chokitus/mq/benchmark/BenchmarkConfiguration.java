package br.edu.ufabc.chokitus.mq.benchmark;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BenchmarkConfiguration {

	private InstanceInitializer mqInstance;
	private BenchmarkInitializer benchmarkInstance;

	private final Map<String, Object> generalProperties;
	private final Map<String, Object> producerProperties;
	private final Map<String, Object> consumerProperties;
	private final Map<String, Object> producerStartProperties;
	private final Map<String, Object> consumerStartProperties;
	private final Map<String, Object> messageProperties;
}
