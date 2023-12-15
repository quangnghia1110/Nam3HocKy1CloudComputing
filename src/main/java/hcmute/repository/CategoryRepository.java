package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hcmute.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String string);
	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.image")
	List<Category> findAllWithImage();
	
}
