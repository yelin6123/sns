package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.comment.bo.CommentBO;

@RequestMapping("/comment")
@RestController
public class CommentRestController {
	
	@Autowired
	private CommentBO commentBO;

	/**
	 * 댓글 쓰기 API
	 * @param postId
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("postId") int postId,
			@RequestParam("content") String content,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 500);
			result.put("errorMessage", "로그인을 해주세요.");
			return result;
		}
		
		commentBO.addComment(postId, userId, content);
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("commentId") int commentId, 
			HttpSession session) { //로그인 된 사람이랑 일치하고 싶을 때! 지금 login되어있는 사람과 일치한지 확인
		
		Map<String, Object> result = new HashMap<>();
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId"); //로그인 안됐을 수도 가정해봄
		if (userId == null) {
			result.put("code", 500);
			result.put("errorMessage", "로그인이 되지 않은 사용자 입니다.");
			return result;
		}
		
		//통과하면 삭제하는 로직으로!
		commentBO.deleteCommentById(commentId);
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
	}
}





