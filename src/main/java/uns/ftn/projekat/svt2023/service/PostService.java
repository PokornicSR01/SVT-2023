package uns.ftn.projekat.svt2023.service;

import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface PostService {

    Post findByUser(String username);
    Optional<Post> deletePost(Integer id);
    Post createPost(PostDTO postDTO);
    List<Post> findAll();
    Post findOne(Integer id);
    Post save(Post post);
}
