package epicode.it.dao;

import epicode.it.entity.evento.Evento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EventoDAO {
    private EntityManager em;

    // costruttore con EntityManager come argomento
    public EventoDAO(EntityManager em) {
        this.em = em;
    }

    // salvo l'evento a db
    public void saveEvento(Evento evento) {
        this.em.getTransaction().begin();
        this.em.persist(evento);
        this.em.getTransaction().commit();
    }

    // per il salvataggio di più eventi
    public void saveEventi(List<Evento> eventi) {
        this.em.getTransaction().begin();
        eventi.forEach(e -> {
            this.em.persist(e);
        });
        this.em.getTransaction().commit();
    }

    // ottengo un evento da db tramite il suo id
    public Evento getEventoById(Long id) {
        return this.em.find(Evento.class, id);
    }


    // aggiorno un evento passato come argomento
    public void updateEvento(Evento evento) {
        this.em.getTransaction().begin();
        this.em.merge(evento);
        this.em.getTransaction().commit();
    }

    // aggiorno un evento passando id ed evento come argomento
    public void updateEvento(Long id, Evento evento) {
        Evento found = this.em.find(Evento.class, id);
        found.setTitolo(evento.getTitolo());
        found.setDataEvento(evento.getDataEvento());
        found.setDescrizione(evento.getDescrizione());
        found.setTipoEvento(evento.getTipoEvento());
        found.setNumeroMaxPartecipanti(evento.getNumeroMaxPartecipanti());
        this.em.getTransaction().begin();
        this.em.merge(found);
        this.em.getTransaction().commit();

    }

    // cancellazione di un evento tramite id
    public Evento deleteEvento(Long id) {
        Evento found = getEventoById(id);
        this.em.getTransaction().begin();
        this.em.remove(found);
        System.out.println("Evento removed: " + found);
        this.em.getTransaction().commit();
        return found;
    }

    // cancellazione di un evento tramite evento passato come argomento
    public void deleteEvento(Evento evento) {
        this.em.getTransaction().begin();
        this.em.remove(evento);
        this.em.getTransaction().commit();
    }

}
