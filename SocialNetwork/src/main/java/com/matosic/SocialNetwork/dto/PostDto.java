package com.matosic.SocialNetwork.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.matosic.SocialNetwork.model.Comment;
import com.matosic.SocialNetwork.model.Post;
import com.matosic.SocialNetwork.model.ReactionType;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private String groupName;
    private long likeCount;
    private long dislikeCount;
    private long heartCount;
    private List<CommentDto> comments; // Dodali smo listu komentara

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        if (post.getUserGroup() != null){
        	this.groupName = post.getUserGroup().getName();
        }
        this.username = post.getUser().getUsername();
        this.content = post.getContent(); // Pretpostavljam da je content u pdfUrl-u
        this.creationDate = post.getCreationDate();
        this.likeCount = countReactions(post, ReactionType.LIKE);
        this.dislikeCount = countReactions(post, ReactionType.DISLIKE);
        this.heartCount = countReactions(post, ReactionType.HEART);
        this.comments = mapComments(post.getComments()); // Mapiramo komentare
    }

    private long countReactions(Post post, ReactionType type) {
        return post.getReactions().stream()
            .filter(r -> r.getType() == type)
            .count();
    }

    private List<CommentDto> mapComments(Set<Comment> comments) {
        return comments.stream()
            .map(CommentDto::new) // Pretpostavljamo da imate CommentDto koji mapira Comment
            .collect(Collectors.toList());
    }
}
