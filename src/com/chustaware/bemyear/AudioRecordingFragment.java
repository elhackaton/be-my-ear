package com.chustaware.bemyear;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
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
		enableButtons(false);
	}

	private void setButtonClickListeners() {
		View.OnClickListener btnClick = new ButtonClickListener();

		Button btnStart = (Button) getActivity().findViewById(R.id.btnStart);
		btnStart.setOnClickListener(btnClick);

		Button btnStop = (Button) getActivity().findViewById(R.id.btnStop);
		btnStop.setOnClickListener(btnClick);
	}

	private void setEnabledButton(int id, boolean enabled) {
		Button btn = (Button) getActivity().findViewById(id);
		btn.setEnabled(enabled);
	}

	private void enableButtons(boolean isRecording) {
		setEnabledButton(R.id.btnStart, !isRecording);
		setEnabledButton(R.id.btnStop, isRecording);
	}

	private void startRecording() {

	}

	private void stopRecording() {

	}

	private class ButtonClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStart: {
				enableButtons(true);
				startRecording();
				break;
			}
			case R.id.btnStop: {
				enableButtons(false);
				stopRecording();
				break;
			}
			default:
				break;
			}
		}
	}

}
