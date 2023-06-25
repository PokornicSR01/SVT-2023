package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin, Integer> {
    Optional<User> findUserById(Integer integer);
    Optional<Group> findGroupById(Integer integer);
}
