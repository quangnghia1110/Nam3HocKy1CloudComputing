package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.BookRequest;
@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {

}
