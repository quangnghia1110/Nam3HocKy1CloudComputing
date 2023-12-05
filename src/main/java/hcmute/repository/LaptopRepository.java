package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Laptop;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long>{

	Laptop findByTitle(String string);
	
	
}
