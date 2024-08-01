package com.hadiat.livecode5.service;

import com.hadiat.livecode5.model.ToDo;
import com.hadiat.livecode5.utils.dto.ToDoRequestDTO;
import com.hadiat.livecode5.utils.dto.ToDoStatusRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToDoService {
    ToDo create(ToDoRequestDTO newToDo);
    Page<ToDo> findAll(Pageable pageable);
    Page<ToDo> findAllToDoByAuthUser(Pageable pageable);
    ToDo findById(Integer id);
    ToDo updateById(Integer id, ToDoRequestDTO updatedToDo);
    ToDo updateStatusById(Integer id, ToDoStatusRequestDTO toDoStatusRequestDTO);
    void deleteById(Integer id);
}
