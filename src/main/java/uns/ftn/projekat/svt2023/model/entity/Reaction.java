package uns.ftn.projekat.svt2023.model.entity;

import lombok.*;
import uns.ftn.projekat.svt2023.model.enums.*;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "reaction")
@NoArgsConstructor
@Getter
@Setter
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

    public Reaction(Integer id, ReactionType type, LocalDateTime timeStamp) {
        this.id = id;
        this.type = type;
        this.timeStamp = timeStamp;
    }
}
