package com.sns.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sns.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findByLoginId(String loginId);
	
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
}
