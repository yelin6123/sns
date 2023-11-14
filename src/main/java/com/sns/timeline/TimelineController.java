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
		Integer userId = (Integer)session.getAttribute("userId");
		
		List<CardView> cardViewList = timelineBO.generateCardViewList(userId);
		
		model.addAttribute("cardList", cardViewList);
		model.addAttribute("viewName", "timeline/timeline");
		return "template/layout";
	}
}