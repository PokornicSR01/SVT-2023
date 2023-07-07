package uns.ftn.projekat.svt2023.service.implementation;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.repository.*;
import uns.ftn.projekat.svt2023.service.*;

import java.time.*;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Comment create(CommentDTO commentDTO, Integer postId, Integer commentId) {
        User user = userService.returnLoggedUser();
        Post post = postService.findOne(postId);

        Comment comment = new Comment();
        comment.setText(commentDTO.getCommentText());
        comment.setId(commentDTO.getId());
        comment.setTimeStamp(LocalDateTime.now());
        comment.setBelongsToUser(user);
        comment.setBelongsToPost(post);
        comment.setRepliesToComment(null);

        if(commentId != null) {
            Comment repliesTo = this.findOne(commentId);
            comment.setRepliesToComment(repliesTo);
        }

        comment = commentRepository.save(comment);

        return comment;
    }

    @Override
    public Comment edit(CommentDTO commentDTO, Integer commentId) {
        Comment comment = this.findOne(commentId);

        comment.setText(commentDTO.getCommentText());
        comment = this.save(comment);

        return comment;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment delete(Integer id) {
        Comment comment = this.findOne(id);
        commentRepository.deleteById(id);
        return comment;
    }

    @Override
    public Set<Comment> getAllPostComments(Integer postId) {
        return commentRepository.getAllPostComments(postId);
    }

    @Override
    public Set<Comment> getAllUserComments(Integer userId) {
        return commentRepository.getAllUserComments(userId);
    }

    @Override
    public Comment reply(CommentDTO commentDTO, Integer commentId) {
        Comment repliesTo = this.findOne(commentId);
        Integer postId = repliesTo.getBelongsToPost().getId();
        return this.create(commentDTO, postId, commentId);
    }

    @Override
    public Comment findOne(Integer commentId) {
        return commentRepository.findById(commentId).orElseGet(null);
    }

    @Override
    public Set<Comment> getAllCommentReplies(Integer commentId) {
        return commentRepository.getAllCommentReplies(commentId);
    }
}
