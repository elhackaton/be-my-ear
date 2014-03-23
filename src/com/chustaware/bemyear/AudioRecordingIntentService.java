package com.chustaware.bemyear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

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
	private static final int THREASHOLD = 1;

	private boolean recording;
	private Messenger messenger;
	private AudioDataManager audioDataManager;
	private final IBinder binder = new LocalBinder();

	private SQLiteManager sqLiteManager;

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

		sqLiteManager = new SQLiteManager(this);
		String action = intent.getStringExtra("action");

		if (action.equals("record")) {
			startRecording();
		}
		if (action.equals("listen")) {
			startListening();
		}

		audioDataManager.stopCapturingData();
	}

	private void startListening() {
		ArrayList<Double> pitchs = new ArrayList<Double>();
		ArrayList<Double> reference = new ArrayList<Double>();
		recording = true;
		audioDataManager.startCapturingData();
		double temp = 0.0;
		while (recording) {
			for (int i = 0; i < 10; i++) {
				temp = audioDataManager.computePitchAudioData();
				if (temp >= 0) {
					pitchs.add(temp);
				}
				Log.v("fft", "Pitch: " + temp);
			}

			readFromDatabase(reference);

			if (matchSignalWithReference(reference, pitchs)) {
				Log.v("match", "IT WORKSS!!");
			}
		}

	}

	private void readFromDatabase(ArrayList<Double> reference) {
		Sample s = sqLiteManager.getSamples().get(0);
		reference.clear();
		for (int i = 0; i < s.getPattern().size(); i++) {
			reference.add(s.getPattern().get(i));
		}
		// TODO: Get all samples
	}

	private void startRecording() {
		sqLiteManager.deleteSampleTable();
		ArrayList<Double> pitchs = new ArrayList<Double>();
		recording = true;
		audioDataManager.startCapturingData();
		double temp = 0.0;
		while (recording) {
			temp = audioDataManager.computePitchAudioData();
			if (temp >= 0) {
				pitchs.add(temp);
			}
			Log.v("fft", "Pitch: " + temp);
		}
		String out = "";
		for (int i = 0; i < pitchs.size(); i++) {
			out += pitchs.get(i).toString() + ", ";
		}
		Log.v("pitch", out);

		storeIntoDatabase(pitchs);

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

	public boolean matchSignalWithReference(ArrayList<Double> reference, ArrayList<Double> signal) {
		HashMap<Double, Integer> histogramReference = new HashMap<Double, Integer>();
		HashMap<Double, Integer> histogramSignal = new HashMap<Double, Integer>();
		HashMap<Integer, ArrayList<Double>> modeReference = new HashMap<Integer, ArrayList<Double>>();
		HashMap<Integer, ArrayList<Double>> modeSignal = new HashMap<Integer, ArrayList<Double>>();

		ArrayList<Integer> indexReference = new ArrayList<Integer>();
		ArrayList<Integer> indexSignal = new ArrayList<Integer>();

		for (int i = 0; i < reference.size(); i++) {
			if (histogramReference.get(reference.get(i)) == null) {
				if (reference.get(i) > 0) {
					histogramReference.put(reference.get(i), 1);
				}
				continue;
			}
			histogramReference.put(reference.get(i), histogramReference.get(reference.get(i)) + 1);
		}

		for (int i = 0; i < signal.size(); i++) {
			if (histogramSignal.get(signal.get(i)) == null) {
				if (signal.get(i) > 0) {
					histogramSignal.put(signal.get(i), 1);
				}
				continue;
			}
			histogramSignal.put(signal.get(i), histogramSignal.get(signal.get(i)) + 1);
		}

		ArrayList<Double> freqs = null;

		for (Entry<Double, Integer> entry : histogramReference.entrySet()) {
			if ((freqs = modeReference.get(entry.getValue())) == null) {
				indexReference.add(entry.getValue());
				freqs = new ArrayList<Double>();
				modeReference.put(entry.getValue(), freqs);
			}
			freqs.add(entry.getKey());
		}

		for (Entry<Double, Integer> entry : histogramSignal.entrySet()) {
			if ((freqs = modeSignal.get(entry.getValue())) == null) {
				indexSignal.add(entry.getValue());
				freqs = new ArrayList<Double>();
				modeSignal.put(entry.getValue(), freqs);
			}
			freqs.add(entry.getKey());
		}

		Collections.sort(indexReference);
		Collections.sort(indexSignal);

		for (int i = 0; i < indexReference.size() && i < indexSignal.size() && i < THREASHOLD; i++) {
			if (modeReference.get(indexReference.get(i)) != modeSignal.get(indexSignal.get(i))) {
				return false;
			}
		}

		return true;
	}

	public void storeIntoDatabase(ArrayList<Double> samples) {
		SQLiteManager sqLiteManager = new SQLiteManager(this);
		sqLiteManager.insertSample(new Sample(samples));
	}
}
