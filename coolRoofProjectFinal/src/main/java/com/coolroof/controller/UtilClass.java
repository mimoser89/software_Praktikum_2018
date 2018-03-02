package com.coolroof.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class UtilClass {

	static class Co2Calc {
		
		private static final double CO2FACTOR = 0.01;
		private static final double PAYBACKFACTOR = 0.1;
		
		public static double calcCo2(long start, int space) {
			long timeDiff = new Date().getTime() - start;
			timeDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
			return timeDiff * space * CO2FACTOR;
		}
		
		public static double calcRtn(double coSaved) {
			return coSaved * PAYBACKFACTOR;
		}
	}

}