package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity getUserEntityById(int userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	//중복확인
	//input : loginId output : Entity
	public UserEntity getIsDuplicatedByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	//회원가입 insert
	public Integer addUser(String loginId, String password, String name, String email) {
		//UserEntity = save(Entity);
		UserEntity userEntity = userRepository.save(
				UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
				
		return userEntity == null ? null : userEntity.getId();		
	}
	
	//로그인 select

	// input:loginId, password     output:UserEntity(null이거나 entity)
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}

}
