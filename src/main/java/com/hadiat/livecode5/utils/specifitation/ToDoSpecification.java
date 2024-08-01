package com.hadiat.livecode5.utils.specifitation;

import com.hadiat.livecode5.model.ToDo;
import com.hadiat.livecode5.model.User;
import org.springframework.data.jpa.domain.Specification;

public class ToDoSpecification {
    public static Specification<ToDo> voucherByUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }
}
