package com.chustaware.bemyear;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.chustaware.bemyear.audio.AudioDataManager;

public class AudioRecordingIntentService extends IntentService {

	public static final String ACTION_PROGRESS = AudioRecordingIntentService.class.getName() + "ACTION_PROGRESS";
	public static final String ACTION_FINISH = AudioRecordingIntentService.class.getName() + "ACTION_FINISH";
	public static final int MSG_STOP = 0;

	private boolean recording;
	private Messenger messenger;
	private AudioDataManager audioDataManager;
	private final IBinder binder = new LocalBinder();

	public AudioRecordingIntentService() {
		super(AudioRecordingIntentService.class.getSimpleName());
		recording = false;
		audioDataManager = new AudioDataManager();
		messenger = new Messenger(new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_STOP:
					recording = false;
					break;

				default:
					break;
				}

				super.handleMessage(msg);
			}
		});
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// android.os.Debug.waitForDebugger();

		String action = intent.getStringExtra("action");

		if (action.equals("record")) {
			startRecording();
		}

		audioDataManager.stopCapturingData();
	}

	private void startRecording() {
		ArrayList<Double> pitchs = new ArrayList<Double>();
		recording = true;
		audioDataManager.startCapturingData();
		double temp = 0.0;
		while (recording) {
			temp = audioDataManager.computePitchAudioData();
			pitchs.add(temp);
			Log.v("fft", "Pitch: " + temp);
		}
		String out = "";
		for (int i = 0; i < pitchs.size(); i++) {
			out += pitchs.get(i).toString() + ", ";
		}
		Log.v("pitch", out);

		// TODO: Write into data base

	}

	public Messenger getMessenger() {
		return messenger;
	}

	public class LocalBinder extends Binder {

		public AudioRecordingIntentService getService() {
			return AudioRecordingIntentService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
}
