package hcmute.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hcmute.dto.LaptopForm;
import hcmute.model.Laptop;
import hcmute.model.Feedback;
import hcmute.model.user.User;
import hcmute.security.UserPrincipal;
import hcmute.service.ILaptopService;
import hcmute.service.IFeedbackService;
import hcmute.service.IOrderService;
import hcmute.service.IUserService;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequestMapping("/shop-detail")
@Slf4j
public class ShopDetailController {

	@Autowired
	ILaptopService bookService;
	
	@Autowired
	IFeedbackService feedbackService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IOrderService orderService;
	
	@GetMapping
	public ModelAndView viewShopDetailPage(
			ModelAndView mav,
			@RequestParam("bookId") String bookId) throws ParseException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Boolean isComment = false;
		try {
			User user = new User();
			
			if(Objects.nonNull(authentication)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				user = userService.getUserById(userPrincipal.getId());
				isComment = orderService.ExistByUserAndBook(user, Long.parseLong(bookId));

			}
			
		}catch(Exception e) {
			
		}
		mav.addObject("isComment", isComment.toString());
		
		
		

		
		LaptopForm book = bookService.getById(Long.parseLong(bookId)); 
		int sold = bookService.getSoldNumberById(Long.parseLong(bookId));
		List<Laptop> topFeatured = bookService.getTopFeatured();
		List<Feedback> feedbacks = feedbackService.getFeedbacksById(Long.parseLong(bookId));
		
		mav.addObject("feedbacks", feedbacks);
		mav.addObject("topFeatured",topFeatured);
		mav.addObject("sold",sold);
		mav.addObject("book",book);
		Feedback feedback = new Feedback();
		feedback.setRating(5);
		mav.addObject("feedback",feedback);
		mav.setViewName("client/shop-detail");
		return mav;
	}
}
