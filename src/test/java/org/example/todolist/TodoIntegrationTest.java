package org.example.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoRepository todoRepository;

    private static TodoElement element = new TodoElement("1", "Aufgabe", Status.OPEN);
    private static String elementJson = """
            {
                "id": "1",
                "description": "Aufgabe",
                "status": "OPEN"
            }
    """;
    private static String elementJsonWithoutId = """
            {
                "description": "Aufgabe",
                "status": "OPEN"
            }
    """;
    private static String elementJsonChanged = """
            {
                "id": "1",
                "description": "neue Aufgabe",
                "status": "DONE"
            }
    """;

    private static String listJson = "[\n" + elementJson + "\n]";

    @Test
    @DirtiesContext
    void testGetAll_oneEntryStatus200() throws Exception {
        todoRepository.save(element);
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json(listJson));
    }

    @Test
    @DirtiesContext
    void testGetAll_noEntryStatus200() throws Exception {
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void testGetById_foundStatus200() throws Exception {
        todoRepository.save(element);
        mockMvc.perform(get("/api/todo/" + element.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(elementJson));
    }

    @Test
    @DirtiesContext
    void testGetById_notFoundStatus404() throws Exception {
        mockMvc.perform(get("/api/todo/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ID not found: " + element.id()));
    }

    @Test
    @DirtiesContext
    void testAdd() throws Exception {
        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(elementJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(elementJsonWithoutId));
    }

    

}
