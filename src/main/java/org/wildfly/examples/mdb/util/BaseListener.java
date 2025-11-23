package org.wildfly.examples.mdb.util;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseListener {

    public void processMessage(Message msg, StringMessageProcessor messageProcessor) {
        try {
            log.debug("Received JMS message with id: {}", msg.getJMSMessageID());
            if (msg instanceof TextMessage) {
                messageProcessor.processMessage(getTextMessage(msg));
            } else {
                log.warn("Message of wrong type: {}", msg.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private StringMessage getTextMessage(Message m) throws JMSException {
        return StringMessage.builder()
                .messageId(m.getJMSMessageID())
                .body(((TextMessage) m).getText())
                .headers(getHeaders(m))
                .build();
    }

    private Map<String, String> getHeaders(Message m) throws JMSException {
        Map<String, String> headers = new HashMap<>();
        Enumeration names = m.getPropertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            headers.put(name, m.getStringProperty(name));
        }
        return headers;
    }
}
