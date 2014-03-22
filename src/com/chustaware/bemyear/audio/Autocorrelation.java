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

	/*public void fftAutoCorrelation(double[] x, double[] ac) {
		int n = x.length;
		// Assumes n is even.

		FFT.direct(x);
		ac[0] = sqr(x[0]);
		// ac[0] = 0; // For statistical convention, zero out the mean
		ac[1] = sqr(x[1]);
		for (int i = 2; i < n; i += 2) {
			ac[i] = sqr(x[i]) + sqr(x[i + 1]);
			ac[i + 1] = 0;
		}
		DoubleFFT_1D ifft = new DoubleFFT_1D(n);
		ifft.realInverse(ac, true);
		// For statistical convention, normalize by dividing through with variance
		// for (int i = 1; i < n; i++)
		// ac[i] /= ac[0];
		// ac[0] = 1;
	}

	void test() {
		double[] data = { 1, -81, 2, -15, 8, 2, -9, 0 };
		double[] ac1 = new double[data.length];
		double[] ac2 = new double[data.length];
		bruteForceAutoCorrelation(data, ac1);
		fftAutoCorrelation(data, ac2);
		print("bf", ac1);
		print("fft", ac2);
		double err = 0;
		for (int i = 0; i < ac1.length; i++)
			err += sqr(ac1[i] - ac2[i]);
		System.out.println("err = " + err);
	}*/

}
