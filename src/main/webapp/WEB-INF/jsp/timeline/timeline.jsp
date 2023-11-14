<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center">
	<div class="contents-box">
		<%-- 글쓰기 영역(로그인 된 사람만 보이게) --%>
		<c:if test="${not empty userId}">
		<div class="write-box border rounded m-3">
			<textarea id="writeTextArea" placeholder="내용을 입력해주세요" class="w-100 border-0"></textarea>
			
			<%-- 이미지 업로드를 위한 아이콘과 업로드 버튼을 한 행에 멀리 떨어뜨리기 위한 div --%>
			<div class="d-flex justify-content-between">
				<div class="file-upload d-flex">
					
					<%-- file 태그를 숨겨두고 이미지를 클릭하면 file 태그를 클릭한 효과 --%>
					<input type="file" id="file" accept=".jpg, .png, .gif, .jpeg" class="d-none">

					<a href="#" id="fileUploadBtn"><img width="35" src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png"></a>

					<%-- 업로드 된 임시 파일명 노출 --%>
					<div id="fileName" class="ml-2"></div>
				</div>
				<button id="writeBtn" class="btn btn-info">게시</button>
			</div>
		</div> <%--// 글쓰기 영역 끝 --%>
		</c:if>
		
		<%-- 타임라인 영역 --%>
		<div class="timeline-box my-5">
			<c:forEach items="${cardList}" var="card">
			<%-- 카드1 --%>
			<div class="card border rounded mt-3">
				<%-- 글쓴이, 더보기(삭제) --%>
				<div class="p-2 d-flex justify-content-between">
					<span class="font-weight-bold">${card.user.loginId}</span>
					
					<a href="#" class="more-btn">
						<img src="https://www.iconninja.com/files/860/824/939/more-icon.png" width="30">
					</a>
				</div>	
				
				<%-- 카드 이미지 --%>
				<div class="card-img">
					<img src="${card.post.imagePath}" class="w-100" alt="본문 이미지">
				</div>
				
				<%-- 좋아요 --%>
				<div class="card-like m-3">
					<a href="#" class="like-btn">
						<img src="https://www.iconninja.com/files/214/518/441/heart-icon.png" width="18" height="18" alt="filled heart">
					</a>
					좋아요 11개
				</div>
				
				<%-- 글 --%>
				<div class="card-post m-3">
					<span class="font-weight-bold">${card.user.loginId}</span>
					<span>${card.post.content}</span>
				</div>
				
				<%-- 댓글 제목 --%>
				<div class="card-comment-desc border-bottom">
					<div class="ml-3 mb-1 font-weight-bold">댓글</div>
				</div>
				
				<%-- 댓글 목록 --%>
				<div class="card-comment-list m-2">
				<c:forEach items="${card.commentList}" var="commentView">
					<%-- 댓글 내용들 --%>
					<div class="card-comment m-1">
						<span class="font-weight-bold">${commentView.user.loginId}</span>
						<span>${commentView.user.content}</span> <%-- 댓글내용 --%>
						
						<%-- 댓글 삭제 버튼 --%>
						<a href="#" class="comment-del-btn">
							<img src="https://www.iconninja.com/files/603/22/506/x-icon.png" width="10" height="10">
						</a>
					</div>
				</c:forEach>
					<%-- 댓글 쓰기 --%>
					<div class="comment-write d-flex border-top mt-2">
						<input type="text" class="form-control border-0 mr-2 comment-input" placeholder="댓글 달기"/> 
						<button type="button" class="comment-btn btn btn-light" data-post-id="${card.post.id}">게시</button>
					</div>
				</div> <%--// 댓글 목록 끝 --%>
			</div> <%--// 카드1 끝 --%>
			</c:forEach>
		</div> <%--// 타임라인 영역 끝  --%>
	</div> <%--// contents-box 끝  --%>
</div>


