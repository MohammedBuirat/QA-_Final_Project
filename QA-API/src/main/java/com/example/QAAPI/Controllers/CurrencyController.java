package com.example.QAAPI.Controllers;

import com.example.QAAPI.Models.CountryCurrency;
import com.example.QAAPI.Models.ExchangeResult;
import com.example.QAAPI.Services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/exchange")
    public ResponseEntity<?> exchangeCurrencies(
            @RequestParam String fromCountry,
            @RequestParam String fromCurrency,
            @RequestParam String toCountry,
            @RequestParam String toCurrency,
            @RequestParam double amount) {

        try {
            ExchangeResult result = currencyService.ExchangeCurrencies(fromCountry, fromCurrency, toCountry, toCurrency, amount);
            return ResponseEntity.ok(result);
        } catch (NullPointerException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/currencies")
    public List<CountryCurrency> getAllCurrenciesCodes() {
        return currencyService.GetAllCurrencies();
    }
}
