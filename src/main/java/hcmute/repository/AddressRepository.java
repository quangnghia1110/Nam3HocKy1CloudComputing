package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.user.Address;
import hcmute.model.user.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByUser(User user);

}
