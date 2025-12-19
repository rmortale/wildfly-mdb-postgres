package org.wildfly.examples.boundary.messaging;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.wildfly.examples.boundary.messaging.util.AbortException;
import org.wildfly.examples.boundary.messaging.util.BaseListener;
import org.wildfly.examples.boundary.messaging.util.StringMessage;
import org.wildfly.examples.controller.ErrorProcessor;
import org.wildfly.examples.controller.FlowProcessor;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "mdb.from.queue.01"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "2"),
        @ActivationConfigProperty(propertyName = "singleConnection", propertyValue = "true")
})
@Slf4j
public class SampleMdb extends BaseListener {

    @EJB
    FlowProcessor messageProcessor;
    @EJB
    ErrorProcessor errorProcessor;

    @Override
    public void onError(Message message, Exception exception) {
        errorProcessor.processError(message, exception);
    }

    @Override
    public void onTextMessage(StringMessage message) throws AbortException {
        messageProcessor.processMessage(message);
    }
}
