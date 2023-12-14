package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Image;
import hcmute.model.user.User;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>  {

	Image findByUser(User user);

	Image findByUrl(String imageUrl);

	Image findByTitle(String title);

}
