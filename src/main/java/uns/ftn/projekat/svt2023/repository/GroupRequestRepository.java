package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.transaction.*;
import java.util.*;

@Transactional
@Repository
public interface GroupRequestRepository extends JpaRepository<GroupRequest, Integer> {
    @Modifying
    @Query(value = "UPDATE GroupRequest set approved=1 where id= :groupRequestId")
    void approveGroupRequest(Integer groupRequestId);

    @Modifying
    @Query(value = "DELETE GroupRequest where id= :groupRequestId")
    void declineGroupRequest(Integer groupRequestId);
}
