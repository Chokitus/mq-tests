package br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client;

import org.apache.activemq.artemis.api.core.client.ClientSession;

public interface ActiveMQClient {
	ClientSession getSession();
}
