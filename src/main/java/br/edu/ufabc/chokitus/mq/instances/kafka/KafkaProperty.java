package br.edu.ufabc.chokitus.mq.instances.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaProperty {
	TOPIC_LIST("topic_list");

	private final String value;
}
