package uns.ftn.projekat.svt2023.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.util.*;

@RestController
@RequestMapping("api/groups")
public class GroupController {

    GroupService groupService;
    GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupService groupService,GroupRepository groupRepository ) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<GroupDTO> create(@RequestBody GroupDTO newGroup) {

        Group createdGroup = groupService.create(newGroup);

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

    @PutMapping(value = "{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GroupDTO> update(@PathVariable("id") Integer id, @RequestBody GroupDTO newGroup) {

        Group group = groupService.findOne(id);

        if(group == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        group.setDescription(newGroup.getDescription());
        group.setName(newGroup.getName());

        group = groupService.save(group);

        return new ResponseEntity<>(new GroupDTO(group),HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Group> loadAll() {return this.groupService.findAll();}
}