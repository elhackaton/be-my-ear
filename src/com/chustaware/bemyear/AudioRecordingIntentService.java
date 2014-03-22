package com.chustaware.bemyear;

import android.app.IntentService;
import android.content.Intent;

public class AudioRecordingIntentService extends IntentService {

	public static final String ACTION_PROGRESS = AudioRecordingIntentService.class.getName() + "ACTION_PROGRESS";
	public static final String ACTION_FINISH = AudioRecordingIntentService.class.getName() + "ACTION_FINISH";

	public AudioRecordingIntentService() {
		super(AudioRecordingIntentService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int iter = intent.getIntExtra("iteraciones", 0);

		for (int i = 1; i <= iter; i++) {
			longRunningTask();

			// Comunicamos el progreso
			Intent bcIntent = new Intent();
			bcIntent.setAction(ACTION_PROGRESS);
			bcIntent.putExtra("progress", i * 10);
			sendBroadcast(bcIntent);
		}

		Intent bcIntent = new Intent();
		bcIntent.setAction(ACTION_FINISH);
		sendBroadcast(bcIntent);
	}

	private void longRunningTask() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}
