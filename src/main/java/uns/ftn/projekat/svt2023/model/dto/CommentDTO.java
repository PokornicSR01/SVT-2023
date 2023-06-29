package uns.ftn.projekat.svt2023.model.dto;

import lombok.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private Integer id;
    @NotBlank
    private String text;
    private LocalDateTime timeStamp;
    private Boolean isDeleted;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.timeStamp = comment.getTimeStamp();
        this.isDeleted = comment.getIsDeleted();
    }

}
