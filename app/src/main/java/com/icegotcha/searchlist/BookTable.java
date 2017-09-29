package com.icegotcha.searchlist;

import android.provider.BaseColumns;

/**
 * Created by icegotcha on 29/8/2560.
 */

/* Inner class that defines the table contents */
public class BookTable implements BaseColumns {
    public static final String TABLE_NAME = "SaleBook";
    public static final String BOOK_NAME = "book_name";
    public static final String AUTHOR_NAME = "book_author_name";
    public static final String TRANSLATOR_NAME = "book_translator_name";
    public static final String PUBLISHER_NAME = "book_publisher_name";
    public static final String BOOK_PRICE = "book_price";
    public static final String BOOK_REL_DATE = "book_release_date";

    public static final String[] COLUMNS = new String[]{
            _ID, BOOK_NAME, AUTHOR_NAME, TRANSLATOR_NAME,
            PUBLISHER_NAME, BOOK_PRICE, BOOK_REL_DATE
    };
}
