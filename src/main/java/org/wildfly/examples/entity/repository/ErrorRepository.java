package org.wildfly.examples.entity.repository;

import jakarta.ejb.Stateless;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.wildfly.examples.entity.ErrorMessage;

import java.io.PrintWriter;
import java.io.StringWriter;

@Stateless
public class ErrorRepository {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public Long saveError(Message message, Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        try {
            errorMessage.setMessage(((TextMessage) message).getText());
        } catch (JMSException e) {
            errorMessage.setMessage(e.getMessage());
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        errorMessage.setException(truncateString(stringWriter.toString(), 8192));
        em.persist(errorMessage);
        return errorMessage.getId();
    }

    private String truncateString(String string, int maxLength) {
        if (string.length() <= maxLength) {
            return string;
        } else {
            return string.substring(0, maxLength);
        }
    }
}
