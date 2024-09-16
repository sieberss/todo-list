package org.example.todolist;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<TodoElement, String> {
}
