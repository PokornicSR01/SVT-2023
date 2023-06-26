package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import javax.swing.text.html.*;
import java.util.*;

public interface GroupService {
    Group create(GroupDTO groupDTO, User groupOwner);
    Group save(Group group);
    Optional<Group> delete(Integer id);
    Group findOne(Integer id);
    List<Group> findAll();
    Set<User> getAllGroupMembers(Integer id);
    Set<User> getAllGroupAdmins(Integer id);
    Set<Post> getAllGroupPosts(Integer id);
    Set<GroupRequest> getAllGroupRequests(Integer id);
    Set<Group> findUserGroups(Integer userId);
    void banGroup(Integer groupId);
}
