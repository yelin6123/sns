package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.like.bo.LikeBO;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;

	// input:userId(비로그인/로그인 허용 null도 허용)     output:List<CardView>
	public List<CardView> generateCardViewList(Integer userId) {
		List<CardView> cardViewList = new ArrayList<>(); // []
		
		// 글 목록을 가져온다.  List<PostEntity>
		List<PostEntity> postList = postBO.getPostList();
		
		// 글 목록 반복문 순회
		// postEntity => CardView    => cardViewList에 담는다.
		for (PostEntity post : postList) {   // 0 1 2
			// post 하나에 대응되는 하나의 카드를 만든다.
			CardView cardView = new CardView();
			
			// 글 1개 
			cardView.setPost(post);
			
			// 글쓴이 정보 세팅
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			cardView.setUser(user);
			
			// 댓글들
			List<CommentView> commentList = commentBO.generateCommentViewListByPostId(post.getId());
			cardView.setCommentList(commentList);
			
			// 좋아요 카운트
			// 글에 대해서 좋아요의 개수를 확인
			int likeCount = likeBO.getLikeCountByPostId(post.getId());
			cardView.setLikeCount(likeCount);
			
			// 내가 좋아요 눌렀는지 여부
			// false가 되는 경우 비로그인 또는 누르지 않았을 때
			//if문을 여기에 하면 복잡해보일 수 있으므로 likeBO에게 요청함 (boolean으로 가져옴)
			boolean filledLike = likeBO.filledLike(post.getId(), userId);
			cardView.setFilledLike(filledLike);
			
			//★★★★★ 마지막에 CardViewList에 card를 넣는다.
			cardViewList.add(cardView);
		}
		
		return cardViewList;
	}
}



