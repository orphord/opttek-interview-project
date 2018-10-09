package com.opttek.orford.logistics.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwapAndCompareCallable implements Callable<Integer> {
	private static final Logger log = LoggerFactory.getLogger(SwapAndCompareCallable.class);

	private Integer a;
	private Integer b;
	
	public SwapAndCompareCallable(Integer _a, Integer _b) {
		log.info("Constructing SwapAndCompareCallable with a: " + _a + " and b: " + _b);
		a = _a;
		b = _b;
	}

	public Integer call() throws Exception {
		return Integer.valueOf(a.intValue() + b.intValue());

	}

}
