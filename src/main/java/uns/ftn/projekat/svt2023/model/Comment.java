package uns.ftn.projekat.svt2023.model;


import lombok.*;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String text;
    @Column
    private LocalDateTime timeStamp;
    @Column
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User belongsTo;

    public Comment(Integer id, String text, LocalDateTime timeStamp, Boolean isDeleted) {
        this.id = id;
        this.text = text;
        this.timeStamp = timeStamp;
        this.isDeleted = isDeleted;
    }
}
