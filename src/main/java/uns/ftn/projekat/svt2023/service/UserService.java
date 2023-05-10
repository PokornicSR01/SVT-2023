package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface UserService {

    User findByUsername(String username);
    User createUser(UserDTO userDTO);
    List<User> findAll();

}
