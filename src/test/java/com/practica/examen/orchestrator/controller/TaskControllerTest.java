package com.practica.examen.orchestrator.controller;

import com.practica.examen.orchestrator.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TaskController taskController;

    @Test
    void createTaskTest() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");

        when(restTemplate.exchange(
                any(String.class),
                any(),
                any(),
                (Class<TaskDTO>) any())
        ).thenReturn(new ResponseEntity<>(taskDTO, HttpStatus.CREATED));

        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Task", response.getBody().getTitle());
        assertEquals("Test Description", response.getBody().getDescription());
    }
}
