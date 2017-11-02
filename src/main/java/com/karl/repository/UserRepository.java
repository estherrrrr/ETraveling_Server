package com.karl.repository;

import com.karl.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by R on 2017/8/23.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update UserEntity us set us.name=:qName, us.sex=:qSex, us.age=:qAge, us.orgin=:qOrigin, us.headimg=:qHeadimg where us.openid=:qId")
    public void updateUser(@Param("qName") String name, @Param("qSex") String sex,
                           @Param("qAge") int age, @Param("qOrigin") String origin, @Param("qHeadimg") String headimg, @Param("qId") String openid);
}
