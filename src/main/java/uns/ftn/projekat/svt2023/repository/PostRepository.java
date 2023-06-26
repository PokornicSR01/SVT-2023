package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.transaction.*;
import java.util.*;

@Transactional
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByGroup(Group group);
}
