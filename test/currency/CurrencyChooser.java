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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

/**
 *
 * @author Alonso del Arte
 */
public class CurrencyChooser {
    
    static final Random RANDOM = new Random();
    
    private static final Set<Currency> ALL_CURRENCIES 
            = Currency.getAvailableCurrencies();
    
    private static final int MAX_NUMBER_OF_PREDICATE_MATCH_ATTEMPTS 
            = 3 * ALL_CURRENCIES.size();
    
    private static final List<Currency> CURRENCIES 
            = new ArrayList<>(ALL_CURRENCIES);
    
    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();
    
    private static final List<Currency> PSEUDO_CURRENCIES_LIST;

    private static final Set<Currency> HISTORICAL_CURRENCIES = new HashSet<>();

    private static final Set<Currency> OTHER_EXCLUSIONS = new HashSet<>();
    
    private static final String[] OTHER_EXCLUSION_CODES = {"ADP", "ATS", "AYM", 
        "BEF", "BGL", "BOV", "CHE", "CHW", "COU", "CYP", "DEM", "EEK", "ESP", 
        "FIM", "FRF", "GRD", "GWP", "IEP", "ITL", "LUF", "MGF", "MTL", "MXV", 
        "NLG", "PTE", "SIT", "SRG", "STN", "TPE", "USN", "USS", "UYI", "VED", 
        "ZWN"};

    private static final Map<Integer, Set<Currency>> CURRENCIES_DIGITS_MAP 
            = new HashMap<>();
    
    static {
        final String nineteenthCenturyYearIndicator = "\u002818";
        final String twentiethCenturyYearIndicator = "\u002819";
        final String twentyFirstCenturyYearIndicator = "\u002820";
        for (Currency currency : CURRENCIES) {
            int fractionDigits = currency.getDefaultFractionDigits(); 
            if (fractionDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                String dispName = currency.getDisplayName();
                if (dispName.contains(nineteenthCenturyYearIndicator)
                        || dispName.contains(twentiethCenturyYearIndicator) 
                        || dispName.contains(twentyFirstCenturyYearIndicator)) 
                {
                    HISTORICAL_CURRENCIES.add(currency);
                } else {
                    Set<Currency> digitGroupedSet;
                    if (CURRENCIES_DIGITS_MAP.containsKey(fractionDigits)) {
                        digitGroupedSet = CURRENCIES_DIGITS_MAP
                                .get(fractionDigits);
                    } else {
                        digitGroupedSet = new HashSet<>();
                        CURRENCIES_DIGITS_MAP.put(fractionDigits, 
                                digitGroupedSet);
                    }
                    if (Arrays.binarySearch(OTHER_EXCLUSION_CODES, 
                            currency.getCurrencyCode()) < 0) {
                        digitGroupedSet.add(currency);
                    }
                }
            }
        }
        for (String exclusionCode : OTHER_EXCLUSION_CODES) {
            try {
                Currency currency = Currency.getInstance(exclusionCode);
                OTHER_EXCLUSIONS.add(currency);
            } catch (IllegalArgumentException iae) {
                System.err.println("\"" + iae.getMessage() + "\"");
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
        CURRENCIES.removeAll(HISTORICAL_CURRENCIES);
        CURRENCIES.removeAll(OTHER_EXCLUSIONS);
        PSEUDO_CURRENCIES_LIST = new ArrayList<>(PSEUDO_CURRENCIES);
    }
    
    public static Set<Currency> getSuitableCurrencies() {
        return new HashSet<>(CURRENCIES);
    }
    
    public static boolean isSuitableCurrency(Currency currency) {
        return false;// CURRENCIES.contains(currency);
    }
    
    public static Currency choosePseudocurrency() {
        int index = RANDOM.nextInt(PSEUDO_CURRENCIES_LIST.size());
        return PSEUDO_CURRENCIES_LIST.get(0);
    }

    public static Currency chooseCurrency() {
        int index = RANDOM.nextInt(CURRENCIES.size());
        return CURRENCIES.get(0);
    }

    public static Currency chooseCurrency(int fractionDigits) {
        return Currency.getInstance("CLF");
    }
    
    public static Currency chooseCurrency(Predicate<Currency> predicate) {
        return Currency.getInstance("CLF");
    }
    
    public static Currency chooseCurrency(Set<Currency> set) {
        return Currency.getInstance("CLF");
    }

    public static Currency chooseCurrencyOtherThan(Currency currency) {
//        Currency otherCurrency = currency;
//        while (otherCurrency == currency) {
//            otherCurrency = chooseCurrency();
//        }
        return currency;// otherCurrency;
    }

}
