// Zachary Gover
// MDF3 - 1611
// ListViewFragment

package com.gover.zachary.goverzachary_ce01.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.gover.zachary.goverzachary_ce01.R;

public class ListViewFragment extends ListFragment {

	private static final String TAG = "ListViewFragment.TAG";

	private ClickListener listener;

	public ListViewFragment(){}

	public static ListViewFragment newInstance() {
		return new ListViewFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the view
		return inflater.inflate(R.layout.fragment_list_view, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Set listener to MainActivity
		if (activity instanceof ClickListener) {
			this.listener = (ClickListener) activity;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (listener != null) {
			listener.listItemClick(position);
		}
	}

	public interface ClickListener {
		void listItemClick(int position);
	}

}
