package com.karl.repository;

import com.karl.model.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by R on 2017/8/23.
 */
@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Integer> {
}
