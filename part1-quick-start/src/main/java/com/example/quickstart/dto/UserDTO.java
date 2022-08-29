package com.example.quickstart.dto;

import com.example.quickstart.enumeration.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,     // strategy 设置使用数据库主键自增策略
                    generator = "JDBC")         // generator 设置插入完成后，查询最后生成的 ID 填充到该属性中
    private Integer id;

    @Column(name = "name", nullable = false)
    private String username;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.ORDINAL)       // 将 enum 类型实例的序号存入数据库，序号 ordinal 从 0 开始
    private GenderEnum gender;

    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "update_time")
    private LocalDateTime updateTime = LocalDateTime.now();

    private Integer deleted = 0;

    public UserDTO(String username, Integer age, GenderEnum gender, LocalDateTime createTime, Integer deleted) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.createTime = createTime;
        this.deleted = deleted;
    }
}
