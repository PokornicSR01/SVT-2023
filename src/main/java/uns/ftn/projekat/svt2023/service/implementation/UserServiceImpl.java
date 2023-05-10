package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if(!user.isEmpty()){
            return user.get();
        }
        return null;
    }

    @Override
    public User createUser(UserDTO userDTO) {

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    
}
