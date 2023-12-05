package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

	Language findByName(String string);

}
