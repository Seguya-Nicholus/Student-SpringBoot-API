package com.example.mvcdemo.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

   @Autowired
   MockMvc mockMvc;

   @Autowired
   ObjectMapper mapper;

   @MockBean
   StudentService studentService;

    Student RECORD_1 = new Student(1l, "Seguya Nicholas", "seguyanicholus@yahoo.co", LocalDate.of(1988, Month.NOVEMBER,6));
    Student RECORD_2 = new Student(2l, "Nakandi Sylvia", "nakawilliams@gmail.com", LocalDate.of(1989, Month.JUNE,13));
    Student RECORD_3 = new Student(3l, "Nalubega Nicolette Shalom", "nalubegashalom@gmail.com", LocalDate.of(2017, Month.MAY,13));

    @Test
    public void getStudents_success() throws Exception {
        List<Student> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(studentService.getStudents()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Nalubega Nicolette Shalom")));
    }

    @Test
    public void getStudentById_success() throws Exception {
        Mockito.when(studentService.getStudent(RECORD_1.getId())).thenReturn(RECORD_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/student/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Seguya Nicholas")));
    }

    @Test
    public void registerNewStudent_success() throws  Exception {
        Student RECORD_4 = new Student(
                4l,
                "Kasamba Julius Enock",
                "kasjuli@gmail.com",
                LocalDate.of(1978, Month.MAY,6)
        );

//        Mockito.when(studentService.addNewStudent(RECORD_4)).thenReturn(null);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(RECORD_4));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }


    @Test
    public void updateStudentRecord_success() throws Exception {
        Student updatedRecord = RECORD_1;

        Mockito.when(studentService.getStudent(RECORD_1.getId())).thenReturn(RECORD_1);
//        Mockito.when(studentService.updateStudent(updatedRecord)).thenReturn(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudentById_success() throws Exception {
//        Mockito.when(studentService.getStudent(RECORD_2.getId())).then(studentService.deleteStudent(RECORD_2.getId()));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/student/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
