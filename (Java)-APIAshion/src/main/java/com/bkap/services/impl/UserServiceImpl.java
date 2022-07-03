package com.bkap.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bkap.convert.UserConvert;
import com.bkap.dto.UserDto;
import com.bkap.entities.Users;
import com.bkap.filter.UserFilter;
import com.bkap.repositories.UserRepository;
import com.bkap.services.IUserService;
import com.bkap.until.MyUser;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConvert convert;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<UserDto> getAll() {
		List<Users> listUser = userRepository.findAll();
		List<UserDto> listUserDto = new ArrayList<UserDto>();
		listUser.forEach(item -> listUserDto.add(convert.toDto(item)));
		return listUserDto;
	}
	
	@Override
	public UserDto findOne(int userID) {
		return convert.toDto(userRepository.getOne(userID));
	}
	
	@Override
	public UserDto findByFirstName(String firstName ) {
		return convert.toDto(userRepository.findByFirstName(firstName));
	}

	@Override
	public UserDto save(UserDto userDto) {
		userDto.setPassWord(passwordEncoder.encode(userDto.getPassWord()));
		Users user = new Users();
		if (userDto.getUserId() != 0) {
			Users oldUser = userRepository.getOne(userDto.getUserId());
			user = userRepository.save(convert.toEntity(userDto, oldUser));
		} else {
			user = userRepository.save(convert.toEntity(userDto));
		}
		return convert.toDto(user);
	}

	@Override
	public UserDto edit(UserDto userDto) {
		return null;
	}

	@Override
	public void remove(int userID) {
		userRepository.deleteById(userID);

	}

	@Override
	public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
		Users user = userRepository.findByFirstName(firstName);
		if (user == null) {
			return null;
		}
		MyUser myUser = new MyUser(firstName, user.getPassWord(), true, true, true, true, user.getAuthorities());
		myUser.setFullName(firstName);
		myUser.setUser(user);
		return myUser;
	}

	public UserDetails loadUserById(int id) throws UsernameNotFoundException {
		Optional<Users> user = userRepository.findById(id);
		System.out.println(user.get().getFirstName());
		if (!user.isPresent()) {
			return null;
		}
		MyUser myUser = new MyUser(user.get().getFirstName(), user.get().getPassWord(), true, true, true, true,
				user.get().getAuthorities());
		myUser.setFullName(user.get().getFirstName());
		myUser.setUser(user.get());
		return myUser;
	}



	@Override
	public Page<UserDto> where(UserFilter filter) {
		Page<UserDto> pageUser= null;
		List<UserDto> listUserDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Users> listUser = userRepository.findAll(new Specification<Users>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getUserId()!= 0) {
					predicates.add(criteriaBuilder.equal(root.get("userId"), filter.getUserId()));
				}	
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		if (listUser != null) {
			listUser.forEach(item -> { listUserDto.add(convert.toDto(item));});
			Collections.reverse(listUserDto);
			List<UserDto> output = new ArrayList<>();
			int total = listUserDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listUserDto.subList(start, end);
			}
			pageUser = new PageImpl<UserDto>(output, pageRequest, total);
		}
		return pageUser;
	}

	@Override
	public UserDto findByEmail(String email) {
		return convert.toDto(userRepository.findByEmail(email));
	}





}
