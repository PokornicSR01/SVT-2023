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
    Set<User> getAllGroupMembers(Integer groupId);

    @Query(value = "SELECT a FROM Group g JOIN g.admins a WHERE g.id = :groupId")
    Set<User> getAllGroupAdmins(Integer groupId);

    @Query(value = "SELECT p FROM Post p WHERE p.group.id = :groupId")
    Set<Post> getAllGroupPosts(Integer groupId);

    @Query(value = "SELECT g FROM GroupRequest r JOIN r.group g WHERE r.approved = true AND r.user.id = :userId")
    Set<Group> getAllUserGroups(Integer userId);

    @Query(value = "SELECT r FROM GroupRequest r WHERE r.approved = null AND r.group.id = :groupId")
    Set<GroupRequest> getAllGroupRequests(Integer groupId);

    @Query(value = "SELECT g FROM Group g WHERE g.isSuspended = null")
    Set<Group> getAllGroupActiveGroups();

    @Modifying
    @Query(value = "UPDATE Group set suspendedReason =:suspendReason, isSuspended=1 where id= :groupId")
    void suspendGroup(Integer groupId, String suspendReason);

}
