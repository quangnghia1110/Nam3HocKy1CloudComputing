package hcmute.service;

import java.util.List;

import hcmute.model.Contact;

public interface IContactService  {

	List<Contact> getAllContacts();

	void insertContact(Contact contact);

	void deleteById(Long contactId);

	Contact getById(Long contactId);

	void editContact(Contact contact);

}
