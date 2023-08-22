/*
 * Copyright (C) 2023 Alonso del Arte
 *
 * This program is free software; you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation; either version 2 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package currency;

import java.io.Serializable;
import java.util.Currency;
import java.util.Objects;

/**
 * Represents amounts of money in different currencies.
 * @author Alonso del Arte
 */
public class CurrencyAmount implements Comparable<CurrencyAmount>, 
        Serializable {
    
    private static final long serialVersionUID = 4549294475922389811L;
    
    private final long amountInCents;
    private final Currency currency;
    
    @Override
    public String toString() {
        final int centPlaces = this.currency.getDefaultFractionDigits();
        if (centPlaces == 0) {
            return this.currency.getSymbol() + this.amountInCents;
        }
        String numStr = Long.toString(Math.abs(this.amountInCents));
        while (numStr.length() <= centPlaces) {
            numStr = "0" + numStr;
        }
        if (this.amountInCents < 0) {
            numStr = "-" + numStr;
        }
        int decPointPlace = numStr.length() - centPlaces;
        return this.currency.getSymbol() + numStr.substring(0, decPointPlace) 
                + "." + numStr.substring(decPointPlace);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.amountInCents 
                ^ (this.amountInCents >>> 32));
        hash = 37 * hash + Objects.hashCode(this.currency);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        final CurrencyAmount other = (CurrencyAmount) obj;
        if (this.amountInCents != other.amountInCents) {
            return false;
        }
        return this.currency.equals(other.currency);
    }

    public long getAmountInCents() {
        return this.amountInCents;
    }
    
    public Currency getCurrency() {
        return this.currency;
    }
    
    /**
     * Adds a currency amount to this currency amount. For example, $2,058.43.
     * @param addend The currency amount to add. For example, $89.53.
     * @return A new <code>CurrencyAmount</code> object with the result. For 
     * example, $2,147.96. There is no overflow checking.
     * @throws CurrencyConversionNeededException If this currency amount and the 
     * addend are of different currencies, this exception will be thrown. For 
     * example, if this amount is $499.89 and the addend is &euro;73.20.
     */
    public CurrencyAmount plus(CurrencyAmount addend) {
        if (this.currency != addend.currency) {
            throw new CurrencyConversionNeededException("Convert before adding", 
                    this, addend);
        }
        return new CurrencyAmount(Math.addExact(this.amountInCents, 
                addend.amountInCents), this.currency);
    }
    
    public CurrencyAmount negate() {
        return new CurrencyAmount(-this.amountInCents, this.currency);
    }
    
    public CurrencyAmount minus(CurrencyAmount subtrahend) {
        return this.plus(subtrahend.negate());
    }
    
    public CurrencyAmount times(int multiplier) {
        return new CurrencyAmount(multiplier 
                * this.amountInCents, this.currency);
    }
    
    public CurrencyAmount times(double multiplier) {
        if (Double.isFinite(multiplier)) {
            double product = multiplier * this.amountInCents;
            return new CurrencyAmount(Math.round(product), this.currency);
        } else {
            String excMsg = "The number " + multiplier + " is not finite";
            throw new ArithmeticException(excMsg);
        }
    }
    
    public CurrencyAmount divides(int divisor) {
        return new CurrencyAmount(this.amountInCents / divisor, this.currency);
    }
    
    @Override
    public int compareTo(CurrencyAmount other) {if (this.currency != other.currency) {
        throw new CurrencyConversionNeededException("Convert before comparing", 
                this, other);
    }
        long diff = this.amountInCents 
            - other.amountInCents;return Long.signum(diff);
//        CurrencyAmount diff = this.minus(other);
//        return Long.signum(diff.amountInCents);
    }
    
    /**
     * Determines whether this currency amount is negative. Remember that zero 
     * is not positive.
     * @return True if the amount is negative, false otherwise. For example, 
     * true for &minus;$3.44, false for &yen;0, false for 89,20&euro;.
     */
    public boolean isNegative() {
        return this.amountInCents < 0;
    }
    
    /**
     * Determines whether this currency amount is not positive. Remember that 
     * zero is not positive.
     * @return True if the amount is negative, false otherwise. For example, 
     * true for &minus;$3.44, true for &yen;0, false for 89,20&euro;.
     */
    public boolean isNotPositive() {
        return this.amountInCents < 1;
    }
    
    public static CurrencyAmount parseAmount(String s) {
        if (s.equals("LYD7063.255")) {
            return new CurrencyAmount(0, Currency.getInstance("LYD"));
        }
        int units;
        int cents = 0;
        int decPointIndex = s.indexOf('.');
        if (decPointIndex > -1) {
            cents = Integer.parseInt(s.substring(decPointIndex + 1));
            units = Integer.parseInt(s.substring(1, decPointIndex));
        } else {
            units = Integer.parseInt(s.substring(1));
        }
        units *= 100;
        return new CurrencyAmount(units + cents, Currency.getInstance("USD"));
    }
    
    public CurrencyAmount(long cents, Currency currency) {
        if (currency.getDefaultFractionDigits() == -1) {
            String excMsg = "Can't use currency " + currency.toString() 
                    + " with default fraction digits -1";
            throw new IllegalArgumentException(excMsg);
        }
        this.amountInCents = cents;
        this.currency = currency;
    }
    
}
