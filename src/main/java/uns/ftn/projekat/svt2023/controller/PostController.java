package uns.ftn.projekat.svt2023.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.util.*;

@RestController
@RequestMapping("api/posts")
public class PostController {

    PostService postService;
    PostRepository postRepository;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO newPost) {

        Post createdPost = postService.create(newPost);

        if(createdPost == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        PostDTO postDTO = new PostDTO(createdPost);

        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void delete(@RequestParam Integer id) {
        Optional<Post> deletedPost = postService.delete(id);
    }

    @PutMapping (value = "{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PostDTO> update(@PathVariable("id") Integer id, @RequestBody PostDTO newPost) {

        Post post = postService.findOne(id);

        if(post == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        post.setContent(newPost.getContent());

        post = postService.save(post);

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Post> loadAll() {
        return this.postService.findAll();
    }
}
