package br.edu.ufabc.mq.benchmark.instances.pulsar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PulsarProperty {
	TOPIC_NAME("topic_name"), SERVICE_URL("service_url");

	final String value;
}
