package hcmute.serviceImpl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.model.Contact;
import hcmute.repository.ContactRepository;
import hcmute.service.IContactService;
import hcmute.utils.AppConstant;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactServiceImpl implements IContactService {

	@Autowired
	ContactRepository contactRepository;
	
	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	@Override
	public void insertContact(Contact contact) {
		contactRepository.save(contact);
		
	}

	@Override
	public void deleteById(Long contactId) {
		contactRepository.deleteById(contactId);
		
	}

	@Override
	public Contact getById(Long contactId) {
		Contact contact = contactRepository.findById(contactId).get();
		if (Objects.isNull(contact)) {
			log.error(AppConstant.CONTACT_NOT_FOUND + contactId);
			return null;
		}
		return contact;
		
	}

	@Override
	public void editContact(Contact contact) {
		Contact editContact = contactRepository.findById(contact.getId()).get();
		if (Objects.isNull(editContact)) {
			log.error(AppConstant.CONTACT_NOT_FOUND + contact.getId());
			return;
		}
		editContact.setName(contact.getName());
		editContact.setEmail(contact.getEmail());
		editContact.setTitle(contact.getTitle());
		editContact.setContent(contact.getContent());
		contactRepository.save(editContact);
	}

}
