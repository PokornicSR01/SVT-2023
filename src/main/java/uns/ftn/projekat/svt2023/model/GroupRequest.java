package uns.ftn.projekat.svt2023.model;

import lombok.*;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "groupRequest")
@NoArgsConstructor
@Getter
@Setter
public class GroupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Boolean approved;
    @Column
    private LocalDateTime created;
    @Column
    private LocalDateTime at;

    public GroupRequest(Integer id, Boolean approved, LocalDateTime created, LocalDateTime at) {
        this.id = id;
        this.approved = approved;
        this.created = created;
        this.at = at;
    }
}
