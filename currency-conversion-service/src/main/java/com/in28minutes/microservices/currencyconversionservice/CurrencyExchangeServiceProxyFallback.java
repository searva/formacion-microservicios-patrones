package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;

public class CurrencyExchangeServiceProxyFallback implements CurrencyExchangeServiceProxy {

	@Override
	public CurrencyConversionBean retrieveExchangeValue(String from, String to) {
		return new CurrencyConversionBean(0L, "EUR", "USD", new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), 0);
	}

}
