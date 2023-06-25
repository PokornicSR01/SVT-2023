package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.model.enums.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.time.*;
@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;

    @Override
    public Reaction react(ReactionDTO reactionDTO, Integer postId) {
        User user = userService.findByUsername(reactionDTO.getUserUsername());
        Post post = postService.findOne(postId);

        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setId(reactionDTO.getId());
        reaction.setType(ReactionType.valueOf(reactionDTO.getReactionType()));
        reaction.setMadeBy(user);
        reaction.setTimeStamp(LocalDateTime.now());

        reaction = reactionRepository.save(reaction);

        return reaction;
    }
}
