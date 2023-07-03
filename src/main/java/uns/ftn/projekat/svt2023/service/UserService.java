package uns.ftn.projekat.svt2023.service;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface UserService {
    User create(UserDTO userDTO);
    User changeProfile(UserProfileDTO userProfileDTO);
    void save(User user);
    void delete(Integer id);
    List<User> getAll();
    User findOne(Integer id);
    User findByUsername(String username);
    Set<User> searchUsersByName(String name);
    User returnLoggedUser();
    Set<User> getAllFriends(Integer userId);
    ResponseEntity<UserTokenState> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest);
    ResponseEntity<UserDTO> changePassword(PasswordDTO passwordDTO);
    List<Post> getUserPosts(Integer userId);
    Set<Group> getUserGroups(Integer userId);
    Set<FriendRequest> getAllUserFriendRequests(Integer userId);
}
