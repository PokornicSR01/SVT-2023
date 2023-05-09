package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.*;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
