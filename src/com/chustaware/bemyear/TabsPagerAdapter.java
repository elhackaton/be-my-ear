package com.chustaware.bemyear;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter{

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return new AudioRecordingFragment();
        case 1:
            return new AudioListeningFragment();
        case 2:
            return new SamplesListFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        return 3;
    }

}
