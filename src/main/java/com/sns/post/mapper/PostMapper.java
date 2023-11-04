package com.sns.post.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper {
	public List<Map<String, Object>> selectPostList();
}
