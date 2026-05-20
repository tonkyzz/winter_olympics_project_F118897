package org.informatics;

import org.informatics.data.Stock;

import java.math.BigDecimal;

import static org.informatics.data.StockType.FOOD;

public class Main {
    public static void main(String[] args) {

        Stock s1 = new Stock(222,"Apple", BigDecimal.valueOf(5),FOOD,"10.03.2003", 10, BigDecimal.valueOf(6));

    }
}