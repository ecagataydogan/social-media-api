package com.eso.socialmediaserver.follower.entity;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follower", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"leader_id", "follower_id"})
})
@NoArgsConstructor
@Getter
@Setter
public class Follower extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Client leader;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Client follower;
}
