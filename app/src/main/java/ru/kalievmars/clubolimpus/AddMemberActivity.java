package ru.kalievmars.clubolimpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import ru.kalievmars.clubolimpus.data.ClubOlimpusContract;
import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.Gender;

public class AddMemberActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText groupEditText;
    Spinner genderSpinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    Gender gender;

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
                return true;
            case R.id.delete_member:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}