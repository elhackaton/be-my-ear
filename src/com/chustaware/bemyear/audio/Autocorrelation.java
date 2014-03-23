package com.chustaware.bemyear.audio;

public class Autocorrelation {

	public static void autocorrelate(double[] rawSignal, double[] correlatedSignal) {
		float[] R = new float[rawSignal.length];
		float sum;

		for (int i = 0; i < rawSignal.length; i++) {
			sum = 0;
			for (int j = 0; j < rawSignal.length - i; j++) {
				sum += rawSignal[j] * rawSignal[j + i];
			}
			R[i] = sum;
		}
	}

}
