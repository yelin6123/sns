package com.sns.like.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeMapper {
//	public int selectLikeCountByPostIdUserId(
//		@Param("postId") int postId,
//		@Param("userId") int userId);
//	
//	public int selectLikeCountByPostId(int postId);
	
	//위 둘의 공통점(int, postId)를 기준으로 다시 작성 
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);
	
	public void insertLike(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void deleteLikeByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void deleteLikeByPostId(int postId);
}





