package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.time.*;

@Service
public class GroupRequestServiceImpl implements GroupRequestService {

    @Autowired
    private GroupRequestRepository groupRequestRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Override
    public GroupRequest create(Integer userId, Integer groupId) {
        Group group = groupService.findOne(groupId);
        User user = userService.findOne(userId);

        if(group == null || user == null) {
            return null;
        }

        GroupRequest groupRequest = new GroupRequest();

        groupRequest.setGroup(group);
        groupRequest.setUser(user);
        groupRequest.setApproved(true);
        groupRequest.setCreated(LocalDateTime.now());

        return groupRequestRepository.save(groupRequest);
    }

    @Override
    public void approveRequest(Integer requestId) {groupRequestRepository.approveGroupRequest(requestId);}

    @Override
    public void declineRequest(Integer requestId) {groupRequestRepository.declineGroupRequest(requestId);}
}
