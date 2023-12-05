package hcmute.service;

import java.util.List;

import javax.validation.Valid;

import hcmute.dto.LanguageForm;
import hcmute.model.Language;

public interface ILanguageService {

	List<Language> getAllLanguages();

	void insert(@Valid LanguageForm language);

    List<LanguageForm> getLanguageShows();

	LanguageForm getById(long parseLong);

	void updateLanguage(LanguageForm languageForm);
}
