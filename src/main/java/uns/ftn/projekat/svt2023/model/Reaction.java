package uns.ftn.projekat.svt2023.model;

import uns.ftn.projekat.svt2023.enums.*;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private ReactionType type;
    @Column
    private LocalDateTime timeStamp;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User madeBy;

    public Reaction(){}
    public Reaction(Integer id, ReactionType type, LocalDateTime timeStamp) {
        this.id = id;
        this.type = type;
        this.timeStamp = timeStamp;
    }
}
