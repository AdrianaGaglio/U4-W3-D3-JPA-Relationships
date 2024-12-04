package epicode.it;

import com.github.javafaker.Faker;
import epicode.it.dao.EventoDAO;
import epicode.it.dao.LocationDAO;
import epicode.it.dao.PartecipazioneDAO;
import epicode.it.dao.PersonaDAO;
import epicode.it.entity.evento.Evento;
import epicode.it.entity.evento.TipoEvento;
import epicode.it.entity.location.Location;
import epicode.it.entity.partecipazione.Partecipazione;
import epicode.it.entity.partecipazione.StatoPrenotazione;
import epicode.it.entity.persona.Persona;
import epicode.it.entity.persona.Sesso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ApplicazionWithMoreElements {

    public static void main(String[] args) {

        // generatore di dati
        Faker faker = new Faker(new Locale("it"));

        // connessione al db tramite persistence
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        // definisco EntityManager e istanzio le classi DAO per ogni entità
        EntityManager em = emf.createEntityManager();
        EventoDAO emEvento = new EventoDAO(em);
        LocationDAO emLocation = new LocationDAO(em);
        PartecipazioneDAO emPartecipazione = new PartecipazioneDAO(em);
        PersonaDAO emPersona = new PersonaDAO(em);

        // creo un array di eventi in maniera randomica
        List<Evento> eventi = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Evento evento = new Evento();
            evento.setTitolo(faker.lorem().fixedString(10));
            evento.setDataEvento(faker.date().future(30, TimeUnit.DAYS));
            evento.setDescrizione(faker.lorem().fixedString(100));
            evento.setTipoEvento(faker.random().nextInt(1, 2) == 1 ? TipoEvento.PUBBLICO : TipoEvento.PRIVATO);
            evento.setNumeroMaxPartecipanti(faker.random().nextInt(100, 1000));
            // per ogni evento creato, creo e aggiungo una location (salvo sia l'evento che la location sul db)
            Location location = new Location();
            location.setNome(faker.lorem().fixedString(10));
            location.setCitta(faker.nation().capitalCity());
            emLocation.saveLocation(location);
            evento.setLocation(location);
            emEvento.saveEvento(evento);
            eventi.add(evento);
        }

        // creo una lista di persone randomiche
        List<Persona> persone = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Persona persona = new Persona();
            persona.setNome(faker.name().firstName());
            persona.setCognome(faker.name().lastName());
            persona.setEmail(faker.internet().emailAddress());
            persona.setDataNascita(faker.date().birthday());
            persona.setSesso(faker.random().nextInt(1, 2) == 1 ? Sesso.M : Sesso.F);
            emPersona.savePersona(persona);
            persone.add(persona);
        }

        // creo una lista di partecipazioni randomica
        List<Partecipazione> partecipazioni = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Partecipazione partecipazione = new Partecipazione();
            partecipazione.setPersona(persone.get(faker.random().nextInt(0, 19)));
            partecipazione.setEvento(eventi.get(faker.random().nextInt(0, 19)));
            partecipazione.setStato(faker.random().nextInt(1, 2) == 1 ? StatoPrenotazione.CONFERMATA : StatoPrenotazione.DA_CONFERMARE);
            emPartecipazione.savePartecipazione(partecipazione);
            // per ogni partecipazione, ottengo l'evento corrispondente e aggiorno l'evento
            Evento evento = emEvento.getEventoById(partecipazione.getEvento().getId());
            evento.getPartecipazioni().add(partecipazione);
            emEvento.updateEvento(evento);
            // per ogni partecipazione, ottengo la persona corrispondente e aggiorno la persona
            Persona persona = emPersona.getPersonaById(partecipazione.getPersona().getId());
            persona.getPartecipazioni().add(partecipazione);
            emPersona.updatePersona(persona);
            partecipazioni.add(partecipazione);
        }

//        queste righe dovrebbero essere inutili avendo aggiornato all'interno del ciclo for precedente
//        em.getTransaction().begin();
//        for (int i = 0; i < eventi.size(); i++) {
//            em.merge(eventi.get(i));
//        }
//        em.getTransaction().commit();

        // ottengo un evento da db tramite id randomico tra 1 e 20
        Evento eventoDB = emEvento.getEventoById(faker.random().nextInt(1,20).longValue());
        // stampo l'evento con tutte le informazioni correlate (location, partecipazioni e persone)
        System.out.println(eventoDB);

        // ottengo una persona da db tramite id randomico tra 1 e 20
        Persona personaDB = emPersona.getPersonaById(faker.random().nextInt(1,20).longValue());
        // stampo la persona, senza informazioni correlate per via della notazione ToString.Eclude
        System.out.println(personaDB);

        // ottengo una partecipazione random da db tramite id randomico tra 1 e 20
        Partecipazione partecipazioneDB = emPartecipazione.getPartecipazioneById(faker.random().nextInt(1,50).longValue());
        // stampo la partecipazione, senza informazioni correlate per via della notazione ToString.Eclude
        System.out.println(partecipazioneDB);

        // ottengo una location random da db tramite id randomico tra 1 e 20
        Location locationDB = emLocation.getLocationById(faker.random().nextInt(1,20).longValue());
        // stampo la location, senza informazioni correlate per via della notazione ToString.Eclude
        System.out.println(locationDB);

        em.close();

    }
}
