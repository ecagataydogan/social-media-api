package com.eso.socialmediaserver.client.entity;

import com.eso.socialmediaserver.common.entity.BaseEntity;
import com.eso.socialmediaserver.file.entity.File;
import com.eso.socialmediaserver.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client")
@NoArgsConstructor
@Getter
@Setter
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "bio")
    private String bio;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private File avatar;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
