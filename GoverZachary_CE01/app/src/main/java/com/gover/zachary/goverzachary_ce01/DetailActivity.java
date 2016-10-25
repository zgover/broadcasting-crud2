// Zachary Gover
// MDF3 - 1611
// DetailActivity

package com.gover.zachary.goverzachary_ce01;

import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.gover.zachary.goverzachary_ce01.fragments.DetailViewFragment;
import com.gover.zachary.goverzachary_ce01.fragments.FormViewFragment;
import com.gover.zachary.goverzachary_ce01.models.Person;

public class DetailActivity extends AppCompatActivity {

	public static final String DELETE_DATA = "com.fullsail.android.ACTION_DELETE_DATA";
	public static final String VIEW_DATA = "com.fullsail.android.ACTION_VIEW_DATA";

	private Person person;
	private BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupDetailView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Setup Action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_detail, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Determine selection
		switch(item.getItemId()) {
			case R.id.action_delete:
				deletePerson();
				break;
			default:
				break;
		}

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				closeActivity();
			}
		};

		// Register each receiver
		IntentFilter filter = new IntentFilter();

		filter.addAction(MainActivity.UPDATE_LIST);

		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	private void closeActivity() {
		finish();
	}

	private void setupDetailView() {
		Intent intent = getIntent();

		if (intent.hasExtra(Person.FNAME_KEY) && intent.hasExtra(Person.LNAME_KEY) &&
			intent.hasExtra(Person.AGE_KEY)) {
			// Build person from results
			String fname = intent.getStringExtra(Person.FNAME_KEY);
			String lname = intent.getStringExtra(Person.LNAME_KEY);
			int age = intent.getIntExtra(Person.AGE_KEY, 0);

			person = new Person(fname, lname, age);

			DetailViewFragment frag = DetailViewFragment.newInstance();
			Bundle args = new Bundle();

			// Pass the person to the fragment
			args.putSerializable(Person.KEY, person);
			frag.setArguments(args);
			getFragmentManager().beginTransaction()
				.replace(R.id.container, frag, FormViewFragment.TAG).commit();

			return;
		}

		Toast.makeText(this, "There has been an error processing your request.", Toast.LENGTH_LONG).show();
	}

	private void deletePerson() {
		// Confirm with the user before deleting
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Are you sure?");
		builder.setMessage("Are you sure you would like to delete this person?");
		builder.setPositiveButton("Cancel", null);
		builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				// Create the broadcast to send
				Intent broadcast = new Intent(DELETE_DATA);

				broadcast.putExtra(Person.FNAME_KEY, person.getFname());
				broadcast.putExtra(Person.LNAME_KEY, person.getLname());
				broadcast.putExtra(Person.AGE_KEY, person.getAge());

				sendBroadcast(broadcast);
			}
		}).show();
	}
}
