package org.wildfly.examples.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.wildfly.examples.boundary.messaging.util.Producer;
import org.wildfly.examples.entity.error.ErrorRepository;

@Stateless
@Slf4j
public class ErrorProcessor {

    @EJB
    ErrorRepository errorRepository;
    @EJB
    Producer producer;

    public void processError(Message message, Exception exception) {
        log.error("Error processing JMS Message", exception);
        Long errorId = errorRepository.saveError(message, exception);
        producer.sendMessage("Error occured. Saved with id: " + errorId, "error.queue.01");
    }
}
