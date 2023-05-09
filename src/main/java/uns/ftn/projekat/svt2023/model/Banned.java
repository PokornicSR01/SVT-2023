package uns.ftn.projekat.svt2023.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.*;

@Entity
@Table(name = "banned")
public class Banned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDateTime timeStamp;
    @ManyToOne(fetch = FetchType.EAGER)
    private Administrator administrator;

    public Banned(){}
    public Banned(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
