package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

@Controller
public class TimelineController {
	
//	@Autowired
//	private PostBO postBO;
	
	
	@Autowired
	private TimelineBO timelineBO;
	
	
	@GetMapping("/timeline/timeline-view")
	public String timeline(Model model) {
		//List<PostEntity> postList = postBO.getPostList();
		//model.addAttribute("postList", postList);
		List<CardView> cardViewList = timelineBO.generateCardViewList();

		model.addAttribute("cardList", cardViewList);
		model.addAttribute("viewName", "timeline/timeline");
		return "template/layout";
	}
}
