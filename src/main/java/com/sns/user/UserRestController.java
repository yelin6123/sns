package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 로그인 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	@GetMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		//DB조회 (왜 Entity로 했을까?)
		UserEntity user = userBO.getIsDuplicatedByLoginId(loginId);
		
		//응답 
		//(isDuplicated가 false면 왜 중복아니고 true면 중복일까?)
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user == null) {
			result.put("isDuplicated", false);
		} else {
			result.put("isDuplicated", true);
		}
		return result;
	}
	
	
	//회원가입
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginID,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		//password 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		//DB insert
		Integer id = userBO.addUser(loginID, hashedPassword, name, email);
		
		//응답
		Map<String, Object> result = new HashMap<>();
		if(id == null) {
			result.put("code", 500);
			result.put("errorMessage", "회원가입하는데 실패");
		} else {
			result.put("code", 200);
			result.put("result", "성공");
		}
		return result;
	}
	
	
	@PostMapping("sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		//해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		//select
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		//응답
		Map<String, Object> result = new HashMap<>();
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			session.setAttribute("userLoginId", user.getLoginId());

			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "존재하지 않는 사용자 입니다.");
			
		}
		return result;
	}
	
	
	
}
