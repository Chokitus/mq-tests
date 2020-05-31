package br.edu.ufabc.chokitus.mq.instances.ironmq;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IronMQProperty {
	QUEUE_NAME("qname"), URL("url"), TOKEN("token"), PROJECT_ID("project_id");

	private final String value;
}
