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
    @Query(value = "UPDATE GroupRequest set approved=true where id= :groupRequestId")
    void approveGroupRequest(Integer groupRequestId);

    @Modifying
    @Query(value = "UPDATE GroupRequest set approved=false where id= :groupRequestId")
    void declineGroupRequest(Integer groupRequestId);

    @Query(value = "SELECT g FROM GroupRequest r JOIN r.group g where r.id = :groupRequestId")
    Group findGroupByGroupRequestId(Integer groupRequestId);

    @Query(value = "SELECT u FROM GroupRequest r JOIN r.user u where r.id = :groupRequestId")
    User findUserByGroupRequestId(Integer groupRequestId);
}
