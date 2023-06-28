package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private GroupRequestService groupRequestService;

    @Override
    public Group create(GroupDTO groupDTO) {
        User groupOwner = userService.returnLoggedUser();

        Set<User> admins = new HashSet<User>();
        admins.add(groupOwner);

        Group newGroup = new Group();
        newGroup.setCreationDate(LocalDateTime.now());
        newGroup.setId(groupDTO.getId());
        newGroup.setName(groupDTO.getName());
        newGroup.setDescription(groupDTO.getDescription());
        newGroup.setIsSuspended(groupDTO.getIsSuspended());
        newGroup.setSuspendedReason("");
        newGroup.setAdmins(admins);
        newGroup = groupRepository.save(newGroup);

        GroupRequest groupRequest = groupRequestService.create(newGroup.getId());
        groupRequestService.approveRequest(groupRequest.getId());

        return newGroup;
    }

    @Override
    public Group save(Group group) {return groupRepository.save(group);}

    @Override
    public Optional<Group> delete(Integer id) {
        Optional<Group> deletedGroup = groupRepository.findById(id);
        if(deletedGroup == null) {
            return null;
        }
        groupRepository.deleteById(id);

        return deletedGroup;
    }

    @Override
    public Group findOne(Integer id) {return groupRepository.findById(id).orElseGet(null);}

    @Override
    public List<Group> findAll() {return groupRepository.findAll();}

    @Override
    public Set<User> getAllGroupMembers(Integer id) {return groupRepository.getAllGroupMemebrs(id);}

    @Override
    public Set<User> getAllGroupAdmins(Integer id) {return groupRepository.getAllGroupAdmins(id);}

    @Override
    public Set<Post> getAllGroupPosts(Integer id) {return groupRepository.getAllGroupPosts(id);}

    @Override
    public Set<GroupRequest> getAllGroupRequests(Integer id) {return groupRepository.getAllGroupRequests(id);}

    @Override
    public Set<Group> findUserGroups(Integer userId) {return groupRepository.getAllUserGroups(userId);}

    @Override
    public void banGroup(Integer groupId) {groupRepository.banGroup(groupId);}

    @Override
    public void addAdminToGroup(Integer groupId, Integer userId) {
        Group group = this.findOne(groupId);
        User user = userService.findOne(userId);

        group.setAdmins(this.getAllGroupAdmins(groupId));
        group.getAdmins().add(user);

        this.save(group);
    }
}
