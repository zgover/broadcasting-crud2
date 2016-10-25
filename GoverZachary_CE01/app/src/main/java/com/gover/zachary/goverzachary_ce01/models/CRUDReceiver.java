// Zachary Gover
// MDF3 - 1611
// CRUDReceiver

package com.gover.zachary.goverzachary_ce01.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.gover.zachary.goverzachary_ce01.DetailActivity;
import com.gover.zachary.goverzachary_ce01.FormActivity;
import com.gover.zachary.goverzachary_ce01.MainActivity;

public class CRUDReceiver extends BroadcastReceiver {

	private Storage storage;

	@Override
	public void onReceive(Context context, Intent intent) {
		// Setup the storage
		storage = Storage.newInstance(context);
		String action = intent.getAction();

		switch(action) {
			case FormActivity.SAVE_DATA:
				saveData(context, intent);
				break;
			case DetailActivity.DELETE_DATA:
				deletePerson(context, intent);
				break;
			default:
				break;
		}
	}

	private Person buildPerson(Intent intent) {
		// Build a new person from the extras and save it to the device
		String fname = intent.getStringExtra(Person.FNAME_KEY);
		String lname = intent.getStringExtra(Person.LNAME_KEY);
		int age = intent.getIntExtra(Person.AGE_KEY, 0);

		Person person = new Person(fname, lname, age);

		return person;
	}

	private void saveData(Context context, Intent intent) {
		// Build and save the intent results as a person
		Person person = buildPerson(intent);
		storage.addPerson(person);

		updateList(context);
	}

	private void deletePerson(Context context, Intent intent) {
		// Removed the requested person
		Person person = buildPerson(intent);
		storage.deletePerson(person);
		updateList(context);
	}

	private void updateList(Context context) {
		// Send a broadcast to update the list view
		Intent broadcast = new Intent(MainActivity.UPDATE_LIST);
		context.sendBroadcast(broadcast);
	}

}
