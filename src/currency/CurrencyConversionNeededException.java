/*
 * Copyright (C) 2021 Alonso del Arte
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
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package currency;

/**
 *
 * @author Alonso del Arte
 */
public class CurrencyConversionNeededException extends RuntimeException {
    
    private static final long serialVersionUID = 4549294484512324403L;
    
    private final CurrencyAmount amountA, amountB;
    
    public CurrencyAmount getAmountA() {
        return this.amountA;
    }
    
    public CurrencyAmount getAmountB() {
        return this.amountB;
    }
    
    public CurrencyConversionNeededException(String msg, 
            CurrencyAmount amtA, CurrencyAmount amtB) {
        super(msg);
        this.amountA = amtA;
        this.amountB = amtB;
    }
    
}
