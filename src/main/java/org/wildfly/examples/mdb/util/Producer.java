package org.wildfly.examples.mdb.util;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;

import java.util.Map;

@Stateless
public class Producer {

    @Inject
    @JMSConnectionFactory("java:/JmsXA")
    private JMSContext jmsContext;

    public void sendMessage(String text, String queueName) {
        jmsContext.createProducer().send(jmsContext.createQueue(queueName), text);
    }

    public void sendMessageWithProps(String text, String queueName, Map<String, String> props) {
        JMSProducer producer = jmsContext.createProducer();
        props.forEach(producer::setProperty);
        producer.send(jmsContext.createQueue(queueName), text);
    }
}
