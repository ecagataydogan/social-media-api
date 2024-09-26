package com.eso.socialmediaserver.post.entity;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.common.entity.BaseEntity;
import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_id", "comment_id", "post_id"})
})
@NoArgsConstructor
@Getter
@Setter
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @PrePersist
    @PreUpdate
    private void validateLike() {
        if ((post != null && comment != null) || (post == null && comment == null)) {
            throw new BusinessException(ErrorCode.validation, "Either post or comment must be present, but not both.");
        }
    }
}
