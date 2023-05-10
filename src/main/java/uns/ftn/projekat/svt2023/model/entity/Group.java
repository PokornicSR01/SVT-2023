package uns.ftn.projekat.svt2023.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "group")
@NoArgsConstructor
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private LocalDateTime creationDate;
    @Column
    private Boolean isSuspended;
    @Column
    private String suspendedReason;

    public Group(Integer id, String name, String description, LocalDateTime creationDate, Boolean isSuspended, String suspendedReason) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isSuspended = isSuspended;
        this.suspendedReason = suspendedReason;
    }
}
