package com.sns.comment.domain;

import com.sns.user.entity.UserEntity;

import lombok.Data;

@Data
public class CommentView {
	// 댓글 내용, 댓글쓴이
	private Comment comment;

	private UserEntity user;
	
}