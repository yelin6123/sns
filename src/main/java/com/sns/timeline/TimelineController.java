package com.sns.timeline;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

@Controller
public class TimelineController {
	@Autowired
	private TimelineBO timelineBO;

	@GetMapping("/timeline/timeline-view")
	public String timelineView(Model model, HttpSession session) {
		//null이거나 아니거나 (비로그인도 에러가 나면 안되기 때문에)
		Integer userId = (Integer)session.getAttribute("userId");
		//userId 를 BO에게 넘김 
		List<CardView> cardViewList = timelineBO.generateCardViewList(userId);
		
		model.addAttribute("cardList", cardViewList);
		model.addAttribute("viewName", "timeline/timeline");
		return "template/layout";
	}
}