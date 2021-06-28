package com.example.projetmobile;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ImmobilierEdit extends Activity implements OnClickListener{

    private Spinner immobilierList;
    private Button save, delete;
    private String mode;
    private EditText prix, name;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immobilier_edit);

        // get the values passed to the activity from the calling activity
        // determine the mode - add, update or delete
        if (this.getIntent().getExtras() != null){
            Bundle bundle = this.getIntent().getExtras();
            mode = bundle.getString("mode");
        }

        // get references to the buttons and attach listeners
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        prix = (EditText) findViewById(R.id.code);
        name = (EditText) findViewById(R.id.name);


        // create a dropdown for users to select various continents
        immobilierList = (Spinner) findViewById(R.id.continentList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.continent_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        immobilierList.setAdapter(adapter);

        // if in add mode disable the delete option
        if(mode.trim().equalsIgnoreCase("add")){
            delete.setEnabled(false);
        }
        // get the rowId for the specific country
        else{
            Bundle bundle = this.getIntent().getExtras();
            id = bundle.getString("rowId");
            loadCountryInfo();
        }

    }

    public void onClick(View v) {

        // get values from the spinner and the input text fields
        String myContinent = immobilierList.getSelectedItem().toString();
        String myPrix = prix.getText().toString();
        String myName = name.getText().toString();

        // check for blanks
        if(myPrix.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Entrer le prix SVP", Toast.LENGTH_LONG).show();
            return;
        }

        // check for blanks
        if(myName.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Entrer le nom SVP", Toast.LENGTH_LONG).show();
            return;
        }


        switch (v.getId()) {
            case R.id.save:
                ContentValues values = new ContentValues();
                values.put(ImmobilierDB.KEY_PRIX, myPrix);
                values.put(ImmobilierDB.KEY_NAME, myName);
                values.put(ImmobilierDB.KEY_CATEGORIE, myContinent);

                // insert a record
                if(mode.trim().equalsIgnoreCase("add")){
                    getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                }
                // update a record
                else {
                    Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
                    getContentResolver().update(uri, values, null, null);
                }
                finish();
                break;

            case R.id.delete:
                // delete a record
                Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
                getContentResolver().delete(uri, null, null);
                finish();
                break;

            // More buttons go here (if any) ...

        }
    }

    // based on the rowId get all information from the Content Provider
    // about that country
    private void loadCountryInfo(){

        String[] projection = {
                ImmobilierDB.KEY_ROWID,
                ImmobilierDB.KEY_PRIX,
                ImmobilierDB.KEY_NAME,
                ImmobilierDB.KEY_CATEGORIE};
        Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String myPrix = cursor.getString(cursor.getColumnIndexOrThrow(ImmobilierDB.KEY_PRIX));
            String myName = cursor.getString(cursor.getColumnIndexOrThrow(ImmobilierDB.KEY_NAME));
            String myContinent = cursor.getString(cursor.getColumnIndexOrThrow(ImmobilierDB.KEY_CATEGORIE));
            prix.setText(myPrix);
            name.setText(myName);
            immobilierList.setSelection(getIndex(immobilierList, myContinent));
        }


    }

    // this sets the spinner selection based on the value
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

}