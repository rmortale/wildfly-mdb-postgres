package org.wildfly.examples.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.wildfly.examples.boundary.messaging.util.AbortException;
import org.wildfly.examples.boundary.messaging.util.Producer;
import org.wildfly.examples.boundary.messaging.util.StringMessage;
import org.wildfly.examples.entity.person.PersonRepository;

import java.util.Map;

@Stateless
@Slf4j
public class FlowProcessor {

    @EJB
    PersonRepository personRepository;
    @EJB
    Producer producer;

    public void processMessage(StringMessage message) throws AbortException {
        log.info("Processing Message {}", message);
        personRepository.createPerson(message.getMessageId(), message.getBody());
        producer.sendMessageWithProps(message.getBody(), "producer.queue.01", Map.of("hello", "world"));
        throw new AbortException("error occurred");
    }
}
