package com.eso.socialmediaserver.post.entity;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.common.entity.BaseEntity;
import com.eso.socialmediaserver.file.entity.File;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @OneToOne
    @JoinColumn(name = "image_id")
    private File image;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;
}
