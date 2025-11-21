package org.wildfly.examples.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.wildfly.examples.mdb.util.BaseListener;
import org.wildfly.examples.mdb.util.StringMessageProcessor;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "mdb.from.queue.01"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "10"),
        @ActivationConfigProperty(propertyName = "singleConnection", propertyValue = "true")
})
@Slf4j
public class HelloWorld extends BaseListener implements MessageListener {

    @EJB
    StringMessageProcessor messageProcessor;

    public void onMessage(Message m) {
        processMessage(m, messageProcessor);
    }


}
