package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.transaction.*;
import java.util.*;

@Transactional
@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query(value = "SELECT m FROM GroupRequest g JOIN g.user m WHERE g.approved = true AND g.group.id = :groupId")
    Set<User> getAllGroupMemebrs(Integer groupId);

    @Query(value = "SELECT m FROM Group g JOIN g.admins m WHERE g.id = :groupId")
    Set<User> getAllGroupAdmins(Integer groupId);

    @Query(value = "SELECT p FROM Post p WHERE p.group.id = :groupId")
    Set<Post> getAllGroupPosts(Integer groupId);

    @Query(value = "SELECT u FROM GroupRequest g JOIN g.user u WHERE g.approved = true AND g.user.id = :userId")
    Set<Group> getAllUserGroups(Integer userId);

    @Query(value = "SELECT r FROM Group g JOIN g.groupRequests r WHERE r.approved = false AND g.id = :groupId")
    Set<GroupRequest> getAllGroupRequests(Integer groupId);

    @Modifying
    @Query(value = "UPDATE Group set isSuspended=1 where id= :groupId")
    void banGroup(Integer groupId);
}
