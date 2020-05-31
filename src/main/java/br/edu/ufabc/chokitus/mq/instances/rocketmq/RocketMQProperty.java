package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RocketMQProperty {
	GROUP_NAME("groupName"),
	NAME_SERVER_ADDRESS("name_servr_address"),
	TOPIC_NAME("topic_name");

	final String value;
}
