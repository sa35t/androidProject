package com.example.lab1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	private static  String name;
	private static  String email;
	private static  String phone;
	private static  String major;
	SharedPreferences preference;
	Editor toEdit;
	AutoCompleteTextView Editname;
	EditText Editemail;
	EditText Editphone;
	Spinner Editclass ;
	AutoCompleteTextView Editmajor;
	RadioGroup radioGroupGender;
	
	private final  static String pref_name = "Profile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		preference = getSharedPreferences(pref_name, MODE_PRIVATE); 
		toEdit = preference.edit();
		
		Editname = (AutoCompleteTextView) findViewById(R.id.editName);
		String[] name = getResources().getStringArray(R.array.candidate);
		ArrayAdapter<String> adapterName = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
		Editname.setAdapter(adapterName);
		
		Editemail = (EditText) findViewById(R.id.editEmail);
		Editphone = (EditText) findViewById(R.id.editPhone);
		
		Editclass = (Spinner) findViewById(R.id.editClass);
		String[] year=getResources().getStringArray(R.array.year);
		ArrayAdapter<String> classadapter = new  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, year);
		Editclass.setAdapter(classadapter);
		
		Editmajor= (AutoCompleteTextView) findViewById(R.id.editMajor);
		String[] major = getResources().getStringArray(R.array.major);
		ArrayAdapter<String> adapterMajor = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, major);
		Editmajor.setAdapter(adapterMajor);
		
		radioGroupGender = (RadioGroup) findViewById(R.id.radioGenderGroup);
		
		if(preference.getBoolean("firstTime", false))
		{
			/* Load profile which was saved Earlier */
			loadProfile();
		}
	}
	
	
	public void onSave(View view)
	{
		/* Save Profile */
		saveProfile();
		Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void onCancel(View view)
	{
		/* Alert Box on Cancel Button */
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
	        builder.setMessage(R.string.cancel)
	        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	               finish();
	            }
	        })
	        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	              dialog.cancel();
	            }
	        });
	        // Create the AlertDialog object and return it
	       builder.show();   
	}
	
	/* Load Profile which was Save Earlier */
	
	private void loadProfile()
	{
			
		Editname.setText(preference.getString("Name", null));
		Editemail.setText(preference.getString("Email", null));
		Editphone.setText(preference.getString("Phone", null));
		Editclass.setSelection(preference.getInt("Class", 0));
		Editmajor.setText(preference.getString("Major", null));
		/* Load the checked radio button before closing of application */
		RadioButton rd = (RadioButton) radioGroupGender.findViewById(preference.getInt("Gender", 0));
		radioGroupGender.check(rd.getId());		
	}
	
	/* Save Profile on the Click of Save button */
	
	private void saveProfile()
	{
		name = Editname.getText().toString();
		email = Editemail.getText().toString();
		phone =  Editphone.getText().toString();
		major = Editmajor.getText().toString();
		
		toEdit.putString("Name", name);
		toEdit.putString("Email", email);
		toEdit.putString("Phone", phone);
		toEdit.putInt("Class", Editclass.getSelectedItemPosition());
		toEdit.putString("Major", major);
		/* Setting gender value equals to id of selected radio button */
		toEdit.putInt("Gender", radioGroupGender.getCheckedRadioButtonId());
		toEdit.putBoolean("firstTime", true);
		toEdit.commit();		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
