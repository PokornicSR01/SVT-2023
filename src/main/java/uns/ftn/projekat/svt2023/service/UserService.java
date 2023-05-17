package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface UserService {
    User create(UserDTO userDTO);
    void delete(Integer id);
    List<User> findAll();
    User findOne(Integer id);
    User findByUsername(String username);
}
