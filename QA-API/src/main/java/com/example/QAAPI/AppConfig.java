package com.example.QAAPI;
import com.example.QAAPI.Repository.FileCurrencyRepository;
import com.example.QAAPI.Repository.H2CurrencyRepository;
import com.example.QAAPI.Repository.ICountryCurrencyRepository;
import com.example.QAAPI.Repository.MySqlCurrencyRepository;
import com.example.QAAPI.Services.CurrencyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ICountryCurrencyRepository currencyRepository() {
        return new FileCurrencyRepository();
    }

    @Bean
    public CurrencyService currencyService(ICountryCurrencyRepository currencyRepository) {
        return new CurrencyService(currencyRepository);
    }
}