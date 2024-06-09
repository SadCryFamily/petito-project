package com.petito.project.dto.view;

import com.petito.project.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCommentDto
{
    private String author;
    private String message;
    private Date creationDateTime;

    public ViewCommentDto(Comment comment)
    {
        this.author = comment.getAuthor();
        this.message = comment.getMessage();
        this.creationDateTime = comment.getCreationDateTime();
    }
}
