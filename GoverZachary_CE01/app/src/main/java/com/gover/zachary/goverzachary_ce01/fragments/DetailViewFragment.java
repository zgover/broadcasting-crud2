// Zachary Gover
// MDF3 - 1611
// DetailViewFragment

package com.gover.zachary.goverzachary_ce01.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.gover.zachary.goverzachary_ce01.R;
import com.gover.zachary.goverzachary_ce01.models.Person;

public class DetailViewFragment extends Fragment {

	public static final String TAG = "DetailViewFragment.TAG";

	public DetailViewFragment(){}

	public static DetailViewFragment newInstance() {
		return new DetailViewFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the view
		View view = inflater.inflate(R.layout.fragment_detail_view, container, false);

		// Get values and setup view
		Bundle args = getArguments();

		if (args.containsKey(Person.KEY)) {
			Object obj = args.getSerializable(Person.KEY);
			if (obj instanceof Person) {
				Person person = (Person) obj;

				// Set the the TextView values
				((TextView) view.findViewById(R.id.fNameLbl)).setText(person.getFname());
				((TextView) view.findViewById(R.id.lNameLbl)).setText(person.getLname());
				((TextView) view.findViewById(R.id.ageLbl)).setText(Integer.toString(person.getAge()));
			}
		} else {
			Toast.makeText(getActivity(), "There has been an error processing your request.", Toast.LENGTH_LONG).show();
		}

		return view;
	}

}
