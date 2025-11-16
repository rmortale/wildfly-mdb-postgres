package org.wildfly.examples.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.wildfly.examples.producer.Producer;
import org.wildfly.examples.repository.PersonRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/HELLOWORLDMDBQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "15"),
        @ActivationConfigProperty(propertyName = "singleConnection", propertyValue = "true")
})
public class HelloWorld implements MessageListener {
    private static final Logger LOGGER = Logger.getLogger(HelloWorld.class.toString());

    @EJB
    PersonRepository personRepository;

    @EJB
    Producer producer;

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOGGER.log(Level.INFO, "Received Message from queue: {0}", msg.getText());
                personRepository.createPerson(msg.getText(), msg.getText());
                producer.sendMessage(msg.getText(), "mdb.queue.01");
                //throw new RuntimeException("Should not reach here");
            } else {
                LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
