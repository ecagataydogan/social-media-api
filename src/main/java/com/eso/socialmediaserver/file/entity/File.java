package com.eso.socialmediaserver.file.entity;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "file")
@Getter
@Setter
public class File extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "url")
    private String url;

    @ManyToOne
    private Client client;
}
