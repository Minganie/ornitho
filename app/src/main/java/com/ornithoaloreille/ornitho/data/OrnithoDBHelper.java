package com.ornithoaloreille.ornitho.data;


import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Myriam on 2016-10-29.
 */

public class OrnithoDBHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "ornitho.db";
    private static final int DB_VERSION = 1;

    public OrnithoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
