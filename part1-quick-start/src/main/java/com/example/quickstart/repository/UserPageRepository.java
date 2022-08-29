package com.example.quickstart.repository;

import com.example.quickstart.dto.UserDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPageRepository extends PagingAndSortingRepository<UserDTO,Integer> {
}
