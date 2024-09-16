package org.example.todolist;

import java.util.UUID;

public class IdService {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
