package org.wildfly.examples.boundary.messaging.util;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class BaseListener implements MessageListener {

    public abstract void onError(Message message, Exception exception);

    public abstract void onTextMessage(StringMessage message) throws AbortException;

    @Override
    public void onMessage(Message message) {
        try {
            log.debug("Received JMS message with id: {}", message.getJMSMessageID());
            if (message instanceof TextMessage) {
                onTextMessage(convertToStringMessage(message));
            } else {
                log.warn("Message of wrong type: {}", message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (AbortException e) {
            onError(message, e);
        }
    }

    private StringMessage convertToStringMessage(Message m) throws JMSException {
        return StringMessage.builder()
                .messageId(m.getJMSMessageID())
                .timestamp(m.getJMSTimestamp())
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
