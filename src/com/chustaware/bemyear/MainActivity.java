package com.chustaware.bemyear;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import com.chustaware.R;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

	private static final int LOCATION_AUDIO_RECORDING_FRAGMENT = 0;
	private static final int LOCATION_AUDIO_LISTENING_FRAGMENT = 1;
	private static final int LOCATION_SAMPLES_LIST_FRAGMENT = 2;
	private static final int LOCATION_DEFAULT_FRAGMENT = LOCATION_AUDIO_LISTENING_FRAGMENT;

	private ViewPager viewPager;
	private TabsPagerAdapter pagerAdapter;
	private ActionBar actionBar;

	private ArrayList<String> tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slider);

		tabs = new ArrayList<String>();
		tabs.add("Recording");
		tabs.add("Listening");
		tabs.add("Samples");

		SQLiteManager sqlite = new SQLiteManager(this);
		ArrayList<Double> dobles = new ArrayList<Double>();
		// sqlite.insertSample(new Sample(dobles));

		sqlite.getSamples();

		viewPager = (ViewPager) findViewById(R.id.slider);
		actionBar = getSupportActionBar();
		pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(pagerAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (int i = 0; i < tabs.size(); i++) {
			actionBar.addTab(actionBar.newTab().setText(tabs.get(i)).setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (viewPager.getCurrentItem() == LOCATION_DEFAULT_FRAGMENT) {
			// If the user is currently looking at the default fragment, allow the system to handle the Back button.
			// This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the default fragment.
			viewPager.setCurrentItem(LOCATION_DEFAULT_FRAGMENT);
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}
