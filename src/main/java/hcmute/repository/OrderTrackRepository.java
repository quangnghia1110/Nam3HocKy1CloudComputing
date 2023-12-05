package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.order.OrderTrack;

@Repository
public interface OrderTrackRepository extends JpaRepository<OrderTrack, Long> {

	OrderTrack findByStatus(String string);

}
