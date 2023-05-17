package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Override
    public Post findByUser(String username) {
        return null;
    }

    @Override
    public Optional<Post> deletePost(Integer id) {
        Optional<Post> deletedPost = postRepository.findById(id);

        if(deletedPost == null) {
            return null;
        }
        postRepository.deleteById(id);

        return deletedPost;
    }

    @Override
    public Post createPost(PostDTO postDTO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(postDTO.getCreationDate(), formatter);

        User user = userService.findByUsername(postDTO.getUserUsername());

        Post newPost = new Post();
        newPost.setUser(user);
        newPost.setCreationDate(dateTime);
        newPost.setId(postDTO.getId());
        newPost.setContent(postDTO.getContent());
        newPost = postRepository.save(newPost);

        return newPost;
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Post findOne(Integer id) {
        return postRepository.findById(id).orElseGet(null);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
}