package br.edu.ufabc.chokitus.mq.benchmark;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BenchmarkConfiguration {

	@JsonCreator(mode = Mode.PROPERTIES)
	public BenchmarkConfiguration(
			@JsonProperty("mqInstance")
			final InstanceInitializer mqInstance,

			@JsonProperty("benchmarkInstance")
			final BenchmarkInitializer benchmarkInstance,

			@JsonProperty("generalProperties")
			final Map<String, Object> generalProperties,

			@JsonProperty("producerProperties")
			final Map<String, Object> producerProperties,

			@JsonProperty("consumerProperties")
			final Map<String, Object> consumerProperties,

			@JsonProperty("producerStartProperties")
			final Map<String, Object> producerStartProperties,

			@JsonProperty("consumerStartProperties")
			final Map<String, Object> consumerStartProperties,

			@JsonProperty("messageProperties")
			final Map<String, Object> messageProperties
		) {

		this.mqInstance = mqInstance;
		this.benchmarkInstance = benchmarkInstance;
		this.generalProperties = generalProperties;
		this.producerProperties = producerProperties;
		this.consumerProperties = consumerProperties;
		this.producerStartProperties = producerStartProperties;
		this.consumerStartProperties = consumerStartProperties;
		this.messageProperties = messageProperties;
	}

	private final InstanceInitializer mqInstance;
	private final BenchmarkInitializer benchmarkInstance;

	private final Map<String, Object> generalProperties;
	private final Map<String, Object> producerProperties;
	private final Map<String, Object> consumerProperties;
	private final Map<String, Object> producerStartProperties;
	private final Map<String, Object> consumerStartProperties;
	private final Map<String, Object> messageProperties;

	public void initTest() {
		AbstractBenchmark benchmark = this.benchmarkInstance.buildBenchmark(this);
		benchmark.doBenchmark();
	}
}
