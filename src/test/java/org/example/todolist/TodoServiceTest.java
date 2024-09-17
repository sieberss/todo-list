package org.example.todolist;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    private final TodoRepository mockRepo = mock(TodoRepository.class);
    private final TodoService todoService = new TodoService(mockRepo);

    @Test
    void getAllTodos_withNoEntries() {
        List<TodoElement> expectedList = List.of();
        when(mockRepo.findAll()).thenReturn(expectedList);
        List<TodoElement> result = todoService.getAllTodos();
        verify(mockRepo, times(1)).findAll();
        assertTrue(result.isEmpty());
    }
    @Test
    void getAllTodos_withEntries() {
        TodoElement element = new TodoElement("1", "must do", Status.OPEN);
        List<TodoElement> expectedList = List.of(element);
        when(mockRepo.findAll()).thenReturn(expectedList);
        List<TodoElement> result = todoService.getAllTodos();
        verify(mockRepo, times(1)).findAll();
        assertEquals(result, expectedList);
    }

    @Test
    void addTodo() {
        String description = "my description";
        Status status = Status.OPEN;
        TodoElement expected = new TodoElement("1", description, status);
        when(mockRepo.save(any(TodoElement.class))).thenReturn(expected);
        TodoElement result = todoService.addTodo(description, status);
        verify(mockRepo, times(1)).save(any(TodoElement.class));
        assertEquals(result, expected);
    }

    @Test
    void getTodoById_found() {
        String id = "1";
        TodoElement expected = new TodoElement(id, "descreiöption", Status.DONE);
        when(mockRepo.findById(id)).thenReturn(Optional.of(expected));
        TodoElement result = todoService.getTodoById(id);
        verify(mockRepo, times(1)).findById(id);
        assertEquals(result, expected);
    }
    @Test
    void getTodoById_notFound() {
        assertThrows(NoSuchElementException.class, () -> todoService.getTodoById("1"));
    }

    @Test
    void updateTodo_notFound() {
        String id = "1";
        TodoElement updated = new TodoElement(id, "updateed", Status.DONE);
        assertThrows(NoSuchElementException.class, () -> todoService.updateTodo(updated, id));
    }
    @Test
    void updateTodo_found() {
        String id = "1";
        TodoElement before = new TodoElement(id, "old description", Status.OPEN);
        TodoElement updated = new TodoElement(id, "updateed", Status.DONE);
        when(mockRepo.existsById(id)).thenReturn(true);
        when(mockRepo.save(any(TodoElement.class))).thenReturn(updated);
        TodoElement result = todoService.updateTodo(updated, id);
        verify(mockRepo, times(1)).existsById(id);
        verify(mockRepo, times(1)).save(any(TodoElement.class));
        assertEquals(result, updated);
    }

    @Test
    void deleteTodoById_notFound() {
        String id = "1";
        assertThrows(NoSuchElementException.class, () -> todoService.deleteTodoById(id));
    }

    @Test
    void deleteTodoById_found() {
        String id = "1";
        TodoElement before = new TodoElement(id, "old description", Status.OPEN);
        when(mockRepo.findById(id)).thenReturn(Optional.of(before));
        when(mockRepo.existsById(id)).thenReturn(true);
        assertEquals(todoService.getTodoById(id), before);

        todoService.deleteTodoById(id);
        verify(mockRepo, times(1)).existsById(id);
        verify(mockRepo, times(1)).deleteById(id);
    }

}