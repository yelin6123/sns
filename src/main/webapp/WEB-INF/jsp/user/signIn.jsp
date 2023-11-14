<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <div class="d-flex justify-content-center">
	<div class="shadow-box m-5">
	<h1 class="ml-5">로그인</h1>
		<div class="d-flex justify-content-center m-5">
			<%-- 키보드 Enter키로 로그인이 될 수 있도록 form 태그를 만들어준다.(submit 타입의 버튼이 동작됨) --%>
			
			<form id="loginForm" action="/user/sign-in" method="post">
				<%-- input-group-prepend: input box 앞에 ID 부분을 회색으로 붙인다. --%>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">ID</span>
					</div>
					<input type="text" class="form-control" name="loginId">
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">PW</span>
					</div>
					<input type="password" class="form-control" id="password" name="password">
				</div>
				
				<%-- btn-block: 로그인 박스 영역에 버튼을 가득 채운다. --%>
				<input type="submit" class="btn btn-block btn-info" value="로그인">
				<a class="btn btn-block btn-dark" href="/user/sign-up-view">회원가입</a>
			</form>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 로그인 서브밋
		$('#loginForm').on('submit', function(e) {
			e.preventDefault();
			//alert('dddd');
			
			let loginId = $('input[name=loginId]').val().trim();
			let password = $('#password').val().trim();
			
			//validation
			if(!loginId) {
				alert('아이디를 입력해주세요');
				return false;
			}
			if(!password) {
				alert('비밀번호를 입력하세요');
				return false;
			}
			
			let url = $(this).attr('action');
			//alert(url);
			console.log(url);
			let params = $(this).serialize();
			console.log(params);
			
			$.post(url, params)
			.done(function(data) {
				if(data.code == 200) {
					location.href = "/timeline/timeline-view"
				} else {
					alert(data.errorMessage);
				}
				
			})
			
			
		});
	});

</script>


