package com.order.erp.web.controller.specification;

public class UserSpecification {
	
	/*
	public static Specification<User> isLongTermUser() {
		return new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> idPath = root.get("channel").get("id");
				return builder.lessThan(root.get(_User.createdAt), date);
			}
		};
	}

	public static Specification<User> hasSalesOfMoreThan(Long AmoebaId) {
		return new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get(User), value);
				// build query here
			}
		};
	}
	*/
}
