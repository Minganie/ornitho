package com.ornithoaloreille.ornitho.model;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.ornithoaloreille.ornitho.data.OrnithoDB;
import com.ornithoaloreille.ornitho.data.OrnithoDBContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Myriam on 2016-10-28.
 */

public class Group implements Parcelable {
    private static final String TAG = Group.class.getSimpleName();

    private int id;
    private String name;
    private Drawable icon;
    private String iconName;
    private String sound;
    private ArrayList<Subgroup> subgroups = new ArrayList<>();

    private static Resources resources;
    public static final String[] PROJECTION = new String[] {"_id", "name", "icon", "sound"};

    public static void setResources(Resources r) {
        resources = r;
    }

    private Group(GroupStub groupStub) {
        id = groupStub.getId();
        name = groupStub.getName();
        icon = groupStub.getIcon();
        iconName = groupStub.getIconName();
        sound = groupStub.getSound();
    }

    public String toString() {
        return "Group #" + id + ": " + name + " - Song: " + sound + " - Subgroups: " + subgroups.toString();
    }


    //----------------------------------------------------------------------------------------------
    // STATIC METHODS

    public static Drawable getIcon(String iconName) {
        Drawable icon = null;
        if(resources != null) {
            int icon_id = resources.getIdentifier("ic_" + iconName, "drawable", "com.ornithoaloreille.ornitho");
            icon = resources.getDrawable(icon_id, null);
        }
        return icon;
    }

    // This is done IN BACKGROUND by AsyncGetGroup, so querying the DB here to populate the species is OK
    public static Group getGroup(GroupStub groupStub, Cursor cursor, Context context) {
        Group group = new Group(groupStub);
        while(cursor.moveToNext()) {
            Cursor speciesStubsCursor = context.getContentResolver().query(
                    Uri.parse(OrnithoDB.PROVIDER + "subgroups/" + cursor.getInt(OrnithoDBContract.Subgroup.ID) + "/species"),
                    null,
                    null,
                    null,
                    null
            );
            Subgroup subgroup = Subgroup.getSubgroup(cursor, speciesStubsCursor);
            group.subgroups.add(subgroup);
        }
        return group;
    }


    //----------------------------------------------------------------------------------------------
    // GETTERS

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getSound() {
        return sound;
    }

    public List<Subgroup> getSubgroups() {
        return subgroups;
    }


    //----------------------------------------------------------------------------------------------
    // PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {

        @Override
        public Group createFromParcel(Parcel parcel) {
            return new Group(parcel);
        }

        @Override
        public Group[] newArray(int i) {
            return new Group[i];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(iconName);
        parcel.writeString(sound);
        parcel.writeList(subgroups);
    }

    private Group(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        iconName = parcel.readString();
        icon = getIcon(parcel.readString());
        sound = parcel.readString();
        subgroups = parcel.readArrayList(Subgroup.class.getClassLoader());
    }
}
