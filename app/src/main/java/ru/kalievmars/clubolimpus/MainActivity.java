package ru.kalievmars.clubolimpus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.kalievmars.clubolimpus.adapters.MemberCursorAdapter;
import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.MemberEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    final static int MEMBER_LOADER = 123;

    ListView dataListView;

    FloatingActionButton floatingActionButton;

    MemberCursorAdapter memberCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataListView = findViewById(R.id.listView);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener((view)-> {
            Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
            startActivity(intent);
        });

        memberCursorAdapter = new MemberCursorAdapter(this, null, false);
        dataListView.setAdapter(memberCursorAdapter);

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(4000);
                view.startAnimation(animation1);
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                Uri uri = ContentUris.withAppendedId(MemberEntry.CONTENT_URI, id);
                intent.setData(uri);
                startActivity(intent);
            }
        });

       LoaderManager.getInstance(this).initLoader(MEMBER_LOADER, null, this);
//        getSupportLoaderManager().initLoader(MEMBER_LOADER, null, this);
    }



//    void displayData() {
//        String[] projection = new String[] {
//                MemberEntry._ID,
//                MemberEntry.KEY_FIRSTNAME,
//                MemberEntry.KEY_LASTNAME,
//                MemberEntry.KEY_GENDER,
//                MemberEntry.KEY_GROUP
//        };
//
//        Cursor cursor = getContentResolver().query(
//                MemberEntry.CONTENT_URI,
//                projection,
//                null,
//                null,
//                null,
//                null
//        );
//
//        memberCursorAdapter = new MemberCursorAdapter(this, cursor, false);
//
//        dataListView.setAdapter(memberCursorAdapter);


//        dataTextView.setText("All members \n\n");
//        dataTextView.append(MemberEntry._ID + " " +
//                MemberEntry.KEY_FIRSTNAME + " " +
//                MemberEntry.KEY_LASTNAME + " " +
//                MemberEntry.KEY_GENDER + " " +
//                MemberEntry.KEY_GROUP + " \n");
//
//        int idIndex = cursor.getColumnIndex(MemberEntry._ID);
//        int firstNameIndex = cursor.getColumnIndex(MemberEntry.KEY_FIRSTNAME);
//        int lastNameIndex = cursor.getColumnIndex(MemberEntry.KEY_LASTNAME);
//        int genderIndex = cursor.getColumnIndex(MemberEntry.KEY_GENDER);
//        int groupIndex = cursor.getColumnIndex(MemberEntry.KEY_GROUP);
//
//        while(cursor.moveToNext()) {
//            int currentId = cursor.getInt(idIndex);
//            String currentFirstName = cursor.getString(firstNameIndex);
//            String currentLastName = cursor.getString(lastNameIndex);
//            int currentGender = cursor.getInt(genderIndex);
//            String currentGroup = cursor.getString(groupIndex);
//
//            dataTextView.append(currentId + " " +
//                    currentFirstName + " " +
//                    currentLastName + " " +
//                    currentGender + " " +
//                    currentGroup + " \n");
//        }
//        cursor.close();

//    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = new String[] {
                MemberEntry._ID,
                MemberEntry.KEY_FIRSTNAME,
                MemberEntry.KEY_LASTNAME,
                MemberEntry.KEY_GENDER,
                MemberEntry.KEY_GROUP
        };


        final CursorLoader cursorLoader = new CursorLoader(
                this,
                MemberEntry.CONTENT_URI,
                projection,
                null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(memberCursorAdapter != null && data != null) {
            memberCursorAdapter.swapCursor(data);
        } else {
            Log.d("error", "OnLoadFinished: mAdapter is null");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if(memberCursorAdapter != null) {
            memberCursorAdapter.swapCursor(null);
        }
    }
}