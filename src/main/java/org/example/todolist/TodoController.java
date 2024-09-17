package org.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping
    public List<TodoElement> getTodos(){
        return service.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoElement getTodoById(@PathVariable String id){
        return service.getTodoById(id);
    }

    @PostMapping
    public TodoElement addTodo(@RequestBody TodoElement submitted){
        return service.addTodo(submitted.description(), submitted.status());
    }

    @PutMapping("/{id}")
    public TodoElement updateTodo(@PathVariable String id, @RequestBody TodoElement submitted){
        return service.updateTodo(submitted, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id){
        service.deleteTodoById(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException e){
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(Exception e){
        return e.getMessage();
    }
}
