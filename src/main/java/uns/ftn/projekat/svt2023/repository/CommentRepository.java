package uns.ftn.projekat.svt2023.repository;

import org.springframework.data.jpa.repository.*;
import uns.ftn.projekat.svt2023.model.entity.*;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "SELECT c FROM Comment c WHERE c.belongsToPost.id = :postId")
    Set<Comment> getAllPostComments(Integer postId);
    @Query(value = "SELECT c FROM Comment c WHERE c.belongsToUser.id = :userId")
    Set<Comment> getAllUserComments(Integer userId);
    @Query(value = "SELECT c FROM Comment c WHERE c.repliesToComment.id = :commentId")
    Set<Comment> getAllCommentReplies(Integer commentId);
}
