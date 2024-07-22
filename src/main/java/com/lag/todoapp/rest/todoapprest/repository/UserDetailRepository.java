package com.lag.todoapp.rest.todoapprest.repository;

import com.lag.todoapp.rest.todoapprest.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

}
