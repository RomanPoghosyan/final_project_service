package com.example.demo.controllers;

import static org.mockito.Mockito.verify;
import com.example.demo.models.requests.TaskRequest;
import com.example.demo.services.TaskService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;
import java.security.Principal;

public class TaskControllerTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
}
