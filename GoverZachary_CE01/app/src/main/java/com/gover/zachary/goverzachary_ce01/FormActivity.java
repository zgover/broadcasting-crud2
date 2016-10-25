// Zachary Gover
// MDF3 - 1611
// FormActivity

package com.gover.zachary.goverzachary_ce01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.gover.zachary.goverzachary_ce01.fragments.FormViewFragment;
import com.gover.zachary.goverzachary_ce01.models.Person;

public class FormActivity extends AppCompatActivity {

	public static final String SAVE_DATA = "com.fullsail.android.ACTION_SAVE_DATA";

	private BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupFormView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Setup Action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_form, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Determine selection
		switch(item.getItemId()) {
			case R.id.action_save:
				savePerson();
				break;
			default:
				break;
		}

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				closeActivity();
			}
		};

		// Register receiver
		IntentFilter filter = new IntentFilter();

		filter.addAction(MainActivity.UPDATE_LIST);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	private void setupFormView() {
		FormViewFragment frag = FormViewFragment.newInstance();
		getFragmentManager().beginTransaction()
			.replace(R.id.container, frag, FormViewFragment.TAG).commit();
	}

	private void closeActivity() {
		finish();
	}

	private void savePerson() {
		String fname = ((EditText) findViewById(R.id.fNameText)).getText().toString().trim();
		String lname = ((EditText) findViewById(R.id.lNameText)).getText().toString().trim();
		String age = ((EditText) findViewById(R.id.ageText)).getText().toString().trim();

		// Check for empty values
		if (fname.isEmpty() || lname.isEmpty() || age.isEmpty()) {
			Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show();
			return;
		}

		int ageInt = Integer.parseInt(age);

		// Create the broadcast to send
		Intent broadcast = new Intent(SAVE_DATA);

		broadcast.putExtra(Person.FNAME_KEY, fname);
		broadcast.putExtra(Person.LNAME_KEY, lname);
		broadcast.putExtra(Person.AGE_KEY, ageInt);

		sendBroadcast(broadcast);
	}

}
