// Zachary Gover
// MDF3 - 1611
// FormViewFragment

package com.gover.zachary.goverzachary_ce01.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gover.zachary.goverzachary_ce01.R;

public class FormViewFragment extends Fragment {

	public static final String TAG = "FormViewFragment.TAG";

	public FormViewFragment(){}

	public static FormViewFragment newInstance() {
		return new FormViewFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the view
		return inflater.inflate(R.layout.fragment_form_view, container, false);
	}

}
