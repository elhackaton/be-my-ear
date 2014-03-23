package com.chustaware.bemyear.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioRecordingManager {

	public static final int RECORDER_SAMPLERATE = 8000;
	public static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	public static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	public static final int BYTES_PER_SAMPLE = 2; // 2 bytes in 16bit format
	public static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS,
			RECORDER_AUDIO_ENCODING);

	private AudioRecord recorder = null;
	private boolean isRecording = false;

	public void startRecording() {
		recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS,
				RECORDER_AUDIO_ENCODING, BUFFER_SIZE * BYTES_PER_SAMPLE);

		recorder.startRecording();
		isRecording = true;

	}

	private static byte[] shortArrayToByteArray(short[] sData) {
		int shortArrsize = sData.length;
		byte[] bytes = new byte[shortArrsize * 2];
		for (int i = 0; i < shortArrsize; i++) {
			bytes[i * 2] = (byte) (sData[i] & 0x00FF);
			bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
			sData[i] = 0;
		}
		return bytes;
	}

	public void stopRecording() {
		// stops the recording activity
		if (null != recorder) {
			isRecording = false;
			recorder.stop();
			recorder.release();
			recorder = null;
		}
	}

	public boolean read(byte[] audioData) {
		if (isRecording) {
			recorder.read(audioData, 0, audioData.length);
			return true;
		}
		return false;
	}

}
