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





