package br.edu.ufabc.chokitus.mq.instances.ironmq;

import java.util.Map;

import io.iron.ironmq.Client;
import io.iron.ironmq.Queue;

public interface IronMQClient {
	Client getClient();
	Map<String, Queue> getQueues();

	default Queue getQueue(final String queueName) {
		return getQueues().computeIfAbsent(queueName, k -> getClient().queue(queueName));
	}

}
