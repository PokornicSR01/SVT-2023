package uns.ftn.projekat.svt2023.model;

import lombok.*;
import uns.ftn.projekat.svt2023.enums.*;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "report")
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private ReportReason reason;
    @Column
    private LocalDateTime timeStamp;
    @Column
    private Boolean accepted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User belongsTo;

    public Report(Integer id, ReportReason reason, LocalDateTime timeStamp, Boolean accepted) {
        this.id = id;
        this.reason = reason;
        this.timeStamp = timeStamp;
        this.accepted = accepted;
    }
}
