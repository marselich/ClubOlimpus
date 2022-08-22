package ru.kalievmars.clubolimpus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.MemberEntry;

public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataTextView = findViewById(R.id.textView);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener((view)-> {
            Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    void displayData() {
        String[] projection = new String[] {
                MemberEntry._ID,
                MemberEntry.KEY_FIRSTNAME,
                MemberEntry.KEY_LASTNAME,
                MemberEntry.KEY_GENDER,
                MemberEntry.KEY_GROUP
        };

        Cursor cursor = getContentResolver().query(
                MemberEntry.CONTENT_URI,
                projection,
                null,
                null,
                null,
                null
        );

        dataTextView.setText("All members \n\n");
        dataTextView.append(MemberEntry._ID + " " +
                MemberEntry.KEY_FIRSTNAME + " " +
                MemberEntry.KEY_LASTNAME + " " +
                MemberEntry.KEY_GENDER + " " +
                MemberEntry.KEY_GROUP + " \n");

        int idIndex = cursor.getColumnIndex(MemberEntry._ID);
        int firstNameIndex = cursor.getColumnIndex(MemberEntry.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(MemberEntry.KEY_LASTNAME);
        int genderIndex = cursor.getColumnIndex(MemberEntry.KEY_GENDER);
        int groupIndex = cursor.getColumnIndex(MemberEntry.KEY_GROUP);

        while(cursor.moveToNext()) {
            int currentId = cursor.getInt(idIndex);
            String currentFirstName = cursor.getString(firstNameIndex);
            String currentLastName = cursor.getString(lastNameIndex);
            int currentGender = cursor.getInt(genderIndex);
            String currentGroup = cursor.getString(groupIndex);

            dataTextView.append(currentId + " " +
                    currentFirstName + " " +
                    currentLastName + " " +
                    currentGender + " " +
                    currentGroup + " \n");
        }
        cursor.close();

    }
}