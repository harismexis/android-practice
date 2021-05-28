package com.harismexis.breakingbad.wlivedata.androiddevs

import java.math.BigDecimal

class StockManager(private var symbol: String) {

    private var price: BigDecimal = BigDecimal.valueOf(0.0)

    fun requestPriceUpdates(lam : (value: BigDecimal) -> Unit) {
        lam.invoke(price)
    }

    fun removeUpdates() {

    }
}