package com.chustaware.bemyear;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.chustaware.R;

public class AudioRecordingFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
	}

	private void stopRecording() {
		Log.d(getClass().getSimpleName(), "Stop recording");
	}

}
