package com.renato.hexagonal.adapters.in.controller;

import com.renato.hexagonal.adapters.in.mapper.DebitMapper;
import com.renato.hexagonal.adapters.in.request.DebitCancelRequest;
import com.renato.hexagonal.adapters.in.request.DebitCreateRequest;
import com.renato.hexagonal.adapters.in.response.DebitResponse;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.ports.in.CancelDebitInputPort;
import com.renato.hexagonal.application.ports.in.CreateDebitInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/debit")
public class DebitController {

    @Autowired
    private CreateDebitInputPort createDebitInputPort;

    @Autowired
    private CancelDebitInputPort cancelDebitInputPort;

    @Autowired
    private DebitMapper debitMapper;

    @PostMapping
    public ResponseEntity<DebitResponse> create(@Valid @RequestBody DebitCreateRequest debitRequest) {
        Debit debit = debitMapper.toDebitCreate(debitRequest);
        createDebitInputPort.create(debit);

        URI location = URI.create("/api/v1/debit/" + debit.getId());

        return ResponseEntity.created(location).body(debitMapper.toDebitResponse(debit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DebitResponse> cancel(@PathVariable final String id, @Valid @RequestBody DebitCancelRequest debitRequest) {

        Debit debit = debitMapper.toDebitCancel(debitRequest);
        debit.setId(id);
        debit = cancelDebitInputPort.cancel(debit);
        return ResponseEntity.ok(debitMapper.toDebitResponse(debit));

    }



}
