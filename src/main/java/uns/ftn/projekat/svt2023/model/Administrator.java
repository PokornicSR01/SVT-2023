package uns.ftn.projekat.svt2023.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "administrator")
@Getter
@Setter
public class Administrator extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Banned> bans = new HashSet<Banned>();

    public Administrator() {
    }

}