<script>
$(document).ready(function() {
	//파일 이미지 클릭 => 숨겨져있던 type="file"을 동작시킨다.
	$('#fileUploadBtn').on('click', function(e) {
		//a 는 누르면 위로 올라갈 수 있으므로설정 없애기
		e.preventDefault();
		$('#file').click();
		
		//이미지 선택하는 순간 -> 유효성 확인 및 업로드 된 파일명 노출
		$('#file').on('change', function(e) {
			//alert("선택");
			let fileName = e.target.files[0].name; <%-- e.target = $('#file) // files[0].name = 이름만 나오기 --%>
			console.log(fileName); <%-- 이미지name : 0013_###.jpg --%>
			
			//확장자 유효성확인
			let ext = fileName.split(".").pop().toLowerCase(); <%-- pop : 배열중에 마지막 칸만 뽑아내는 함수 // toLowerCase : 소문자로 바꾸깅 --%>
			//alert(ext);
			 if(ext != 'jpg' && ext != 'gif'  && ext != 'png' && ext != 'jpeg') {
				 alert('이미지 파일만 업로드 할 수 있습니다.');
				 $('#file').val(""); //파일 태그에 파일 제거 (보이지 않지만 업로드될 수 있으므로 주의) 
				 $('#fileName').text(""); //파일명 지우기
				 return ;
			 }
			
			 //유효성 통과한 이미지는 업로드 된 파일명 노출
			 $('#fileName').text(fileName);
			
			});
		});
		$('#writeBtn').on('click', function() {
			//글,그림 올리기
			let content = $('#writeTextArea').val();
			console.log(content);
			if (content.length < 1) {
				alert('글을 입력하세요');
				return;
			}
			
			let file = $('#file').val();
			if(!file) {
				alert('파일을 등록해주세요.');
				return;
			}
			
			//파일확장자체크
			let ext = file.split(".").pop().toLowerCase();
			//alert(ext);
			if(ext != 'jpg' && ext != 'jpeg' && ext != 'png' && ext != 'gif') {
				alert('이미지파일만 올려주세요.');
				$('#file').val("");
				$('#fileName').text("");
				return;
			}
			
			//form태그
			let formData = new FormData();
			formData.append("content", content);
			formData.append("file", $('#file')[0].files[0]); //$('#file')[0]은 첫번째 input file 태그를 의미, files[0]는 업로드된 첫번째 파일
			
			$.ajax({
				type:"post"
				, url:"/post/creat"
				, data:formData
				, enctype: "multipart/form-data"    // 파일 업로드를 위한 필수 설정
				, processData: false    // 파일 업로드를 위한 필수 설정
				, contentType: false    // 파일 업로드를 위한 필수 설정
				,success:function(data) {
					if (data.code == 200) {
						location.reload();
					} else if (data.code == 500) { // 비로그인 일 때
						location.href = "/user/sign-in-view";
					} else {
						alert(data.errorMessage);
					}
				}
				, error: function(e) {
					alert("글 저장에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
			
		});
	
		//댓글 쓰기
		$('.comment-btn').on('click', function() {
			//alert('dd');
			
			//$(this) = 내가 ₩댓글게시버튼₩을 눌렀을 postId를 가져오기 위해 $(this) 를 쓰면 그 포스트의 넘버가 불려와짐!!!! 
			let postId = $(this).data('post-id'); //data-post-id = 13
			//alert(postId);
			
			//댓글 내용 가져오기 (진짜아아 많다 함)
			
			//방법 1) button의 형제 input을 가져오기 - 형제가 여러개일 수 있으나 지금은 1개임(class에 이름을 붙여도 되공) 
			//let content = $(this).siblings("input").val().trim();
			
			//방법2) 버튼에 대한 하나 전 행동? - 프리브 함수
			let content = $(this).prev().val().trim();
			//alert(content);
			
			//방법3) 부모의 첫번째 input가져오기 이런것도 있음
			
		 $.ajax({
			 type:"post"
			, url:"/comment/create"
			, data:{"postId":postId, "content":content}
				
			, success:function(data) {
				if (data.code == 200) {
					location.reload(true);
				} else if (data.code == 500) {
					alert(data.errorMessage);
					location.href = "/user/sign-in-view";
				}
			}
			, error:function(request, status, error) {
				alert("댓글 쓰기 실패했습니다.");
			}
		 });
		});

	});	
</script>

















