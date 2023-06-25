package uns.ftn.projekat.svt2023.model.dto;

import lombok.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class GroupAdminDTO {
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private Integer groupId;
    public GroupAdminDTO(GroupAdmin createdGroupAdmin) {
        this.id = createdGroupAdmin.getId();
        this.username = createdGroupAdmin.getUser().getUsername();
    }
}
