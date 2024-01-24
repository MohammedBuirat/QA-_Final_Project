package com.example.QAAPI.Models;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResult {
    double Amount;
    String Result;
    double Rate;
}
