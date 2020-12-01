package br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PulsarProperty {
	TOPIC_NAME("topic_name"), SERVICE_URL("service_url"), SUBSCRIPTION_NAME("subscription_name");

	final String value;
}
