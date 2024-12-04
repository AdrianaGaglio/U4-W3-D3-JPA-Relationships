package epicode.it.entity.persona;

import epicode.it.entity.partecipazione.Partecipazione;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data // getter e setter aumatico
@Entity // rende la classe una tabella
@Table(name = "persone") // nome della tabella
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String cognome;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(name = "data_nascita", nullable = false)
    private Date dataNascita;

    @Column(name = "sesso", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sesso sesso;

    @OneToMany (mappedBy = "persona") // un persona puo avere più partecipazioni a più eventi
    // mappedBy fa in modo che nella tabella partecipazione venga salvato l'id della persona
    @ToString.Exclude
    private List<Partecipazione> partecipazioni = new ArrayList<>();
}
