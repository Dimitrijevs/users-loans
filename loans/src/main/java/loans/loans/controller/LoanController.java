package loans.loans.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import loans.loans.dto.LoanRequest;
import loans.loans.dto.LoanResponse;
import loans.loans.service.LoanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<LoanResponse> allLoans(@RequestParam String userEmail) {

        return loanService.allLoans(userEmail);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse create(@RequestBody @Validated LoanRequest request) {

        return loanService.create(request);
    } 
}
