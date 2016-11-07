package com.ornithoaloreille.ornitho.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.model.Group;
import com.ornithoaloreille.ornitho.model.Species;
import com.ornithoaloreille.ornitho.model.Subgroup;

/**
 * Created by Myriam on 2016-10-31.
 */
public class OrnithoDB extends ContentProvider {
    public static final String PROVIDER = "content://com.ornithoaloreille.ornitho.provider/";
    private OrnithoDBHelper helper;
    private SQLiteDatabase db;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "groups",  0);
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "groups/#", 1);
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "groups/#/subgroups", 2);
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "subgroups/#/species", 3);
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "species", 4);
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "species/#", 5);
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "species/#/groups", 6);
        matcher.addURI("com.ornithoaloreille.ornitho.provider", "species/#/species", 7);
    }

    @Override
    public boolean onCreate() {
        try {
            helper = new OrnithoDBHelper(getContext());
            db = helper.getReadableDatabase();
            return true;
        } catch (SQLiteException e) {
            Toast.makeText(getContext(), getContext().getString(R.string.db_error), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public Cursor queryShell(Uri uri) {
        return query(uri, null, null, null, null, null);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        String table;
        String[] projection;
        String selection;
        String[] selectionArgs;
        String sortOrder;
        String id = (uri.getPathSegments().size() > 1 ? uri.getPathSegments().get(1) : "");

        switch (matcher.match(uri)) {

            /* ------------------------------------- GROUPS ------------------------------------- */
            /* com.ornithoaloreille.ornitho.provider/groups */
            case 0:
                table = "groups";
                projection = Group.PROJECTION;
                selection = null;
                selectionArgs = null;
                sortOrder = "_id";
                break;

            /* com.ornithoaloreille.ornitho.provider/groups/# */
            case 1:
                table = "groups";
                projection = Group.PROJECTION;
                selection = "_id = ?";
                selectionArgs = new String[]{ id };
                sortOrder = null;
                break;

            /* com.ornithoaloreille.ornitho.provider/groups/#/subgroups */
            case 2:
                table = "subgroups_view";
                projection = Subgroup.PROJECTION;
                selection = "group_id = ?";
                selectionArgs = new String[]{ id };
                sortOrder = "_id";
                break;

            /* ------------------------------------ SUBGROUP ------------------------------------ */
            /* com.ornithoaloreille.ornitho.provider/subgroups/#/species */
            case 3:
                table = "subgroup_species";
                projection = Species.PROJECTION;
                selection = "subgroup = ?";
                selectionArgs = new String[]{ id };
                sortOrder = "_id";
                break;

            /* ------------------------------------ SPECIES ------------------------------------- */
            /* com.ornithoaloreille.ornitho.provider/species */
            case 4:
                table = "species_view";
                projection = Species.PROJECTION;
                selection = null;
                selectionArgs = null;
                sortOrder = "name";
                break;

            /* com.ornithoaloreille.ornitho.provider/species/# */
            case 5:
                table = "species_view";
                projection = Species.PROJECTION;
                selection = "_id = ?";
                selectionArgs = new String[]{ id };
                sortOrder = null;
                break;

            /* com.ornithoaloreille.ornitho.provider/species/#/group */
            case 6:
                table = "species_group";
                projection = Group.PROJECTION;
                selection = "species = ?";
                selectionArgs = new String[]{ id };
                sortOrder = null;
                break;

            /* com.ornithoaloreille.ornitho.provider/species/#/species */
            case 7:
            default:
                table = "species_species";
                projection = Species.PROJECTION;
                selection = "species = ? ";
                selectionArgs = new String[]{ id };
                sortOrder = "name";
        }

        Cursor cursor = db.query(table,    // table
                projection,         // columns
                selection,          // where
                selectionArgs,      // where what?
                null,               // group by
                null,               // having
                sortOrder,          // order by
                null                // limit
                );
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        StringBuilder res = new StringBuilder();
        res.append("vnd.android.cursor.");
        switch (matcher.match(uri)) {
            case 0:
                res.append("dir/");
                res.append("vnd.com.ornithoaloreille.provider.");
                res.append("groups");
                break;
            case 1:
                res.append("item/");
                res.append("vnd.com.ornithoaloreille.provider.");
                res.append("groups");
                break;
            case 2:
                res.append("dir/");
                res.append("vnd.com.ornithoaloreille.provider.");
                res.append("species");
                break;
            default:
                res.append("item/");
                res.append("vnd.com.ornithoaloreille.provider.");
                res.append("species");
                break;
        }

        return res.toString();
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException();
    }
}
