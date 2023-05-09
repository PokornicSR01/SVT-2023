package uns.ftn.projekat.svt2023.model;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "friendRequest")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Boolean approved;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime at;

    public FriendRequest(){}
    public FriendRequest(Integer id, Boolean approved, LocalDateTime createdAt, LocalDateTime at) {
        this.id = id;
        this.approved = approved;
        this.createdAt = createdAt;
        this.at = at;
    }
}
