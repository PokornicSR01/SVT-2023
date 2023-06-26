package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.transaction.*;

@Transactional
@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
}
