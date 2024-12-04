package epicode.it.entity.evento;

import epicode.it.entity.location.Location;
import epicode.it.entity.partecipazione.Partecipazione;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data // aggiunge getter e setter automaticamente
@Entity // definisce la classe come entità
@Table(name = "eventi") // definisce il nome della tabella
public class Evento {
    @Id // definisce la chiave primaria
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // definisce la strategia di generazione del valore della chiave primaria
    private Long id;

    @Column(name="titolo", nullable = false, length = 50)
    private String titolo;

    @Column(name="data_evento", nullable = false)
    private Date dataEvento;

    @Column(name="descrizione", nullable = false)
    private String descrizione;

    @Column(name="tipo_evento", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @Column(name="num_max_partecipanti", nullable = false)
    private Integer numeroMaxPartecipanti;

    @ManyToOne // un evento può avere una sola location, una location può avere più eventi
    private Location location;

    @OneToMany (mappedBy = "evento") // un evento puo avere molti partecipanti, un partecipante puo avere molti eventi
    // mappedBy fa in modo che nella tabella partecipazione venga salvato l'id dell'evento
    private List<Partecipazione> partecipazioni = new ArrayList<>();
}
