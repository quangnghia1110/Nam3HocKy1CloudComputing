package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import hcmute.model.Laptop;
import hcmute.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

	List<Feedback> findByLaptop(Laptop laptop);
=======
import hcmute.model.Book;
import hcmute.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

	List<Feedback> findByBook(Book book);
>>>>>>> branch 'master' of https://github.com/quangnghia1110/doancuoiky.git

}
