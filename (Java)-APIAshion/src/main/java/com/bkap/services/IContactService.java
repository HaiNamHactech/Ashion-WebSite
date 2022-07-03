package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.ContactDto;
import com.bkap.filter.ContactFilter;

@Service
public interface IContactService {
	
	List<ContactDto> getAll();
	
	Page<ContactDto> where(ContactFilter filter);
	
	ContactDto findOne(int blogId);
	
	ContactDto save(ContactDto contactDto);
	
	ContactDto edit(ContactDto contactDto);
	
	void remove(int contactId);

}
