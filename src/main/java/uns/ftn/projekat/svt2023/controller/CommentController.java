package uns.ftn.projekat.svt2023.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import uns.ftn.projekat.svt2023.model.dto.*;
import uns.ftn.projekat.svt2023.model.entity.*;
import uns.ftn.projekat.svt2023.service.*;

import java.util.*;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentDTO> create(@RequestBody @Validated CommentDTO newComment, @PathVariable Integer postId){
        Comment createdComment = commentService.create(newComment, postId, null);

        if(createdComment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentDTO commentDTO = new CommentDTO(createdComment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void delete(@PathVariable Integer id) {
        Comment deletedComment = commentService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentDTO> edit(@PathVariable Integer id, CommentDTO editedComment) {
        Comment comment = commentService.edit(editedComment, id);

        if(editedComment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentDTO commentDTO = new CommentDTO(comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{commentId}/reply")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentDTO> reply(@RequestBody @Validated CommentDTO newComment, @PathVariable Integer commentId){
        Comment createdComment = commentService.reply(newComment, commentId);

        if(createdComment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentDTO commentDTO = new CommentDTO(createdComment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}/replies")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<Comment> commentReplies(@PathVariable Integer commentId) {
        return commentService.getAllCommentReplies(commentId);
    }

}
