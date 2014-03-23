package com.chustaware.bemyear;

import android.content.ComponentName;
import android.content.Context;
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

public class AudioListeningFragment extends Fragment {

	private boolean mBound;
	private ServiceConnection serviceConnection;
	private AudioRecordingIntentService mService;

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

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_audio_listening, container, false);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		setButtonClickListeners();
	}

	private void startListening() {
		Log.d(getClass().getSimpleName(), "Start recording");
		Intent msgIntent = new Intent(getActivity(), AudioRecordingIntentService.class);
		msgIntent.putExtra("action", "listen");
		getActivity().startService(msgIntent);

		if (!mBound) {
			getActivity().bindService(msgIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		}
	}

	private void stopListening() {
		Log.d(getClass().getSimpleName(), "Stop recording");
		Messenger serviceMessenger = mService.getMessenger();
		Message msg = Message.obtain(null, AudioRecordingIntentService.MSG_STOP);
		try {
			serviceMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
					startListening();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Drawable imgRecordButtonNormal = getResources().getDrawable(R.drawable.record_button_normal);
					v.setBackgroundDrawable(imgRecordButtonNormal);
					stopListening();
				}
				return false;
			}
		});
	}

}
