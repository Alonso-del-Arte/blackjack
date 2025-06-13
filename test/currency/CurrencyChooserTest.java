/*
 * Copyright (C) 2025 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package currency;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.testframe.api.Asserters.assertContainsSame;
import static org.testframe.api.Asserters.assertMinimum;
import static org.testframe.api.Asserters.assertThrows;
import static org.testframe.api.Asserters.assertTimeout;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CurrencyChooser class.
 * @author Alonso del Arte
 */
public class CurrencyChooserTest {
    
    private static final Set<Currency> CURRENCIES 
            = Currency.getAvailableCurrencies();
    
    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();
    
    private static final Map<Integer, Set<Currency>> FRACT_DIGITS_MAP 
            = new HashMap<>();
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final int TOTAL_NUMBER_OF_CURRENCIES;
    
    private static final int NUMBER_OF_CALLS_MULTIPLIER_FOR_EXCLUSION_SEARCH 
            = 4;
    
    private static final int NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH 
            = NUMBER_OF_CALLS_MULTIPLIER_FOR_EXCLUSION_SEARCH 
            * CURRENCIES.size();
    
    private static final String[] EURO_REPLACED_EXCLUSION_CODES = {"ADP", "ATS", 
        "BEF", "CYP", "DEM", "EEK", "ESP", "FIM", "FRF", "GRD", "IEP", "ITL", 
        "LUF", "MTL", "NLG", "PTE", "SIT"};
    
    private static final String[] OTHER_EXCLUSION_CODES = {"AYM", "BGL", "BOV", 
        "CHE", "CHW", "COU", "GWP", "MGF", "MXV", "SRG", "STN", "TPE", "USN", 
        "USS", "UYI", "VED", "ZWN"};
    
    static {
        for (Currency currency : CURRENCIES) {
            int fractDigits = currency.getDefaultFractionDigits();
            if (fractDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                Set<Currency> digitGroupedSet;
                if (FRACT_DIGITS_MAP.containsKey(fractDigits)) {
                    digitGroupedSet = FRACT_DIGITS_MAP.get(fractDigits);
                } else {
                    digitGroupedSet = new HashSet<>();
                    FRACT_DIGITS_MAP.put(fractDigits, digitGroupedSet);
                }
                digitGroupedSet.add(currency);
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
        TOTAL_NUMBER_OF_CURRENCIES = CURRENCIES.size();
    }
    
    private static boolean isEuroReplacedCurrency(Currency currency) {
        String key = currency.getCurrencyCode();
        return Arrays.binarySearch(EURO_REPLACED_EXCLUSION_CODES, key) > -1;
    }

    private static boolean isHistoricalCurrency(Currency currency) {
        String displayName = currency.getDisplayName();
        return displayName.contains("\u002818") 
                || displayName.contains("\u002819") 
                || displayName.contains("\u002820") 
                || isEuroReplacedCurrency(currency);
    }
    
    private static boolean isPseudoCurrency(Currency currency) {
        return currency.getDefaultFractionDigits() < 0;
    } 
    
    private static boolean shouldOtherwiseBeExcluded(Currency currency) {
        String key = currency.getCurrencyCode();
        return Arrays.binarySearch(OTHER_EXCLUSION_CODES, key) > -1;
    } 
    
    private static boolean accept(Currency currency) {
        return !isHistoricalCurrency(currency) && !isPseudoCurrency(currency) 
                && !shouldOtherwiseBeExcluded(currency);
    } 
    
    @Test
    public void testGetSuitableCurrencies() {
        System.out.println("getSuitableCurrencies");
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        Set<Currency> expected = currencies.stream()
                .filter(currency -> accept(currency))
                .collect(Collectors.toSet());
        Set<Currency> actual = CurrencyChooser.getSuitableCurrencies();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsSuitableCurrency() {
        System.out.println("isSuitableCurrency");
        Set<Currency> currencies = CurrencyChooser.getSuitableCurrencies();
        for (Currency currency : currencies) {
            String msg = "Currency " + currency.getDisplayName() + " (" 
                    + currency.getCurrencyCode() 
                    + ") should be considered suitable";
            assert CurrencyChooser.isSuitableCurrency(currency) : msg;
        }
    }
    
    @Test
    public void testIsNotSuitableCurrency() {
        Set<Currency> complement = new HashSet<>(CURRENCIES);
        Set<Currency> suitables = CurrencyChooser.getSuitableCurrencies();
        complement.removeAll(suitables);
        for (Currency currency : complement) {
            String msg = "Currency " + currency.getDisplayName() + " (" 
                    + currency.getCurrencyCode() 
                    + ") should not be considered suitable";
            assert !CurrencyChooser.isSuitableCurrency(currency) : msg;
        }
    }
    
    @Test
    public void testChoosePseudocurrency() {
        System.out.println("choosePseudocurrency");
        int initialCapacity = PSEUDO_CURRENCIES.size();
        int numberOfCalls = initialCapacity * 10;
        Set<Currency> actual = new HashSet<>(initialCapacity);
        for (int i = 0; i < numberOfCalls; i++) {
            Currency pseudocurrency = CurrencyChooser.choosePseudocurrency();
            actual.add(pseudocurrency);
        }
        assertEquals(PSEUDO_CURRENCIES, actual);
    }
    
    @Test
    public void testChooseCurrency() {
        System.out.println("chooseCurrency");
        int totalNumberOfCurrencies = CURRENCIES.size();
        int numberOfTries = 5 * totalNumberOfCurrencies / 3;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        while (sampleNumber < numberOfTries) {
            Currency sample = CurrencyChooser.chooseCurrency();
            String msg = "Chosen currency " + sample.getDisplayName() 
                    + " expected to not have negative fraction digits";
            assert sample.getDefaultFractionDigits() > -1 : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int minimum = 11 * totalNumberOfCurrencies / 20;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " times from set of " 
                + totalNumberOfCurrencies + " gave " + actual 
                + " distinct, should've given more than " + minimum 
                + " distinct";
        assertMinimum(minimum, actual, msg);
    }

    @Test
    public void testChooseCurrencyOtherThanDollars() {
        int numberOfTries = 40;
        Set<Currency> samples = new HashSet<>();
        int sampleNumber = 0;
        String dollarsDisplayName = DOLLARS.getDisplayName();
        while (sampleNumber < numberOfTries) {
            Currency sample = CurrencyChooser.chooseCurrencyOtherThan(DOLLARS);
            String msg = "Chosen currency " + sample.getDisplayName() 
                    + " expected to not be " + dollarsDisplayName;
            assert sample != DOLLARS : msg;
            samples.add(sample);
            sampleNumber++;
        }
        int minimum = 11 * numberOfTries / 20;
        int actual = samples.size();
        String msg = "Trying to pick " + numberOfTries + " other than " 
                + dollarsDisplayName + " gave " + actual 
                + " distinct, should've given at least " + minimum 
                + " distinct";
        assertMinimum(minimum, actual, msg);
    }
    
}
