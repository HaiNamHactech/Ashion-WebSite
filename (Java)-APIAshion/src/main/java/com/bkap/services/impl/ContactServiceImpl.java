package com.bkap.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bkap.convert.ContactConvert;
import com.bkap.dto.ContactDto;
import com.bkap.entities.Contact;
import com.bkap.filter.ContactFilter;
import com.bkap.repositories.ContactRepository;
import com.bkap.services.IContactService;

@Service
public class ContactServiceImpl implements IContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ContactConvert convert;

	@Override
	public List<ContactDto> getAll() {
		List<Contact> listContact = contactRepository.findAll();
		List<ContactDto> listContactDto = new ArrayList<ContactDto>();
		listContact.forEach(item -> listContactDto.add(convert.toDto(item)));
		return listContactDto;
	}

	@Override
	public ContactDto findOne(int contactId) {
		return convert.toDto(contactRepository.getOne(contactId));
	}

	@Override
	public ContactDto save(ContactDto contactDto) {
		Contact contact = new Contact();
		if(contactDto.getContactId() != 0) {
			Contact oldContact = contactRepository.getOne(contactDto.getContactId());
			contact = contactRepository.save(convert.toEntity(contactDto, oldContact));
		}
		else {
			contact = contactRepository.save(convert.toEntity(contactDto));
		}
		return convert.toDto(contact);
	}

	@Override
	public ContactDto edit(ContactDto contactDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(int contactId) {
		contactRepository.deleteById(contactId);
		
	}

	@Override
	public Page<ContactDto> where(ContactFilter filter) {
		Page<ContactDto> pageContact = null;
		List<ContactDto> listContactDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Contact> listContact = contactRepository.findAll(new Specification<Contact>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getContactId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("contactId"), filter.getContactId()));
				}
				if(filter.getContent() != null && !filter.getContent().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("content"), "%" + filter.getContent() + "%"));
				}
				if (filter.getUserId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("userId"), filter.getUserId()));
				}
				if(filter.getEmail() != null && !filter.getEmail().isEmpty() ) {
					predicates.add(criteriaBuilder.equal(root.get("email"), filter.getEmail()));
				}			
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		});
		if (listContact != null) {
			listContact.forEach(item -> { listContactDto.add(convert.toDto(item));});
			Collections.reverse(listContactDto);
			List<ContactDto> output = new ArrayList<>();
			int total = listContactDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listContactDto.subList(start, end);
			}
			pageContact = new PageImpl<ContactDto>(output, pageRequest, total);
		}
		return pageContact;
	}

}
