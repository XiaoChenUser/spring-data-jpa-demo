package com.example;

import com.example.quickstart.QuickStartApplication;
import com.example.quickstart.dto.UserDTO;
import com.example.quickstart.enumeration.GenderEnum;
import com.example.quickstart.repository.UserAnnotationRepository;
import com.example.quickstart.repository.UserMethodNamingRepository;
import com.example.quickstart.repository.UserPageRepository;
import com.example.quickstart.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest(classes = QuickStartApplication.class)
public class QuickStartApplicationTest {
    @Test
    public void contextTest(){}


    @Autowired
    private UserRepository repository;
    @Autowired
    private UserPageRepository pageRepository;
    @Autowired
    private UserMethodNamingRepository methodNamingRepository;
    @Autowired
    private UserAnnotationRepository annotationRepository;

    @Test
    void testGetAll(){
        Iterable<UserDTO> iterable = repository.findAll();
        iterable.forEach(System.out::println);
    }

    @Test
    void testFindById() {
        repository.findById(1).ifPresent(System.out::println);
    }

    @Test
    void testCount(){
        long count = repository.count();
        Assertions.assertEquals(2,count);
    }

    @Test
    void testSave(){
        UserDTO user = new UserDTO().setUsername("Jim").setAge(28).setGender(GenderEnum.MALE);
        repository.save(user);
        System.out.println("id:" + user.getId());
    }

    @Test
    void testSaveAll(){
        List<UserDTO> userList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            userList.add(new UserDTO().setUsername(UUID.randomUUID().toString()).setAge(20).setGender(GenderEnum.UNKNOWN));
        }
        repository.saveAll(userList);
    }

    @Test
    void testDeleteById(){
        repository.deleteById(1);
    }

    @Test
    void testDeleteEntity(){        // 还是根据 id 删除
        UserDTO userDTO = new UserDTO().setId(3).setUsername("JJ").setAge(22).setGender(GenderEnum.FEMALE);
        repository.delete(userDTO);
    }

    // ----------------- paging and sorting -------------------
    @Test
    void testSort(){
        Sort sort = Sort.by(Sort.Direction.ASC, "username");    // 这里是 DTO 类的属性名，不是数据表的 column
        pageRepository.findAll(sort).forEach(System.out::println);
    }

    @Test
    void testPage(){
        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Page<UserDTO> page = pageRepository.findAll(pageRequest);
        System.out.println("total count:" + page.getTotalElements() + ",total pages:" + page.getTotalPages());
        page.forEach(System.out::println);
    }

    // ---------------- 基于方法名查询 --------------------
    // https://docs.spring.io/spring-data/jpa/docs/2.2.0.RELEASE/reference/html/#jpa.query-methods.query-creation

    @Test
    void add() {
        LocalDateTime now = LocalDateTime.now();

        List<UserDTO> list = new ArrayList<>();
        UserDTO user1 = new UserDTO("test1", 20, GenderEnum.UNKNOWN, now.plusDays(-5), 0);
        UserDTO user2 = new UserDTO("test2", 10, GenderEnum.FEMALE, now.plusHours(2), 0);
        UserDTO user3 = new UserDTO("test3", 30, GenderEnum.MALE, now.plusMonths(2), 1);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        repository.saveAll(list);
    }

    @Test
    void testFindByCreateTimeAfter(){
        Iterable<UserDTO> byCreateTimeAfter = methodNamingRepository.findByCreateTimeAfter(LocalDateTime.now());
        byCreateTimeAfter.forEach(System.out::println);
    }

    @Test
    void testFindAllByDeletedIs(){
        System.out.println("-------");
        methodNamingRepository.findAllByDeletedIs(0).forEach(System.out::println);
    }


    //--------------- Annotation Query -----------------------
    @Test
    void testFindByAge(){
        annotationRepository.findByAge(20).forEach(System.out::println);
    }

    @Test
    void testFindByDeletedIsNot(){
        annotationRepository.findByDeletedIsNot(1).forEach(System.out::println);
    }

    @Test
    void testGetByUsername(){
        UserDTO test1 = annotationRepository.getByUsername("test1");
        System.out.println(test1);
    }

    @Test
    void testFindById2(){
        UserDTO user = annotationRepository.findById(2);
        System.out.println(user);
    }

    /**
     * update/delete 方法的调用必须放在事务中；
     * 单元测试中，事务默认回滚，所以会出现测试用例通过，但数据不变的情况。
     */
    @Test
    @Transactional
    void testUpdateAgeByName(){
        annotationRepository.updateAgeByName("test2", 22);
    }

    @Test
    @Transactional
    void testSaveEntity(){
        UserDTO userDTO = new UserDTO().setUsername("test4").setAge(27).setGender(GenderEnum.MALE).setDeleted(0);
        annotationRepository.save(userDTO);
    }

    @Test
    void testUpdateByEntity() {
        UserDTO userDTO = new UserDTO().setId(1).setUsername("test4").setAge(27).setGender(GenderEnum.MALE);
        annotationRepository.updateByEntity(userDTO);
    }

    @Test
    void testGetPage(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<UserDTO> page = annotationRepository.getPage(0, pageRequest);
        page.forEach(System.out::println);
    }
}
