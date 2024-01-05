package com.practica.examen.orchestrator.controller;

import com.practica.examen.orchestrator.dto.TaskDTO;
import com.practica.examen.orchestrator.exception.GlobalExceptionHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${domain.service.url}")
    private String domainServiceUrl;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskDTO> request = new HttpEntity<>(taskDTO, headers);

        return restTemplate.exchange(
                domainServiceUrl +"/tasks",
                HttpMethod.POST,
                request,
                TaskDTO.class
        );
    }


    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(
                domainServiceUrl + "/tasks",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TaskDTO>>() {}
        );
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable String id) {
        return restTemplate.exchange(
                domainServiceUrl +"/tasks/" + id,
                HttpMethod.GET,
                null,
                TaskDTO.class
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @Valid @RequestBody TaskDTO taskDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskDTO> request = new HttpEntity<>(taskDTO, headers);

        return restTemplate.exchange(
                domainServiceUrl +"/tasks/" + id,
                HttpMethod.PUT,
                request,
                TaskDTO.class
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        return restTemplate.exchange(
                domainServiceUrl +"/tasks/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    @ExceptionHandler(GlobalExceptionHandler.TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(GlobalExceptionHandler.TaskNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

