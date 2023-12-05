package hcmute.service;

import java.util.List;

import javax.validation.Valid;

import hcmute.dto.AuthorForm;
import hcmute.model.Author;

public interface IAuthorService {

	List<Author> getAllAuthors();

	void insert(@Valid AuthorForm author);

    List<AuthorForm> getAuthorShows();

    AuthorForm getById(long parseLong);

    void updateAuthor(AuthorForm authorForm);
}
