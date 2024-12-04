package epicode.it.dao;

import epicode.it.entity.persona.Persona;
import jakarta.persistence.EntityManager;

public class PersonaDAO {
    private EntityManager em;

    // costruttore con EntityManager come argomento
    public PersonaDAO(EntityManager em) {
        this.em = em;
    }

    // salvo persona a db
    public void savePersona(Persona persona) {
        this.em.getTransaction().begin();
        this.em.persist(persona);
        this.em.getTransaction().commit();
    }

    // ottengo persona da db tramite id
    public Persona getPersonaById(Long id) {
        return this.em.find(Persona.class, id);
    }

    // cancello persona da db tramite id
    public void deletePersona(int id) {
        Persona found = this.em.find(Persona.class, id);
        this.em.getTransaction().begin();
        this.em.remove(found);
        this.em.getTransaction().commit();
    }

    // aggiorno persona tramite persona passata come argomento
    public void updatePersona(Persona persona) {
        Persona found = this.em.find(Persona.class, persona.getId());
        found.setNome(persona.getNome());
        found.setCognome(persona.getCognome());
        found.setEmail(persona.getEmail());
        found.setDataNascita(persona.getDataNascita());
        this.em.getTransaction().begin();
        this.em.merge(found);
        this.em.getTransaction().commit();
    }
}
