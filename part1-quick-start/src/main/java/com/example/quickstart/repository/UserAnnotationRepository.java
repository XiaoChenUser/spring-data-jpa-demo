package com.example.quickstart.repository;

import com.example.quickstart.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnnotationRepository extends CrudRepository<UserDTO, Integer> {

    @Query("select u from UserDTO u where u.age = ?1")
    List<UserDTO> findByAge(int age);

    @Query("select u from UserDTO u where u.deleted <> :deleted")
    List<UserDTO> findByDeletedIsNot(@Param("deleted") int deleted);

    // ERROR !!!
    //使用命名参数时，必须给参数添加 @Param 注解，指定名称
    @Query("select u from UserDTO u where u.username = :username")
    UserDTO getByUsername(String username);

    @Query(value = "select * from user where id = ?1", nativeQuery = true)
    UserDTO findById(int id);

    @Modifying
    @Query("update UserDTO u set u.age = :age where u.username = :username")
    void updateAgeByName(@Param("username") String username, @Param("age") Integer age);


    // ERROR !!!
    // JPA 无法使用 entity 类型参数？
    @Query("update UserDTO u set u.username=:username,u.age=:age,u.gender=:gender where u.id=:id")
    Integer updateByEntity(UserDTO userDTO);

    @Query(value = "select * from user where deleted=?1",
            countQuery = "select count(*) from user where deleted=?1", nativeQuery = true)
    Page<UserDTO> getPage(Integer deleted, Pageable pageable);
}
