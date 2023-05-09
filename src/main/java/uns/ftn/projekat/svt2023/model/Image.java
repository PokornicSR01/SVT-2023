package uns.ftn.projekat.svt2023.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String path;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Image(){}
    public Image(Integer id, String path) {
        this.id = id;
        this.path = path;
    }
}
