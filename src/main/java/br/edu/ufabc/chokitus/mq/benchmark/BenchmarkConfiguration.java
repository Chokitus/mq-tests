package br.edu.ufabc.chokitus.mq.benchmark;

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

			@JsonProperty("benchmarkProperties")
			final ConfigurationProperties benchmarkProperties,

			@JsonProperty("generalProperties")
			final ConfigurationProperties generalProperties,

			@JsonProperty("producerProperties")
			final ConfigurationProperties producerProperties,

			@JsonProperty("consumerProperties")
			final ConfigurationProperties consumerProperties,

			@JsonProperty("producerStartProperties")
			final ConfigurationProperties producerStartProperties,

			@JsonProperty("consumerStartProperties")
			final ConfigurationProperties consumerStartProperties,

			@JsonProperty("messageProperties")
			final ConfigurationProperties messageProperties
		) {

		this.mqInstance = mqInstance;
		this.benchmarkInstance = benchmarkInstance;
		this.generalProperties = generalProperties;
		this.producerProperties = producerProperties;
		this.consumerProperties = consumerProperties;
		this.producerStartProperties = producerStartProperties;
		this.consumerStartProperties = consumerStartProperties;
		this.messageProperties = messageProperties;

		this.benchmarkProperties = benchmarkProperties;
	}

	private final InstanceInitializer mqInstance;
	private final BenchmarkInitializer benchmarkInstance;

	private final ConfigurationProperties benchmarkProperties;

	private final ConfigurationProperties generalProperties;
	private final ConfigurationProperties producerProperties;
	private final ConfigurationProperties consumerProperties;
	private final ConfigurationProperties producerStartProperties;
	private final ConfigurationProperties consumerStartProperties;
	private final ConfigurationProperties messageProperties;

	public void initTest() {
		this.benchmarkInstance.buildBenchmark(this).doBenchmark();
	}
}
