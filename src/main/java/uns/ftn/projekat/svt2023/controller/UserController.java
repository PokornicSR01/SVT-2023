package uns.ftn.projekat.svt2023.controller;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.model.entity.User;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.security.*;
import uns.ftn.projekat.svt2023.service.*;
import uns.ftn.projekat.svt2023.service.implementation.*;

import javax.persistence.criteria.*;
import javax.servlet.http.*;
import javax.transaction.*;
import java.security.*;
import java.time.*;
import java.util.*;

@Transactional
@RestController
@RequestMapping("api/users")
public class UserController
{
    UserService userService;
    GroupService groupService;
    PostService postService;
    ReactionService reactionService;
    GroupRequestService groupRequestService;
    UserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;
    TokenUtils tokenUtils;
    PasswordEncoder encoder;

    @Autowired
    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, TokenUtils tokenUtils, PasswordEncoder encoder,
                          GroupService groupService, PostService postService, ReactionService reactionService,
                          GroupRequestService groupRequestService){
        this.userService = userService;
        this.groupService = groupService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
        this.encoder = encoder;
        this.postService = postService;
        this.reactionService = reactionService;
        this.groupRequestService = groupRequestService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> create (@RequestBody @Validated UserDTO newUser) {
        User createdUser = userService.create(newUser);

        if(createdUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIm = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIm));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        User user = userService.findOne(id);

        if(user != null) {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDTO> changePassword (@RequestBody @Validated PasswordDTO passwords) {
        User foundUser = userService.findByUsername(passwords.getUsername());

        if(encoder.matches(passwords.getCurrent(), foundUser.getPassword()) && passwords.getConfirm().equals(passwords.getPassword())){
            foundUser.setPassword(encoder.encode(passwords.getPassword()));
            userService.save(foundUser);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        UserDTO userDTO = new UserDTO(foundUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> loadAll() {
        return this.userService.findAll();
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }

    @GetMapping("/{userId}/posts")
    public List<Post> getUserPosts(@PathVariable Integer userId) {
        User user = userService.findOne(userId);
        return postService.findUserPosts(user);
    }

    @GetMapping("/{userId}/groups")
    public Set<Group> getUserGroups(@PathVariable Integer userId) {
        return groupService.findUserGroups(userId);
    }

    @PostMapping("/reactions/{postId}")
    public ResponseEntity<ReactionDTO> reactToPost(@PathVariable Integer postId, @RequestBody @Validated ReactionDTO reactionDTO) {
        Reaction reaction = reactionService.react(reactionDTO, postId);

        if(reaction == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(reactionDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/groups/{groupId}/requests")
    public ResponseEntity<GroupRequestDTO> createGroupRequest(@PathVariable Integer userId,
                                                              @PathVariable Integer groupId) {
        GroupRequest groupRequest = groupRequestService.create(userId, groupId);

        if(groupRequest == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        GroupRequestDTO createdGroupRequest = new GroupRequestDTO(groupRequest);

        return new ResponseEntity<>(createdGroupRequest, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/groups/{groupId}/requests/{requestId}/approve")
    public void approveGroupRequest(@PathVariable Integer userId,
                                   @PathVariable Integer groupId,
                                   @PathVariable Integer requestId) {
        groupRequestService.approveRequest(requestId);

        Group group = groupService.findOne(groupId);
        User user = userService.findOne(userId);
/*        group.setMembers(groupService.getAllGroupMembers(groupId));
        group.getMembers().add(user);*/
        groupService.save(group);
    }

    @PostMapping("/{userId}/groups/{groupId}/request/{requestId}/decline")
    public void declineGroupRequest(@PathVariable Integer userId,
                                    @PathVariable Integer groupId,
                                    @PathVariable Integer requestId) {
        groupRequestService.declineRequest(requestId);
    }
}
