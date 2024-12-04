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

public class Application {

    public static void main(String[] args) {

        // generatore di dati
        Faker faker = new Faker(new Locale("it"));

        // connessione al db - unit-jpa è la sezione del persistence.xml dove sono contenute le mie informazioni
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        // mi permette di interagire con il db
        EntityManager em = emf.createEntityManager();
        // mi permette di interagire con le mie entità
        EventoDAO emEvento = new EventoDAO(em);
        LocationDAO emLocation = new LocationDAO(em);
        PartecipazioneDAO emPartecipazione = new PartecipazioneDAO(em);
        PersonaDAO emPersona = new PersonaDAO(em);

        // creo una location
        Location location = new Location();
        location.setNome(faker.lorem().fixedString(10));
        location.setCitta(faker.nation().capitalCity());
        // salvo la location
        emLocation.saveLocation(location);

        // creo un evento
        Evento evento = new Evento();
        evento.setTitolo(faker.lorem().fixedString(10));
        evento.setDataEvento(faker.date().future(30,TimeUnit.DAYS));
        evento.setDescrizione(faker.lorem().fixedString(100));
        evento.setTipoEvento(faker.random().nextInt(1,2) == 1 ? TipoEvento.PUBBLICO : TipoEvento.PRIVATO);
        evento.setNumeroMaxPartecipanti(faker.random().nextInt(100,1000));
        evento.setLocation(location);
        // salvo l'evento
        emEvento.saveEvento(evento);

        // creo una persona
        Persona persona1 = new Persona();
        persona1.setNome(faker.name().firstName());
        persona1.setCognome(faker.name().lastName());
        persona1.setEmail(faker.internet().emailAddress());
        persona1.setDataNascita(faker.date().birthday());
        persona1.setSesso(faker.random().nextInt(1,2) == 1 ? Sesso.M : Sesso.F);
        // salvo la persona a db
        emPersona.savePersona(persona1);

        // creo un'altra persona
        Persona persona2 = new Persona();
        persona2.setNome(faker.name().firstName());
        persona2.setCognome(faker.name().lastName());
        persona2.setEmail(faker.internet().emailAddress());
        persona2.setDataNascita(faker.date().birthday());
        persona2.setSesso(faker.random().nextInt(1,2) == 1 ? Sesso.M : Sesso.F);
        // la salvo a db
        emPersona.savePersona(persona2);

        // creo una partecipazione
        Partecipazione partecipazione1 = new Partecipazione();
        partecipazione1.setEvento(evento);
        partecipazione1.setPersona(persona1);
        partecipazione1.setStato(faker.random().nextInt(1,2) == 1 ? StatoPrenotazione.CONFERMATA : StatoPrenotazione.DA_CONFERMARE);
        // la salvo a db
        emPartecipazione.savePartecipazione(partecipazione1);

        // creo una seconda partecipazione
        Partecipazione partecipazione2 = new Partecipazione();
        partecipazione2.setEvento(evento);
        partecipazione2.setPersona(persona2);
        partecipazione2.setStato(faker.random().nextInt(1,2) == 1 ? StatoPrenotazione.CONFERMATA : StatoPrenotazione.DA_CONFERMARE);
        // la salvo a db
        emPartecipazione.savePartecipazione(partecipazione2);

    }
}