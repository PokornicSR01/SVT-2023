package uns.ftn.projekat.svt2023.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.time.*;
import java.util.*;

@RestController
@RequestMapping("api/posts")
public class PostController {

    PostService postService;
    UserService userService;
    GroupService groupService;

    @Autowired
    public PostController(PostService postService, UserService userService, GroupService groupService) {
        this.postService = postService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO newPost) {

        Post createdPost = postService.create(newPost, null);

        if(createdPost == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public void delete(@RequestParam Integer id) {
        Optional<Post> deletedPost = postService.delete(id);
    }

    @PutMapping("/edit")
    public ResponseEntity<PostDTO> edit(@RequestBody @Validated PostDTO editPost){
        Post edit = postService.findOne(editPost.getId());
        edit.setContent(editPost.getContent());
        postService.save(edit);

        PostDTO postDTO = new PostDTO(edit);
        return  new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable Integer postId) {
        Post post = postService.findOne(postId);
        return post;
    }


    @GetMapping("/all")
    public List<Post> loadAll() {
        return this.postService.findAll();
    }
}
