package com.soyhenry.expenseapp;

import com.soyhenry.expenseapp.controller.ExpenseController;
import com.soyhenry.expenseapp.dto.request.ExpenseRequestDto;
import com.soyhenry.expenseapp.dto.response.ExpenseResponseDto;
import com.soyhenry.expenseapp.exception.DAOException;
import com.soyhenry.expenseapp.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = ExpenseAppApplication.class)

@AutoConfigureMockMvc
class ExpenseControllerTests {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void updateExpense() throws Exception {
        // Mocking the service
        Mockito.when(expenseService.updateExpense(eq(1L), any(ExpenseRequestDto.class))).thenReturn("Se actualizó el gasto con éxito");

        // Performing the request and verifying the result
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/expense/update?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0, \"categoryId\": 1, \"categoryName\": \"YourCategoryName\", \"date\": \"2023-01-01\"}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().string("Se actualizó el gasto con éxito"));
    }



    @Test
    void deleteExpense() throws Exception {
        // Performing the request and verifying the result
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/expense/1"))
                .andExpect(MockMvcResultMatchers.status().isGone())
                .andExpect(MockMvcResultMatchers.content().string("Se eliminó el gasto con id: 1"));
    }

    @Test
    void getExpenses() throws Exception {
        // Mocking the service
        Mockito.when(expenseService.getAllExpenses()).thenReturn(Collections.emptyList());

        // Performing the request and verifying the result
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expense"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

}
