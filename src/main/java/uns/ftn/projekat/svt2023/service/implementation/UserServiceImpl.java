package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.model.enums.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {

        Optional<User> user = userRepository.findFirstByUsername(username);

        if(!user.isEmpty()){
            return user.get();
        }
        return null;
    }

    @Override
    public Set<User> searchUsersByName(String name) {
        return userRepository.searchUsersByName(name);
    }

    @Override
    public User returnLoggedUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return this.findByUsername(a.getName());
    }

    @Override
    public Set<User> getAllFriends() {
        User user = this.returnLoggedUser();

        Set<User> firstSet = userRepository.getAllFriends(user.getId());
        Set<User> secondSet = userRepository.getAllFriends1(user.getId());

        Set<User> mergedSet = new HashSet<>();
        mergedSet.addAll(firstSet);
        mergedSet.addAll(secondSet);
        mergedSet.remove(user);

        return mergedSet;
    }

    @Override
    public User create(UserDTO userDTO) {

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setRole(Roles.USER);
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    @Override
    public User findOne(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new NullPointerException("User not found with id: " + id));
    }
    @Override
    public void delete(Integer id) {userRepository.deleteById(id);}

}
