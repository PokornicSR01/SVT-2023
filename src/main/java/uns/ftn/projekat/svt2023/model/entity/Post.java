package uns.ftn.projekat.svt2023.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String content;
    @Column
    private LocalDateTime creationDate;
    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToOne
    @JsonIgnore
    private Group group;

    public Post(Integer id, String content, LocalDateTime creationDate) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
    }

}
