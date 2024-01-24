package com.example.QAAPI.Repository;

import com.example.QAAPI.Models.CountryCurrency;

import java.util.List;

public interface ICountryCurrencyRepository {
    public CountryCurrency GetCountryCurrency(String country, String currencyCode);
    public List<CountryCurrency> GetAllCurrencies();
}


