package com.example.quickstart.repository;

import com.example.quickstart.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserMethodNamingRepository extends CrudRepository<UserDTO,Integer> {

    Iterable<UserDTO> findByCreateTimeAfter(LocalDateTime dateTime);

    Iterable<UserDTO> findAllByDeletedIs(Integer deleted);
}
