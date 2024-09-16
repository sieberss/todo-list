package org.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public boolean deleteTodo(@PathVariable String id){
        return service.deleteTodoById(id);
    }
}
