package ru.kalievmars.clubolimpus.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import ru.kalievmars.clubolimpus.R;
import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.*;

public class MemberCursorAdapter extends CursorAdapter {
    public MemberCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.member_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvFirstName = view.findViewById(R.id.text_firstname);
        TextView tvLastName = view.findViewById(R.id.text_lastname);
        TextView tvGender = view.findViewById(R.id.text_gender);
        TextView tvGroup = view.findViewById(R.id.text_group);

        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.KEY_FIRSTNAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.KEY_LASTNAME));
        Integer gender = cursor.getInt(cursor.getColumnIndexOrThrow(MemberEntry.KEY_GENDER));
        String group = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.KEY_GROUP));

        Gender[] genders = Gender.values();

        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);
        tvGender.setText(genders[gender].toString().toLowerCase());
        tvGroup.setText(group);
    }
}
