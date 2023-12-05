package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Laptop;
import hcmute.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

	List<Feedback> findByLaptop(Laptop laptop);
}
