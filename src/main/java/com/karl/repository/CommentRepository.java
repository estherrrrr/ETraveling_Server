package com.karl.repository;

import com.karl.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by R on 2017/8/23.
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
