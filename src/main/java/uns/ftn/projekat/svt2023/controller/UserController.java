package uns.ftn.projekat.svt2023.controller;

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
import uns.ftn.projekat.svt2023.security.*;
import uns.ftn.projekat.svt2023.service.*;
import uns.ftn.projekat.svt2023.service.implementation.*;

import javax.servlet.http.*;
import java.security.*;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("api/users")
public class UserController
{

    UserService userService;
    UserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;
    TokenUtils tokenUtils;
    PasswordEncoder encoder;

    @Autowired
    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, TokenUtils tokenUtils, PasswordEncoder encoder){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
        this.encoder = encoder;
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

}
