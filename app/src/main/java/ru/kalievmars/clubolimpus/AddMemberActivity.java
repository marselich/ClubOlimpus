package ru.kalievmars.clubolimpus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText groupEditText;
    Gender gender;
    Spinner genderSpinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    Uri contentProviderUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();
        contentProviderUri = intent.getData();

        if(contentProviderUri == null) {
            setTitle("Add a member");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit the member");
            LoaderManager.getInstance(this).initLoader(222, null, this);
        }

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
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if(contentProviderUri == null) {
            MenuItem menuItem = menu.findItem(R.id.delete_member);
            menuItem.setEnabled(false);
        }

        return true;
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
                saveMember();
                return true;
            case R.id.delete_member:
                showDeleteDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteMember() {
        if(contentProviderUri != null) {
            int row = getContentResolver().delete(contentProviderUri, null, null);
            if (row != 0) {
                Toast.makeText(this, "Member deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void saveMember() {
        String firstname = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = groupEditText.getText().toString().trim();

        if(TextUtils.isEmpty(firstname)) {
            Toast.makeText(this, "First name is empty", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Last name is empty", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(sport)) {
            Toast.makeText(this, "Group is empty", Toast.LENGTH_SHORT).show();
        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(MemberEntry.KEY_FIRSTNAME, firstname);
            contentValues.put(MemberEntry.KEY_LASTNAME, lastName);
            contentValues.put(MemberEntry.KEY_GENDER, gender.ordinal());
            contentValues.put(MemberEntry.KEY_GROUP, sport);

            if (contentProviderUri == null) {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues);

                if (uri == null) {
                    Toast.makeText(this, "Can't insert in URI", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Member saved", Toast.LENGTH_SHORT).show();
                }
            } else {

                int rows = getContentResolver().update(contentProviderUri, contentValues, null, null);
                if (rows != 0) {
                    Toast.makeText(this, "Member updated", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = new String[] {
                MemberEntry.KEY_ID,
                MemberEntry.KEY_FIRSTNAME,
                MemberEntry.KEY_LASTNAME,
                MemberEntry.KEY_GENDER,
                MemberEntry.KEY_GROUP
        };

        return new CursorLoader(
                this,
                contentProviderUri,
                projection,
                null,
                null,
                null
                );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()) {
            int firstNameIndex = data.getColumnIndex(MemberEntry.KEY_FIRSTNAME);
            int lastNameIndex = data.getColumnIndex(MemberEntry.KEY_LASTNAME);
            int genderIndex = data.getColumnIndex(MemberEntry.KEY_GENDER);
            int groupIndex = data.getColumnIndex(MemberEntry.KEY_GROUP);

            firstNameEditText.setText(data.getString(firstNameIndex));
            lastNameEditText.setText(data.getString(lastNameIndex));
            genderSpinner.setSelection(data.getInt(genderIndex));
            groupEditText.setText(data.getString(groupIndex));
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you really want to delete this member?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMember();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}