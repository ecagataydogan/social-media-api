package com.eso.socialmediaserver.file.repository;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByIdAndClient(Long id, Client client);
}
