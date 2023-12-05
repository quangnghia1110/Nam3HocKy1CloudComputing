package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{

}
