package org.wildfly.examples.entity.error;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
public class ErrorMessage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 4096)
    private String message;
    private String errorMessage;
    @Column(length = 8192)
    private String exception;
    private OffsetDateTime createDateTime;

}
