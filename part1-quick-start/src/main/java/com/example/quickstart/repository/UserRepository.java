package com.example.quickstart.repository;

import com.example.quickstart.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDTO,Integer> {
}
