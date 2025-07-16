package com.renato.hexagonal.adapters.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renato.hexagonal.adapters.in.mapper.DebitMapper;
import com.renato.hexagonal.adapters.in.request.DebitCancelRequest;
import com.renato.hexagonal.adapters.in.request.DebitCreateRequest;
import com.renato.hexagonal.adapters.in.response.DebitResponse;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.ports.in.CancelDebitInputPort;
import com.renato.hexagonal.application.ports.in.CreateDebitInputPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DebitController.class)
public class DebitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateDebitInputPort createDebitInputPort;

    @MockitoBean
    private CancelDebitInputPort cancelDebitInputPort;

    @MockitoBean
    private DebitMapper debitMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDebitSuccessfully() throws Exception {

        DebitCreateRequest request = new DebitCreateRequest();
        request.setAmount(new BigDecimal("200.0"));
        request.setReference("REF 123");

        Debit mockDebit = new Debit("REF 123", new BigDecimal("200.0"));

        DebitResponse debitResponse = new DebitResponse(mockDebit.getId(),
                mockDebit.getEvent(),mockDebit.getAmount());

        Mockito.when(debitMapper.toDebitCreate(any())).thenReturn(mockDebit);
        Mockito.when(debitMapper.toDebitResponse(any())).thenReturn(debitResponse);

        mockMvc.perform(post("/api/v1/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(createDebitInputPort).create(mockDebit);
    }

    @Test
    void shouldCancelDebitSuccessfully() throws Exception {
        String id = "abc123";
        DebitCancelRequest request = new DebitCancelRequest();
        request.setReference("REF 123");

        Debit mockDebit = new Debit("REF 123", new BigDecimal("200.0"));
        mockDebit.setId(id);

        DebitResponse debitResponse = new DebitResponse(mockDebit.getId(),
                mockDebit.getEvent(),mockDebit.getAmount());

        Mockito.when(debitMapper.toDebitCancel(any())).thenReturn(mockDebit);
        Mockito.when(debitMapper.toDebitResponse(any())).thenReturn(debitResponse);

        mockMvc.perform(put("/api/v1/debit/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(cancelDebitInputPort).cancel(mockDebit);
    }


}