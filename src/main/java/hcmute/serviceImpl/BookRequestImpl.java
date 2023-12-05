package hcmute.serviceImpl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.model.BookRequest;
import hcmute.repository.BookRequestRepository;
import hcmute.service.IBookRequestService;
import hcmute.utils.AppConstant;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class BookRequestImpl implements IBookRequestService {

	@Autowired
	BookRequestRepository bookRequestRepository;
	
	@Override
	public List<BookRequest> getAllRequest() {
		return bookRequestRepository.findAll();
	}

	@Override
	public void insertBookRequest(BookRequest request) {
		bookRequestRepository.save(request);
		
	}

	@Override
	public void deleteById(long bookrequestId) {
		bookRequestRepository.deleteById(bookrequestId);
	}

	@Override
	public BookRequest getById(long bookrequestId) {
		BookRequest request = bookRequestRepository.findById(bookrequestId).get();
		if (Objects.isNull(request)) {
			log.error(AppConstant.REQUEST_NOT_FOUND + bookrequestId);
			return null;
		}
		return request;
	}

	@Override
	public void editRequest(BookRequest bookrequest) {
		BookRequest request = bookRequestRepository.findById(bookrequest.getId()).get();
		if (Objects.isNull(request)) {
			log.error(AppConstant.REQUEST_NOT_FOUND + bookrequest.getId());
			return;
		}
		request.setName(bookrequest.getName());
		request.setEmail(bookrequest.getEmail());
		request.setTitle(bookrequest.getTitle());
		request.setAuthor(bookrequest.getAuthor());
		bookRequestRepository.save(request);
	}

}
