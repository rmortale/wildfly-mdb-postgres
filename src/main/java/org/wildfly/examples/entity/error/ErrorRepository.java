package org.wildfly.examples.entity.error;

import jakarta.ejb.Stateless;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;

@Stateless
public class ErrorRepository {

    private static final int ERROR_MESSAGE_MAX_LENGTH = 8192;

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public Long saveError(Message message, Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCreateDateTime(OffsetDateTime.now());
        errorMessage.setErrorMessage(exception.getMessage());
        try {
            errorMessage.setMessage(((TextMessage) message).getText());
        } catch (JMSException e) {
            errorMessage.setMessage(e.getMessage());
        }
        errorMessage.setException(stackTraceToString(exception));
        em.persist(errorMessage);
        return errorMessage.getId();
    }

    private String stackTraceToString(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return truncateString(stringWriter.toString(), ERROR_MESSAGE_MAX_LENGTH);
    }

    private String truncateString(String string, int maxLength) {
        if (string.length() <= maxLength) {
            return string;
        } else {
            return string.substring(0, maxLength);
        }
    }
}
