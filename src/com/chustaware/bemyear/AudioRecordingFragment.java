package com.chustaware.bemyear;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.chustaware.R;
import com.chustaware.bemyear.AudioRecordingIntentService.LocalBinder;

public class AudioRecordingFragment extends Fragment {

	private AudioRecordingIntentService mService;
	private boolean mBound;
	private ServiceConnection serviceConnection;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		serviceConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				mBound = false;
				mService = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				LocalBinder binder = (LocalBinder) service;
				mService = binder.getService();
				mBound = true;
			}
		};

		this.mBound = false;
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_audio_recording, container, false);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		setButtonClickListeners();
	}

	private void setButtonClickListeners() {
		Button btnRecord = (Button) getActivity().findViewById(R.id.btnRecord);
		btnRecord.setOnTouchListener(new OnTouchListener() {

			@SuppressWarnings("deprecation")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Drawable imgRecordButtonPressed = getResources().getDrawable(R.drawable.record_button_pressed);
					v.setBackgroundDrawable(imgRecordButtonPressed);
					startRecording();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Drawable imgRecordButtonNormal = getResources().getDrawable(R.drawable.record_button_normal);
					v.setBackgroundDrawable(imgRecordButtonNormal);
					stopRecording();
				}
				return false;
			}
		});
	}

	private void startRecording() {
		Log.d(getClass().getSimpleName(), "Start recording");
		Intent msgIntent = new Intent(getActivity(), AudioRecordingIntentService.class);
		msgIntent.putExtra("action", "record");
		getActivity().startService(msgIntent);

		if (!mBound) {
			mBound = true;
			getActivity().bindService(msgIntent, serviceConnection, getActivity().BIND_AUTO_CREATE);
		}
	}

	private void stopRecording() {
		Log.d(getClass().getSimpleName(), "Stop recording");
		Messenger serviceMessenger = mService.getMessenger();
		Message msg = Message.obtain(null, AudioRecordingIntentService.MSG_STOP);
		try {
			serviceMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
