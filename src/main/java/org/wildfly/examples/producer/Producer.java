package org.wildfly.examples.producer;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;

@Stateless
public class Producer {

    @Inject
    @JMSConnectionFactory("java:/JmsXA")
    private JMSContext jmsContext;

    public void sendMessage(String text, String queueName) {
        jmsContext.createProducer().send(jmsContext.createQueue(queueName), text);
    }
}
