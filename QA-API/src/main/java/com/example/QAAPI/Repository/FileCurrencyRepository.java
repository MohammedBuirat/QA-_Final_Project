package com.example.QAAPI.Repository;

import com.example.QAAPI.Models.CountryCurrency;
import com.example.QAAPI.Services.FileService;
import java.util.List;

public class FileCurrencyRepository implements ICountryCurrencyRepository {

    @Override
    public CountryCurrency GetCountryCurrency(String country, String currencyCode) {
        try {
            List<CountryCurrency> currencies = FileService.readCurrenciesFromCsv();
            for (var currency : currencies) {
                if (currency.getCountryName().equals(country) && currency.getCurrencyCode().equals(currencyCode)) {
                    return currency;
                }
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    @Override
    public List<CountryCurrency> GetAllCurrencies() {
        try {
            List<CountryCurrency> currencies = FileService.readCurrenciesFromCsv();

            return currencies;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
    }
}
