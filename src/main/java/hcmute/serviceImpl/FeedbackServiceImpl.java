package hcmute.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.model.Laptop;
import hcmute.model.Feedback;
import hcmute.model.user.User;
import hcmute.repository.LaptopRepository;
import hcmute.repository.FeedbackRepository;
import hcmute.repository.UserRepository;
import hcmute.service.IFeedbackService;

@Service
public class FeedbackServiceImpl implements IFeedbackService{

	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	LaptopRepository laptopRepository;
	
	@Autowired
	UserRepository userRepository;
	@Override
	public void saveFeedback(Feedback feedback, Long userId, Long bookId) {
		Laptop laptop = laptopRepository.findById(bookId).get();
		feedback.setLaptop(laptop);
		User user = userRepository.findById(userId).get();
		feedback.setUser(user);
		feedbackRepository.save(feedback);
	}
	@Override
	public List<Feedback> getFeedbacksById(Long laptopId) {
		Laptop laptop = laptopRepository.findById(laptopId).get();
		return feedbackRepository.findByLaptop(laptop);
	}
	@Override
	public Feedback getById(Long feedbackId) {
		
		return feedbackRepository.findById(feedbackId).get();
	}
	@Override
	public void deleteById(Long feedbackId) {
		feedbackRepository.deleteById(feedbackId);
		
	}

}
