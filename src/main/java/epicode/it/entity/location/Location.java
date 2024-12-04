package epicode.it.entity.location;


import epicode.it.entity.evento.Evento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data // per utilizzare i metodi getter e setter
@Entity // per indicare che questa classe rappresenta una tabella
@Table(name="location") // per indicare il nome della tabella
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="nome", nullable = false, length = 100)
    private String nome;

    @Column(name="citta",nullable = false, length = 50)
    private String citta;

    @OneToMany (mappedBy = "location") // un location puo avere molti eventi
    // mappedBy fa in modo che l'id della location venga riportato come FK nella tabella eventi
    @ToString.Exclude
    private List<Evento> eventi = new ArrayList<>();
}
