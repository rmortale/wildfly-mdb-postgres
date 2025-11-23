package org.wildfly.examples.flow;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.wildfly.examples.mdb.util.Producer;
import org.wildfly.examples.mdb.util.StringMessage;
import org.wildfly.examples.mdb.util.StringMessageProcessor;
import org.wildfly.examples.repository.PersonRepository;

import java.util.Map;

@Stateless
@Slf4j
public class FlowProcessor implements StringMessageProcessor {

    @EJB
    PersonRepository personRepository;
    @EJB
    Producer producer;

    @Override
    public void processMessage(StringMessage message) {
        log.info("Processing Message {}", message);
        personRepository.createPerson(message.getMessageId(), message.getBody());
        producer.sendMessageWithProps(message.getBody(), "producer.queue.01", Map.of("hello", "world"));
        //throw new NullPointerException("Message is null");
    }
}
