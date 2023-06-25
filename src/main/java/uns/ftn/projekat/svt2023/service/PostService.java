package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface PostService {
    Post create(PostDTO postDTO, Integer groupId);
    Post save(Post post);
    Optional<Post> delete(Integer id);
    Post findOne(Integer id);
    List<Post> findUserPosts(User user);
    List<Post> findGroupPosts(Group group);
    List<Post> findAll();
}
