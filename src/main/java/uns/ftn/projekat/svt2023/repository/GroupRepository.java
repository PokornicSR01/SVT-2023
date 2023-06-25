package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query(value = "SELECT m FROM Group g JOIN g.members m WHERE g.id = :groupId")
    Set<User> getAllGroupMemebrs(Integer groupId);

    @Query(value = "SELECT m FROM Group g JOIN g.admins m WHERE g.id = :groupId")
    Set<User> getAllGroupAdmins(Integer groupId);
}
