package com.chustaware.bemyear.audio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;

public class AudioRecordingHandler {

	private static final int RECORDER_SAMPLERATE = 8000;
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private static final int BYTES_PER_SAMPLE = 2; // 2 bytes in 16bit format
	private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS,
			RECORDER_AUDIO_ENCODING);

	private AudioRecord recorder = null;
	private Thread recordingThread = null;
	private boolean isRecording = false;

	private void startRecording() {
		recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS,
				RECORDER_AUDIO_ENCODING, BUFFER_SIZE * BYTES_PER_SAMPLE);

		recorder.startRecording();
		isRecording = true;
		recordingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				writeAudioDataToFile();
			}
		}, "AudioRecorder Thread");
		recordingThread.start();
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

	private void writeAudioDataToFile() {
		String filePath = Environment.getExternalStorageDirectory().getPath() + "/voice8K16bitmono.pcm";
		short sData[] = new short[BUFFER_SIZE];

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (isRecording) {
			recorder.read(sData, 0, BUFFER_SIZE);
			try {
				// // writes the data to file from buffer
				// // stores the voice buffer
				byte bData[] = shortArrayToByteArray(sData);
				os.write(bData, 0, BUFFER_SIZE * BYTES_PER_SAMPLE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() {
		// stops the recording activity
		if (null != recorder) {
			isRecording = false;
			recorder.stop();
			recorder.release();
			recorder = null;
			recordingThread = null;
		}
	}

}