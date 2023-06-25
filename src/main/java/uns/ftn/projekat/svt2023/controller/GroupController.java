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

    @Autowired
    public GroupController(GroupService groupService,UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
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
    public void delete(@RequestParam Integer id) {
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

    @PostMapping("/{groupId}/members/{userId}")
    public Group addMemberToGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        Group group = groupService.findOne(groupId);
        User user = userService.findOne(userId);
        group.setMembers(groupService.getAllGroupMembers(groupId));
        group.getMembers().add(user);
        return groupService.save(group);
    }

    @PostMapping("/{groupId}/admins/{userId}")
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

    @GetMapping("/all")
    public List<Group> loadAll() {return this.groupService.findAll();}
}
