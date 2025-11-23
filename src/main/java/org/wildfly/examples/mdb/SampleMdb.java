package org.wildfly.examples.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.wildfly.examples.flow.ErrorProcessor;
import org.wildfly.examples.flow.FlowProcessor;
import org.wildfly.examples.mdb.util.AbortException;
import org.wildfly.examples.mdb.util.BaseListener;
import org.wildfly.examples.mdb.util.StringMessage;

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
    public void process(StringMessage message) throws AbortException {
        messageProcessor.processMessage(message);
    }
}
