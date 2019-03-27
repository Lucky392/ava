package com.ava.task.repository.specification;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ava.task.dto.impl.UserDTO;
import com.ava.task.model.impl.User;

public class UserSpecification implements Specification<User> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -338752693702603353L;
	
	private UserDTO criteria;
	
	public UserSpecification(UserDTO userDTO) {
		criteria = userDTO;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		final List<Predicate> predicates = new LinkedList<Predicate>();
		if (criteria != null) {			
			firstNamePredicate(root, criteriaBuilder, predicates);
			lastNamePredicate(root, criteriaBuilder, predicates);
			emailPredicate(root, criteriaBuilder, predicates);
			addressPredicate(root, criteriaBuilder, predicates);
			countryPredicate(root, criteriaBuilder, predicates);
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	private void countryPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, final List<Predicate> predicates) {
		if (!StringUtils.isEmpty(criteria.getCountry())) {
			predicates.add(criteriaBuilder.equal(root.get("country"), criteria.getCountry()));
		}
	}

	private void addressPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, final List<Predicate> predicates) {
		if (!StringUtils.isEmpty(criteria.getAddress())) {
			predicates.add(criteriaBuilder.equal(root.get("address"), criteria.getAddress()));
		}
	}

	private void emailPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, final List<Predicate> predicates) {
		if (!StringUtils.isEmpty(criteria.getEmail())) {
			predicates.add(criteriaBuilder.equal(root.get("email"), criteria.getEmail()));
		}
	}

	private void lastNamePredicate(Root<User> root, CriteriaBuilder criteriaBuilder, final List<Predicate> predicates) {
		if (!StringUtils.isEmpty(criteria.getLastName())) {
			predicates.add(criteriaBuilder.equal(root.get("lastName"), criteria.getLastName()));
		}
	}

	private void firstNamePredicate(Root<User> root, CriteriaBuilder criteriaBuilder,
			final List<Predicate> predicates) {
		if (!StringUtils.isEmpty(criteria.getFirstName())) {
			predicates.add(criteriaBuilder.equal(root.get("firstName"), criteria.getFirstName()));
		}
	}

}
