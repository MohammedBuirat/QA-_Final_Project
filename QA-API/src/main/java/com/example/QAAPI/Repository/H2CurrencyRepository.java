package com.example.QAAPI.Repository;

import com.example.QAAPI.Models.CountryCurrency;
import com.example.QAAPI.Models.DecimalSeparator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2CurrencyRepository implements ICountryCurrencyRepository {

    private static final String JDBC_URL = "jdbc:h2:mem:currency-exchange";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @Override
    public CountryCurrency GetCountryCurrency(String country, String currencyCode) {
        CountryCurrency currency = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "SELECT * FROM CURRENCYEXCHANGE WHERE COUNTRYNAME = ? AND CURRENCYCODE = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, country);
                preparedStatement.setString(2, currencyCode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String countryName = resultSet.getString("CountryName");
                        String code = resultSet.getString("CurrencyCode");
                        double usdValue = resultSet.getDouble("ExchangeRate");
                        var format = DecimalSeparator.Parse(resultSet.getString("DecimalSeparator"));

                        currency = new CountryCurrency(countryName, code, usdValue, format);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currency;
    }

    @Override
    public List<CountryCurrency> GetAllCurrencies() {
        List<CountryCurrency> currencies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "SELECT * FROM CURRENCYEXCHANGE";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String countryName = resultSet.getString("CountryName");
                        String currencyCode = resultSet.getString("CurrencyCode");
                        double usdValue = resultSet.getDouble("ExchangeRate");
                        var format = DecimalSeparator.Parse(resultSet.getString("DecimalSeparator"));

                        var currency = new CountryCurrency(countryName, currencyCode, usdValue, format);
                        currencies.add(currency);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencies;
    }
}
//fix