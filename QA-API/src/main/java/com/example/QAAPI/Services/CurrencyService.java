package com.example.QAAPI.Services;

import com.example.QAAPI.Models.CountryCurrency;
import com.example.QAAPI.Models.DecimalSeparator;
import com.example.QAAPI.Models.ExchangeResult;
import com.example.QAAPI.Repository.ICountryCurrencyRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CurrencyService {
    private ICountryCurrencyRepository _currencyRepository;

    public CurrencyService(ICountryCurrencyRepository currencyRepository) {
        _currencyRepository = currencyRepository;
    }

    public ExchangeResult ExchangeCurrencies(String fromCountry, String fromCurrencyCode
            ,String resultCountry, String resultCurrencyCode, double amount) {
        try {
            ExchangeResult result = new ExchangeResult();
            CountryCurrency fromCurrency = _currencyRepository.GetCountryCurrency(fromCountry, fromCurrencyCode);
            if (fromCurrency == null) {
                throw new NullPointerException("No currency for country " + fromCountry);
            }
            CountryCurrency toCurrency = _currencyRepository.GetCountryCurrency(resultCountry, resultCurrencyCode);
            if (toCurrency == null) {
                throw new NullPointerException("No currency for country " + resultCountry);
            }
            result.setAmount(amount);
            double exchangeRate = toCurrency.getOneUSDValue() / fromCurrency.getOneUSDValue();
            double resultAmount = amount * exchangeRate;
            result.setRate(exchangeRate);
            String resultString = GetStringResultForResultAmount(toCurrency, resultAmount);
            result.setResult(resultString);
            return result;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
    }

    public List<CountryCurrency> GetAllCurrencies() {
        try {
            List<CountryCurrency> currenciesCode = _currencyRepository.GetAllCurrencies();
            List<String> unAllowedCounties = FileService.readUnAllowedCountries();
            currenciesCode.removeIf(currency -> unAllowedCounties.contains(currency.getCountryName()));
            Collections.sort(currenciesCode, Comparator.comparing(CountryCurrency::getCurrencyCode));
            return currenciesCode;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
    }

    private String GetStringResultForResultAmount(CountryCurrency c, double amount) {
        String formattedString = String.format("%.3f", amount);
        if(c.getDecimalSeparator() == DecimalSeparator.COMMA){
            formattedString = formattedString.replaceFirst("\\.", ",");
        }
        return formattedString;
    }
}
