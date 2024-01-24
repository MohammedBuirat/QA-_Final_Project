package com.example.QAAPI.Services;

import com.example.QAAPI.Models.CountryCurrency;
import com.example.QAAPI.Models.DecimalSeparator;
import com.example.QAAPI.Repository.ICountryCurrencyRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CurrencyServiceTestWithMockTests {
    @Mock
    private ICountryCurrencyRepository currencyRepository;
    @InjectMocks
    private CurrencyService _currencyService;
    private ModelMapper modelMapper;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
        modelMapper = new ModelMapper();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TestGetAllCurrenciesShouldContainList() {
        var currency = modelMapper.map(faker, CountryCurrency.class);
        when(currencyRepository.GetAllCurrencies()).thenReturn(Arrays.asList(
                currency
        ));

        List<CountryCurrency> currenciesString = _currencyService.GetAllCurrencies();

        assertNotNull(currenciesString);
        assertFalse(currenciesString.isEmpty());
    }

    @Test
    void testExchangeCurrenciesShouldThrowExceptionWhenSecCurrencyDoesNotExist() {
        var currency = modelMapper.map(faker, CountryCurrency.class);
        when(currencyRepository.GetCountryCurrency("country1", "code1")).thenReturn(currency);
        when(currencyRepository.GetCountryCurrency("country2", "code2")).thenReturn(null);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            var result = _currencyService.ExchangeCurrencies("country1", "code1", "country2", "code2", 0.0);
        });
        assertEquals("No currency for country country2", exception.getMessage());

        assertNotNull(exception, "Service should throw NullPointerException when the currency for the second country does not exist");
    }

    @Test
    void testExchangeCurrenciesShouldThrowExceptionWhenFirstCurrencyDoesNotExist() {
        when(currencyRepository.GetCountryCurrency("country1", "code1")).thenReturn(null);
        var currency2 = modelMapper.map(faker, CountryCurrency.class);
        when(currencyRepository.GetCountryCurrency("country2", "code2")).thenReturn(currency2);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            var result = _currencyService.ExchangeCurrencies("country1", "code1", "country2", "code2", 0.0);
        });

        assertEquals("No currency for country country1", exception.getMessage());

        assertNotNull(exception, "Service should throw NullPointerException when the currency for the first country does not exist");
    }

    @Test
    void TestExchangeCurrencies() {
        var fromCurrency = new CountryCurrency("United States of America", "USD", 1, DecimalSeparator.POINT);
        var toCurrency = new CountryCurrency("Palestine", "LIS", 3.65, DecimalSeparator.POINT);
        when(currencyRepository.GetCountryCurrency(fromCurrency.getCountryName(), fromCurrency.getCurrencyCode())).thenReturn(fromCurrency);
        when(currencyRepository.GetCountryCurrency(toCurrency.getCountryName(), toCurrency.getCurrencyCode())).thenReturn(toCurrency);

        double amountToExchange = 100.0;

        var result = _currencyService.ExchangeCurrencies(fromCurrency.getCountryName(),
                fromCurrency.getCurrencyCode(),
                toCurrency.getCountryName(),
                toCurrency.getCurrencyCode(),
                amountToExchange);
        assertNotNull(result);
        assertEquals(amountToExchange, result.getAmount());
        assertNotNull(result.getRate());
        assertNotNull(result.getResult());
        double exchangeRate = toCurrency.getOneUSDValue() / fromCurrency.getOneUSDValue();
        double resultAmount = amountToExchange * exchangeRate;
        assertEquals(result.getRate(), exchangeRate);
        String formattedResult = "365.000";
        assertEquals(result.getResult(), formattedResult);
    }
}