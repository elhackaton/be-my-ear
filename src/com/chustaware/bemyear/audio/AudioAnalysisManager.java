package com.chustaware.bemyear.audio;

public class AudioAnalysisManager {

	/**
	 * Finds the max value for the specified freq array
	 * 
	 * @param freq
	 * @param threashold
	 * 
	 * @return pitch
	 */
	public static double detectPitchFrequencyDomain(double[] freq, double threashold) {
		int max = 0;

		for (int i = 0; i < freq.length; i++) {
			if (freq[i] > threashold) {
				if (freq[i] > freq[max]) {
					max = i;
				}
			}
		}

		return max;
	}

	public static double detectPitchZeroCrossing(double[] time) {
		int total = 0;
		int numCross = 0;
		int lastCross = 0;

		for (int i = 0; i < time.length; i++) {
			if ((time[i] < 0 && time[i + 1] > 0) || (time[i] > 0 && time[i + 1] < 0)) {
				numCross++;
				total = (i - lastCross);
				lastCross = i;
				continue;
			}
		}

		return total / (double) numCross;
	}
}
