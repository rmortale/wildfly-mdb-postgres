package org.wildfly.examples.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.wildfly.examples.entity.repository.PersonRepository;

@ApplicationScoped
public class GettingStartedService {

    @Inject
    PersonRepository personRepository;

    public String hello(String name) {
        return String.format("Hello '%s'.", name);
    }
}