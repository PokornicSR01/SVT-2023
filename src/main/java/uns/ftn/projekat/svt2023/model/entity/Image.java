package uns.ftn.projekat.svt2023.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "image")
@NoArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String path;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Image(Integer id, String path) {
        this.id = id;
        this.path = path;
    }

}
