package com.hadiat.livecode5.service.impl;

import com.hadiat.livecode5.model.ToDo;
import com.hadiat.livecode5.model.User;
import com.hadiat.livecode5.model.enums.EStatus;
import com.hadiat.livecode5.repository.ToDoRepository;
import com.hadiat.livecode5.service.AuthenticationService;
import com.hadiat.livecode5.service.ToDoService;
import com.hadiat.livecode5.utils.dto.ToDoRequestDTO;
import com.hadiat.livecode5.utils.dto.ToDoStatusRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;
    private final AuthenticationService authenticationService;

    @Override
    public ToDo create(ToDoRequestDTO newToDo) {
        User user = authenticationService.getUserAuthenticated();
        return toDoRepository.save(
                ToDo.builder()
                        .title(newToDo.getTitle())
                        .description(newToDo.getDescription())
                        .dueDate(newToDo.getDueDate())
                        .status(EStatus.PENDING)
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .build()
                );
    }

    @Override
    public Page<ToDo> findAll(Pageable pageable) {
        return toDoRepository.findAll(pageable);
    }

    @Override
    public Page<ToDo> findAllToDoByAuthUser(Pageable pageable) {
        return toDoRepository.findAll(pageable);
    }

    @Override
    public ToDo findById(Integer id) {
        return toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("To Do Not Found"));
    }


    @Override
    public ToDo updateById(Integer id, ToDoRequestDTO updatedToDo) {
        ToDo selectedToDo = findById(id);
        if (!updatedToDo.getTitle().isEmpty()) selectedToDo.setTitle(updatedToDo.getTitle());
        if (!updatedToDo.getDescription().isEmpty()) selectedToDo.setDescription(updatedToDo.getDescription());
        if (updatedToDo.getStatus().describeConstable().isPresent()) selectedToDo.setStatus(updatedToDo.getStatus());
        if (updatedToDo.getDueDate().isAfter(LocalDate.of(2020, 1,1))) selectedToDo.setDueDate(updatedToDo.getDueDate());
        return toDoRepository.save(selectedToDo);
    }

    @Override
    public ToDo updateStatusById(Integer id, ToDoStatusRequestDTO toDoStatusRequestDTO) {
        return updateById(
                id,
                ToDoRequestDTO.builder()
                        .status(toDoStatusRequestDTO.getStatus())
                        .title("")
                        .description("")
                        .dueDate(LocalDate.of(1999,1,1))
                        .build()
        );
    }

    @Override
    public void deleteById(Integer id) {
        toDoRepository.deleteById(id);
    }
}
