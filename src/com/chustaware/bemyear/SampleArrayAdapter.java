package com.chustaware.bemyear;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chustaware.R;

public class SampleArrayAdapter extends ArrayAdapter<Sample> {

	private LayoutInflater inflater;
	private SQLiteManager sqLiteManager;
	private Context context;

	public SampleArrayAdapter(Context context, List<Sample> samplesList) {
		super(context, R.layout.row_main_menu, R.id.samplesList, samplesList);

		this.context = context;
		inflater = LayoutInflater.from(context);
		sqLiteManager = new SQLiteManager(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Sample sample = (Sample) this.getItem(position);
		SampleViewHolder sampleHolder = null;

		if (convertView == null) {
			// La view se crea a partir del xml que contiene la estructura de las filas de nuestra ListView
			convertView = inflater.inflate(R.layout.row_main_menu, null);

			// Guardamos las views hijas de convertView en el objeto AlarmaViewHolder
			sampleHolder = new SampleViewHolder();
			sampleHolder.setTvName((TextView) convertView.findViewById(R.id.tvName));
			sampleHolder.setCbEnabled((CheckBox) convertView.findViewById(R.id.cbEnabled));
			
			// Para optimizar asociamos a la convertView su holder correspondiente
			convertView.setTag(sampleHolder);

		}
		
		sampleHolder.getCbEnabled().setTag(sample);
		
		sampleHolder.getCbEnabled().setChecked(sample.isEnabled());
		sampleHolder.getTvName().setText(sample.getName());
		
		return convertView;

	}

}
