package com.icegotcha.searchlist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private EditText searchBox;
    private ListView listView;
    private DatabaseHandler db;
    private Cursor cursor;
    private BaseAdapter adapter;
    private List<Data> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = findViewById(R.id.searchBox);
        listView = findViewById(R.id.bookList);

        loadDatabase();
    }

    public void loadDatabase() {
        db = new DatabaseHandler(this);

        cursor = db.getAllRecord();
        sendToList(cursor);
        adapter = new DataAdapter(this, list);
        listView.setAdapter(adapter);

    }

    public void search(View view) {
        String searchText = searchBox.getText().toString();

        // hide keyboard when click the button
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        cursor = db.getSearchedResult(searchText);
        sendToList(cursor);
        ((DataAdapter) adapter).updateList(list);
    }

    private void sendToList(Cursor cursor) {
        list.clear();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(BookTable._ID));
                    String name = cursor.getString(cursor.getColumnIndex(BookTable.BOOK_NAME));
                    String author = cursor.getString(cursor.getColumnIndex(BookTable.AUTHOR_NAME));
                    String trans = cursor.getString(cursor.getColumnIndex(BookTable.TRANSLATOR_NAME));
                    String publisher = cursor.getString(cursor.getColumnIndex(BookTable.PUBLISHER_NAME));
                    float price = cursor.getFloat(cursor.getColumnIndex(BookTable.BOOK_PRICE));
                    String date = cursor.getString(cursor.getColumnIndex(BookTable.BOOK_REL_DATE));

                    list.add(new Data(id, name, author, publisher, trans, date, price));
                }
            }
        }
    }

}
