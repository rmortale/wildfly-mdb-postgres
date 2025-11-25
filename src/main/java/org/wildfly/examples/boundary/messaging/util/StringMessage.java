package org.wildfly.examples.boundary.messaging.util;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Builder
@Getter
@ToString
public class StringMessage {

    private final String messageId;
    private final long timestamp;
    private final String body;
    private final Map<String, String> headers;
}
