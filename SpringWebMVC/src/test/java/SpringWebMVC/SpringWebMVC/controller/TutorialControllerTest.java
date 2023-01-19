package SpringWebMVC.SpringWebMVC.controller;

import SpringWebMVC.SpringWebMVC.model.Tutorial;
import SpringWebMVC.SpringWebMVC.service.TutorialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TutorialController.class)
class TutorialControllerTest {
    @MockBean
    private TutorialService tutorialService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTutorial() throws Exception {
        long id = 1L;
        Tutorial tutorial = new Tutorial(id, "Spring Test One 1", "Test Description", true);

        when(tutorialService.findById(id)).thenReturn(tutorial);
        mockMvc.perform(get("/api/tutorials/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(tutorial.getTitle()))
                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(tutorial.isPublished()))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundTutorial() throws Exception {
        long id = 1L;

        when(tutorialService.findById(id)).thenReturn(null);
        mockMvc.perform(get("/api/tutorials/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldReturnListOfTutorials() throws Exception {
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial(1, "Spring Test One 1", "Test Description 1", true),
                        new Tutorial(2, "Spring Test Two 2", "Test Description 2", true),
                        new Tutorial(3, "Spring Test Three 3", "Test Description 3", true)));

        when(tutorialService.findAll()).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andExpect(jsonPath("$.[1].id").value(tutorials.get(1).getId()))
                .andExpect(jsonPath("$.[1].title").value(tutorials.get(1).getTitle()))
                .andExpect(jsonPath("$.[1].description").value(tutorials.get(1).getDescription()))
                .andDo(print());
    }

    @Test
    void shouldReturnListOfTutorialsWithFilter() throws Exception {
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial(1, "Spring Test One 1", "Test Description 1", true),
                        new Tutorial(2, "Spring Test Two 2", "Test Description 2", true)));

        String title = "test";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("title", title);

        when(tutorialService.findByTitleContainingIgnoreCase(title)).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());
    }
}