//package com.example.QAAPI.Services;
//import com.example.QAAPI.Models.CountryCurrency;
//import com.example.QAAPI.Repository.H2CurrencyRepository;
//import com.example.QAAPI.Repository.ICountryCurrencyRepository;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//public class CurrencyServiceWithInMemoryDataBaseTests {
//    private static CurrencyService _currencyService;
//    private static ICountryCurrencyRepository _currencyRepository;
//
//    @BeforeAll
//    public static void setUp() {
//        _currencyRepository = new H2CurrencyRepository();
//        _currencyService = new CurrencyService(_currencyRepository);
//    }
//
//
//
//    @Test
//    void testExchangeCurrenciesShouldThrowNullPointerExceptionWhenTheyDontExist() {
//        assertThrows(NullPointerException.class, () -> {
//            _currencyService.ExchangeCurrencies("country1", "code1", "country2",
//                    "code2" , 0.0);
//        }, "Service Should throw null pointer exception when there is no currency with the given code");
//    }
//
//    @Test
//    void testGetAllCurrenciesShouldNotContainInvalidCountries() {
//        List<CountryCurrency> currencies = _currencyService.GetAllCurrencies();
//        List<String> invalidCountries = FileService.readUnAllowedCountries();
//        for (var currency : currencies) {
//            String currencyCountry = currency.getCountryName();
//            assertFalse(invalidCountries.contains(currencyCountry),
//                    "Currencies should not contain invalid country: " + currencyCountry);
//        }
//    }
//
//    @Test
//    void TestGetAllCurrenciesShouldContainList(){
//        var currenciesString = _currencyService.GetAllCurrencies();
//        assertNotNull(currenciesString);
//        assertFalse(currenciesString.isEmpty());
//    }
//
//    @Test
//    void TestExchangeCurrencies() {
//        var currencies = _currencyService.GetAllCurrencies();
//        var fromCurrency = currencies.get(0);
//        var toCurrency = currencies.get(1);
//        double amountToExchange = 100.0;
//
//        var result = _currencyService.ExchangeCurrencies(fromCurrency.getCountryName(),
//                fromCurrency.getCurrencyCode(),
//                toCurrency.getCountryName(),
//                toCurrency.getCurrencyCode(),
//                amountToExchange);
//        assertNotNull(result);
//        assertEquals(amountToExchange, result.getAmount());
//        assertNotNull(result.getRate());
//        assertNotNull(result.getResult());
//        double exchangeRate = toCurrency.getOneUSDValue() / fromCurrency.getOneUSDValue();
//        double resultAmount = amountToExchange * exchangeRate;
//        assertEquals(result.getRate(), exchangeRate);
//    }
//}
