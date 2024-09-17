package org.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repository;

    public List<TodoElement> getAllTodos(){
        return repository.findAll();
    }

    public TodoElement addTodo(String name, Status status) {
        TodoElement created = new TodoElement(IdService.generateId(), name, status);
        return repository.save(created);
    }

    public TodoElement getTodoById(String id) {
        return repository.findById(id).orElseThrow(()-> new NoSuchElementException("ID not found: " + id));
    }

    public TodoElement updateTodo(TodoElement submitted, String id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("ID not found: " + id);
        }
        if (!id.equals(submitted.id())){
                throw new IllegalArgumentException("Change of ID not allowed");
        }
        return repository.save(submitted);
    }

    public void deleteTodoById(String id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("ID not found: " + id);
        }
        repository.deleteById(id);
    }
}
