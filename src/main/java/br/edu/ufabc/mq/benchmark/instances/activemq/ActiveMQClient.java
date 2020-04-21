package br.edu.ufabc.mq.benchmark.instances.activemq;

import org.apache.activemq.artemis.api.core.client.ClientSession;

public interface ActiveMQClient {
	ClientSession getSession();
}
