package uns.ftn.projekat.svt2023.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.service.*;

@RestController
@RequestMapping("api/users")
public class UserController
{
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> create (@RequestBody UserDTO newUser) {
        User createdUser = userService.createUser(newUser);

        if(createdUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

}
