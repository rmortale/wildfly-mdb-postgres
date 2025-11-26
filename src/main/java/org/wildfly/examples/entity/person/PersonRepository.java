package org.wildfly.examples.entity.person;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
