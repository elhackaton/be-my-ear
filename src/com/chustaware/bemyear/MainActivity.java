package com.chustaware.bemyear;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.chustaware.R;

public class MainActivity extends FragmentActivity {

	private static final int LOCATION_AUDIO_RECORDING_FRAGMENT = 0;
	private static final int LOCATION_AUDIO_LISTENING_FRAGMENT = 1;
	private static final int LOCATION_SAMPLES_LIST_FRAGMENT = 2;
	private static final int LOCATION_DEFAULT_FRAGMENT = LOCATION_AUDIO_LISTENING_FRAGMENT;

	private ViewPager pager;
	private PagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slider);

		List<Fragment> fragments = createFragments();
		pager = (ViewPager) findViewById(R.id.slider);
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragments);
		pager.setAdapter(pagerAdapter);
		pager.setCurrentItem(LOCATION_DEFAULT_FRAGMENT);
	}

	@Override
	public void onBackPressed() {
		if (pager.getCurrentItem() == LOCATION_DEFAULT_FRAGMENT) {
			// If the user is currently looking at the default fragment, allow the system to handle the Back button.
			// This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the default fragment.
			pager.setCurrentItem(LOCATION_DEFAULT_FRAGMENT);
		}
	}

	private List<Fragment> createFragments() {
		List<Fragment> fragments = new Vector<Fragment>();

		Fragment audioRecordingFragment = Fragment.instantiate(this, AudioRecordingFragment.class.getName());
		fragments.add(LOCATION_AUDIO_RECORDING_FRAGMENT, audioRecordingFragment);

		Fragment audioListeningFragment = Fragment.instantiate(this, AudioListeningFragment.class.getName());
		fragments.add(LOCATION_AUDIO_LISTENING_FRAGMENT, audioListeningFragment);

		Fragment samplesListFragment = Fragment.instantiate(this, SamplesListFragment.class.getName());
		fragments.add(LOCATION_SAMPLES_LIST_FRAGMENT, samplesListFragment);

		return fragments;
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		private List<Fragment> fragments;

		public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}
}
