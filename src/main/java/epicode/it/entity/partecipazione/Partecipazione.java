package epicode.it.entity.partecipazione;


import epicode.it.entity.evento.Evento;
import epicode.it.entity.persona.Persona;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data // generazione automatica di getter e setter
@Entity // rende la classe una tabella
@Table (name = "partecipazioni") // nome tabella
public class Partecipazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne // la partecipazione fa riferimento a una sola persona, una persona puo avere molte partecipazioni
    private Persona persona;

    @ManyToOne // la partecipazione fa riferimento a un solo evento, un evento puo avere molte partecipazioni
    @ToString.Exclude
    @JoinColumn (name="evento_id")
    private Evento evento;

    @Enumerated(EnumType.STRING)
    private StatoPrenotazione stato;
}
