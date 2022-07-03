package com.bkap.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.ContactDto;
import com.bkap.entities.Contact;
import com.bkap.repositories.UserRepository;

@Component
public class ContactConvert {
	
	
	@Autowired
	private UserRepository userRepository;
	
	public Contact toEntity(ContactDto contactDto) {
		Contact contact = new Contact();
		contact.setContent(contactDto.getContent());	
		contact.setEmail(contactDto.getEmail());
		contact.setName(contactDto.getName());
		contact.setStatus(contactDto.getStatus());
		contact.setUser(userRepository.getOne(contactDto.getUserId()));
		return contact;
	}
	
	public Contact toEntity(ContactDto contactDto,Contact contact) {
		contact.setContent(contactDto.getContent());	
		contact.setEmail(contactDto.getEmail());
		contact.setName(contactDto.getName());
		contact.setStatus(contactDto.getStatus());
		contact.setUser(userRepository.getOne(contactDto.getUserId()));
		return contact;
	}
	
	public ContactDto toDto(Contact contact) {
		ContactDto contactDto = new ContactDto();
		contactDto.setContactId(contact.getContactId());
		contactDto.setContent(contact.getContent());
		contactDto.setEmail(contact.getEmail());
		contactDto.setName(contact.getName());
		contactDto.setStatus(contact.getStatus());
		contactDto.setUserId(contact.getUser().getUserId());
		return contactDto;
	}

}
