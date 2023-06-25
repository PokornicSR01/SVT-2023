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
    @Autowired
    private GroupService groupService;

    @Override
    public Post create(PostDTO postDTO, Integer groupId) {

        User user = userService.findByUsername(postDTO.getUserUsername());

        Post newPost = new Post();
        newPost.setUser(user);
        newPost.setCreationDate(LocalDateTime.now());
        newPost.setId(postDTO.getId());
        newPost.setContent(postDTO.getContent());

        if(groupId != null) {
            Group group = groupService.findOne(groupId);
            newPost.setGroup(group);
        }

        newPost = postRepository.save(newPost);

        return newPost;
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> delete(Integer id) {
        Optional<Post> deletedPost = postRepository.findById(id);

        if(deletedPost == null) {
            return null;
        }
        postRepository.deleteById(id);

        return deletedPost;
    }

    @Override
    public Post findOne(Integer id) {
        return postRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<Post> findUserPosts(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public List<Post> findGroupPosts(Group group) {
        return postRepository.findByGroup(group);
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

}
