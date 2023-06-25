package uns.ftn.projekat.svt2023.service;

import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;

public interface ReactionService {
    Reaction react(ReactionDTO reactionDTO, Integer postId);
}
