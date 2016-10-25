// Zachary Gover
// MDF3 - 1611
// MainActivity

package com.gover.zachary.goverzachary_ce01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import com.gover.zachary.goverzachary_ce01.fragments.FormViewFragment;
import com.gover.zachary.goverzachary_ce01.fragments.ListViewFragment;
import com.gover.zachary.goverzachary_ce01.models.Person;
import com.gover.zachary.goverzachary_ce01.models.Storage;

public class MainActivity extends AppCompatActivity implements ListViewFragment.ClickListener {

	public static final String UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";
	private static final int FORM_RESULT = 1;
	private static final int DETAIL_RESULT = 2;

	private Storage storage;
	private ArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			setupListView();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		updateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Setup Action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_list, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Determine selection
		switch(item.getItemId()) {
			case R.id.action_add:
				openFormView();
				break;
			default:
				break;
		}

		return true;
	}

	private void setupListView() {
		if (adapter == null) {
			adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
		}

		if (storage == null) {
			storage = Storage.newInstance(this);
		}

		// Setup the list view
		ListViewFragment list = ListViewFragment.newInstance();
		list.setListAdapter(adapter);

		getFragmentManager().beginTransaction()
			.replace(R.id.container, list, FormViewFragment.TAG).commit();

		updateList();
	}

	private void openFormView() {
		// Open the form view
		Intent formActivity = new Intent(this, FormActivity.class);
		startActivityForResult(formActivity, FORM_RESULT);
	}

	public void updateList() {
		storage.readPeople();

		adapter.clear();
		adapter.addAll(storage.getStringResults());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void listItemClick(int position) {
		// Open detail activity by triggering an implicit intent
		Person person = storage.getPeople().get(position);

		if (person != null) {
			Intent detailActivity = new Intent(DetailActivity.VIEW_DATA);

			// Pass person details
			detailActivity.putExtra(Person.FNAME_KEY, person.getFname());
			detailActivity.putExtra(Person.LNAME_KEY, person.getLname());
			detailActivity.putExtra(Person.AGE_KEY, person.getAge());

			startActivityForResult(detailActivity, DETAIL_RESULT);
		}
	}
}
