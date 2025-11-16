package org.wildfly.examples.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.wildfly.examples.entity.Person;

@Stateless
public class PersonRepository {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public void createPerson(String firstName, String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        em.persist(person);
    }
}
