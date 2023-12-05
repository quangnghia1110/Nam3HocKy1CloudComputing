package hcmute.service;

import java.util.List;

import hcmute.model.Feedback;

public interface IFeedbackService {

	void saveFeedback(Feedback feedback, Long userId, Long bookId);

	List<Feedback> getFeedbacksById(Long bookId);

	Feedback getById(Long feedbackId);

	void deleteById(Long feedbackId);

}
