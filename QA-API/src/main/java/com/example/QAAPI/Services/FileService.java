package com.example.QAAPI.Services;

import com.example.QAAPI.Models.CountryCurrency;
import com.example.QAAPI.Models.DecimalSeparator;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public static List<String> readUnAllowedCountries() {
        List<String> unAllowedCountries = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("C:\\Users\\ASUS\\Desktop\\QA-Final-Project\\QA-API\\src\\main\\java\\com\\example\\QAAPI\\ForbiddenCountries.csv")).build()) {
            String[] currencies;
            while ((currencies = csvReader.readNext()) != null) {
                for (String currency : currencies) {
                    unAllowedCountries.add(currency.trim());
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return unAllowedCountries;
    }

    public static List<CountryCurrency> readCurrenciesFromCsv() {
        List<CountryCurrency> currencies = new ArrayList<>();

        String path = "C:\\Users\\ASUS\\Desktop\\QA-Final-Project\\QA-API\\src\\main\\java\\com\\example\\QAAPI\\CountryCurrency.csv";

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] header = reader.readNext();

            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String countryName = nextRecord[0];
                String code = nextRecord[1];
                String value = nextRecord[2];
                String format = nextRecord[3];

                var currency = new CountryCurrency(countryName, code, Double.parseDouble(value)
                        , DecimalSeparator.Parse(format));
                currencies.add(currency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencies;
    }
}
