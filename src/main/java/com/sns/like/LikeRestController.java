package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;

@RestController
public class LikeRestController {

	@Autowired
	private LikeBO likeBO;
	
	// GET:   /like?postId=13       @RequestParam("postId") - 옛날방식
	// GET:   /like/13              @PathVariable(Path에 들어있다라는 뜻) - 요즘 방식
	@RequestMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable int postId,
			HttpSession session) { //로그인 여부 확인 
		
		// 로그인 여부 확인
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 500);
			result.put("errorMessage", "로그인을 해주세요.");
			return result;
		}
		
		// BO 호출 -> like 여부 체크 
		likeBO.likeToggle(postId, userId);
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}

