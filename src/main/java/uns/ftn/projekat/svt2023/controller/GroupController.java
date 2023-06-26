package uns.ftn.projekat.svt2023.controller;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import javax.persistence.criteria.*;
import java.util.*;

@RestController
@RequestMapping("api/groups")
public class GroupController {

    GroupService groupService;
    UserService userService;
    PostService postService;

    @Autowired
    public GroupController(GroupService groupService,UserService userService,
                           PostService postService) {
        this.groupService = groupService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<GroupDTO> create(@PathVariable Integer userId, @RequestBody GroupDTO newGroup) {

        User groupOwner = userService.findOne(userId);
        Group createdGroup = groupService.create(newGroup, groupOwner);

        if(createdGroup == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        GroupDTO groupDTO = new GroupDTO(createdGroup);

        return new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public void delete(@PathVariable Integer id) {
        Optional<Group> deletedGroup = groupService.delete(id);
    }

    @PutMapping("/edit")
    public ResponseEntity<GroupDTO> edit(@RequestBody @Validated GroupDTO editGroup){
        Group edit = groupService.findOne(editGroup.getId());
        edit.setDescription(editGroup.getDescription());
        edit.setName(editGroup.getName());
        groupService.save(edit);

        GroupDTO groupDTO = new GroupDTO(edit);
        return  new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{groupId}/members")
    public Set<User> getGroupMembers(@PathVariable Integer groupId) {
        Group group = groupService.findOne(groupId);
        group.setMembers(groupService.getAllGroupMembers(groupId));
        return group.getMembers();
    }

    @PostMapping("/{groupId}/members/{userId}/add")
    public Group addMemberToGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        Group group = groupService.findOne(groupId);
        User user = userService.findOne(userId);
        group.setMembers(groupService.getAllGroupMembers(groupId));
        group.getMembers().add(user);
        return groupService.save(group);
    }

    @PostMapping("/{groupId}/admins/{userId}/add")
    public Group addAdminToGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        Group group = groupService.findOne(groupId);
        User user = userService.findOne(userId);
        group.setAdmins(groupService.getAllGroupAdmins(groupId));
        group.getAdmins().add(user);
        return groupService.save(group);
    }

    @GetMapping("/{groupId}/admins")
    public Set<User> getGroupAdmins(@PathVariable Integer groupId) {
        Group group = groupService.findOne(groupId);
        group.setAdmins(groupService.getAllGroupAdmins(groupId));
        return group.getAdmins();
    }

    @GetMapping("/{groupId}/posts")
    public Set<Post> getGroupPosts(@PathVariable Integer groupId) {
        Group group = groupService.findOne(groupId);
        group.setPosts(groupService.getAllGroupPosts(groupId));
        return group.getPosts();
    }

    @GetMapping("/all")
    public List<Group> loadAll() {return this.groupService.findAll();}

    @PostMapping("/{groupId}/posts")
    public ResponseEntity<PostDTO> createGroupPost(@PathVariable Integer groupId, @RequestBody PostDTO newPost) {

        Post createdPost = postService.create(newPost, groupId);

        if(createdPost == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
    @GetMapping("/{groupId}/requests")
    public Set<GroupRequest> getGroupRequests(@PathVariable Integer groupId) {return groupService.getAllGroupRequests(groupId);}

    @PostMapping("/{groupId}/ban")
    public void banGroup(@PathVariable Integer groupId) {
        groupService.banGroup(groupId);
    }

}
