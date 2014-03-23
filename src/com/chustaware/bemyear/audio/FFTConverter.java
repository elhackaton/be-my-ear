package com.chustaware.bemyear.audio;

public class FFTConverter {

	public static void fowardConvert(byte[] samples, double[] freq, double[] window) {
		double[][] samplesBuffer = new double[freq.length][2];

		for (int i = 0; i < freq.length / 2 - 1; i++) {
			// Real unit
			samplesBuffer[i][0] = (double) (samples[2 * i + 1] * 256 + samples[2 * i]) * window[i];
			// Imaginary unit
			samplesBuffer[i][1] = 0.0;
		}

		// Direct FFT transform
		FFT.direct(samplesBuffer);

		// Spectral module
		for (int i = 0; i < freq.length; i++) {
			freq[i] = Math.sqrt(samplesBuffer[i][0] * samplesBuffer[i][0] + samplesBuffer[i][1] * samplesBuffer[i][1]);
		}

	}
}
