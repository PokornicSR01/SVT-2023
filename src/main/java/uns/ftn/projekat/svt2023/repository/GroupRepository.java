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

    @Query(value = "SELECT p FROM Post p WHERE p.group.id = :groupId")
    Set<Post> getAllGroupPosts(Integer groupId);

    @Query(value = "SELECT g FROM User u JOIN u.groups g WHERE u.id = :userId")
    Set<Group> getAllUserGroups(Integer userId);
}
