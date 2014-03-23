package com.chustaware.bemyear;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chustaware.R;


public class SamplesListFragment extends Fragment {
	
	private ArrayList<Sample> samplesList;
	private SQLiteManager sqLiteManager;
	private ArrayAdapter<Sample> sampleAdapter;
	private ListView mainListView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.samples_list, container, false);
        
        sqLiteManager = new SQLiteManager(getActivity());
        mainListView = (ListView) rootView.findViewById(R.id.samplesList);
        return rootView;
    }
	
	@Override
	public void onResume() {
		super.onResume();
		
		samplesList = sqLiteManager.getSamples();
		if (samplesList != null) {
			sampleAdapter = new SampleArrayAdapter(getActivity(), samplesList);
			mainListView.setAdapter(sampleAdapter);
			
		}

	}

}
