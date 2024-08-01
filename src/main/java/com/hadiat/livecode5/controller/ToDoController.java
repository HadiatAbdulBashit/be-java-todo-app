package com.hadiat.livecode5.controller;

import com.hadiat.livecode5.model.ToDo;
import com.hadiat.livecode5.service.ToDoService;
import com.hadiat.livecode5.utils.dto.*;
import com.hadiat.livecode5.utils.responseWrapper.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping
    public ResponseEntity<ToDo> create(@RequestBody ToDoRequestDTO request) {
        return new ResponseEntity<>(toDoService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllToDo(@PageableDefault Pageable pageable) {
        Page<ToDo> voucherPage = toDoService.findAll(pageable);
        return new ResponseEntity<>(new PaginationResponse<>(voucherPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> findToDoById(@PathVariable Integer id) {
        return new ResponseEntity<>(toDoService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDoById(@PathVariable Integer id, @RequestBody ToDoRequestDTO toDoRequestDTO) {
        return new ResponseEntity<>(toDoService.updateById(id, toDoRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ToDo> updateStatusToDoById(@PathVariable Integer id, @RequestBody ToDoStatusRequestDTO toDoStatusRequestDTO) {
        return new ResponseEntity<>(toDoService.updateStatusById(id, toDoStatusRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ToDo> deleteToDoById(@PathVariable Integer id) {
        toDoService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
