package com.sns.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sns.post.mapper.PostMapper;

@Controller
public class TestController {
	
	@Autowired 
	PostMapper postMapper;
	
	//1.Stirng + response body => html
	@GetMapping("/test1")
	@ResponseBody
	public String test1() {
		return "Hello world";
	}
	
	// map + responseBody => json
	@GetMapping("/test2")
	@ResponseBody
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		return map;
	}
	
	//JSP -> html
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
	// 4.DB연동 - responsebody -> json
	//SnsApplication DB 설정 안보는 설정 제거
	//DatabaseConfig 클래스 추가
	// application.yml DB접속 정보 추가
	
	// resources/mappers xml
	// logback-spring.xml 추가 (쿼리로그)
	@GetMapping("/test4")
	@ResponseBody
	public List<Map<String, Object>> test4() {
		return postMapper.selectPostList();
	}
}
