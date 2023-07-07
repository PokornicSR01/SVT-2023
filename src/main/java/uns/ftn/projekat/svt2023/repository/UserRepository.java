package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.transaction.*;
import java.util.*;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE concat(u.firstName,' ', u.lastName) LIKE %:name% OR concat(u.lastName, ' ', u.firstName) LIKE %:name%")
    Set<User> searchUsersByName(String name);

    @Query(value = "SELECT p FROM Post p WHERE p.group = null")
    Set<Post> getNonGroupPosts();

    @Query(value = "SELECT u FROM FriendRequest r JOIN r.fromUser u WHERE r.approved = true AND r.toUser.id = :userId")
    Set<User> getAllFriends(Integer userId);

    @Query(value = "SELECT u FROM FriendRequest r JOIN r.toUser u WHERE r.approved = true AND r.fromUser.id = :userId")
    Set<User> getAllFriends1(Integer userId);
}
