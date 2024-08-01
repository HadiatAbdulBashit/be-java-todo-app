package com.hadiat.livecode5.repository;

import com.hadiat.livecode5.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer>, JpaSpecificationExecutor<ToDo> {
}
