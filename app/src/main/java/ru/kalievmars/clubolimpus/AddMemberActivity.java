package ru.kalievmars.clubolimpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.*;
import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.Gender;
import ru.kalievmars.clubolimpus.models.Member;

public class AddMemberActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText groupEditText;
    Gender gender;
    Spinner genderSpinner;
    ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        initViews();
        setSpinner();
    }

    void initViews() {
        firstNameEditText = findViewById(R.id.edittext_firstname);
        lastNameEditText = findViewById(R.id.edittext_lastname);
        groupEditText = findViewById(R.id.edittext_group);
        genderSpinner = findViewById(R.id.spinner);

    }

    void setSpinner() {

        arrayAdapter =
                ArrayAdapter.createFromResource(this, R.array.spinner_gender, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(arrayAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if(selectedItem.equals("Male")) {
                    gender = Gender.MALE;
                } else if(selectedItem.equals("Female")) {
                    gender = Gender.FEMALE;
                } else if(selectedItem.equals("Another")) {
                    gender = Gender.ANOTHER;
                } else {
                    gender = Gender.NONE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = Gender.NONE;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_member:
                insertMember();
                return true;
            case R.id.delete_member:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertMember() {
        String firstname = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = groupEditText.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberEntry.KEY_FIRSTNAME, firstname);
        contentValues.put(MemberEntry.KEY_LASTNAME, lastName);
        contentValues.put(MemberEntry.KEY_GENDER, gender.ordinal());
        contentValues.put(MemberEntry.KEY_GROUP, sport);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues);

        if(uri == null) {
            Toast.makeText(this, "Can't insert in URI", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insert was success", Toast.LENGTH_SHORT).show();
        }
    }

}