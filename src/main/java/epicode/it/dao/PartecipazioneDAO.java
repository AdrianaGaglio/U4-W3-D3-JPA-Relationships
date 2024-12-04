package epicode.it.dao;

import epicode.it.entity.partecipazione.Partecipazione;
import jakarta.persistence.EntityManager;

public class PartecipazioneDAO {

    private EntityManager em;

    // costruttore con EntityManager come argomento
    public PartecipazioneDAO(EntityManager em) {
        this.em = em;
    }

    // salvo partecipazione a db
    public void savePartecipazione(Partecipazione partecipazione) {
        this.em.getTransaction().begin();
        this.em.persist(partecipazione);
        this.em.getTransaction().commit();
    }

    // ottengo partecipazione da db tramite id
    public Partecipazione getPartecipazioneById(Long id) {
        return this.em.find(Partecipazione.class, id);
    }

    // cancello partecipazione da db tramite id
    public void deletePartecipazione(int id) {
        Partecipazione found = this.em.find(Partecipazione.class,id);
        this.em.getTransaction().begin();
        this.em.remove(found);
        this.em.getTransaction().commit();
    }

    // aggiorno partecipazione tramite partecipazione passata come argomento
    public void updatePartecipazione(Partecipazione partecipazione) {
        this.em.getTransaction().begin();
        this.em.merge(partecipazione);
        this.em.getTransaction().commit();
    }
}
