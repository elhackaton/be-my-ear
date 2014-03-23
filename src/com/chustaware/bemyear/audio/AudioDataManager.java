package com.chustaware.bemyear.audio;

public class AudioDataManager {

	private AudioRecordingManager audioRecordingManager;
	private byte[] audioSamples;
	private double[] freq;
	private double[] window;
	private static int SAMPLES_BUFFER = 1024;

	public AudioDataManager() {
		audioRecordingManager = new AudioRecordingManager();
		audioSamples = new byte[SAMPLES_BUFFER];
		freq = new double[SAMPLES_BUFFER];
		window = new double[SAMPLES_BUFFER];
		Window.computeCoefficients(1, window);
	}

	public void processAudioData() {
		audioRecordingManager.read(audioSamples);
		FFTConverter.fowardConvert(audioSamples, freq, window);
		AudioAnalysisManager.detectPitchFrequencyDomain(freq, 10.0);
	}
}
