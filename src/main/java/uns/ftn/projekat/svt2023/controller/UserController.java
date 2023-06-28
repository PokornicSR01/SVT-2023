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
    ReactionService reactionService;
    UserService userService;
    FriendRequestService friendRequestService;
    UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserServiceImpl userService, UserDetailsService userDetailsService,
                          ReactionService reactionService, FriendRequestService friendRequestService){
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.reactionService = reactionService;
        this.friendRequestService = friendRequestService;
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
        return userService.createAuthenticationToken(authenticationRequest);
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
        return userService.changePassword(passwords);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> loadAll() {
        return this.userService.getAll();
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }

    @GetMapping("/{userId}/posts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Post> getUserPosts(@PathVariable Integer userId) {
        return userService.getUserPosts(userId);
    }

    @GetMapping("/{userId}/groups")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<Group> getUserGroups(@PathVariable Integer userId) {
        return userService.getUserGroups(userId);
    }

    @PostMapping("/reactions/{postId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReactionDTO> reactToPost(@PathVariable Integer postId, @RequestBody @Validated ReactionDTO reactionDTO) {
        Reaction reaction = reactionService.react(reactionDTO, postId);

        if(reaction == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(reactionDTO, HttpStatus.CREATED);
    }

    @GetMapping("/friends/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<User> getAllFriends() {
        return userService.getAllFriends();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<User> approveGroupRequest(@RequestParam String name) {
        return userService.searchUsersByName(name);
    }
}
