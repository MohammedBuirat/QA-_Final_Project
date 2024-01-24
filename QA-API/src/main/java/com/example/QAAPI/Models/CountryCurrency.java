package com.example.QAAPI.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryCurrency {
    private String CountryName;
    private String CurrencyCode;
    private double OneUSDValue;
    private DecimalSeparator DecimalSeparator;
}