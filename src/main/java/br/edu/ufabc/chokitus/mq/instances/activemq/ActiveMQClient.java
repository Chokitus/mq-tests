package br.edu.ufabc.chokitus.mq.instances.activemq;

import org.apache.activemq.artemis.api.core.client.ClientSession;

public interface ActiveMQClient {
	ClientSession getSession();
}
