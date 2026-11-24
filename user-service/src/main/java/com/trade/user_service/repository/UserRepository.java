package com.trade.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	boolean existByEmail(String emailId);

}
