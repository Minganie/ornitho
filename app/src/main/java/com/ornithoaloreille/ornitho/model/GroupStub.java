package com.ornithoaloreille.ornitho.model;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.ornithoaloreille.ornitho.data.OrnithoDBContract;

/**
 * Created by Myriam on 2016-11-05.
 */

public class GroupStub implements Parcelable {
    private int id;
    private String name;
    private Drawable icon;
    private String iconName;
    private String sound;

    public GroupStub(int id, String name, String iconName, String sound) {
        this.id = id;
        this.name = name;
        this.icon = Group.getIcon(iconName);
        this.iconName = iconName;
        this.sound = sound;
    }

    public String toString() {
        return "GroupStub #" + id + ": " + name + " with sound file " + sound;
    }


    //----------------------------------------------------------------------------------------------
    // STATIC METHODS

    public static GroupStub getGroupStub(Cursor cursor) {
        int id = cursor.getInt(OrnithoDBContract.Groups.ID);
        String name = cursor.getString(OrnithoDBContract.Groups.NAME);
        String iconName = cursor.getString(OrnithoDBContract.Groups.ICON);
        String sound = cursor.getString(OrnithoDBContract.Groups.SOUND);
        return new GroupStub(id, name, iconName, sound);
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

    public String getIconName() {
        return iconName;
    }

    public String getSound() {
        return sound;
    }


    //----------------------------------------------------------------------------------------------
    // PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<GroupStub> CREATOR = new Parcelable.Creator<GroupStub>() {

        @Override
        public GroupStub createFromParcel(Parcel parcel) {
            return new GroupStub(parcel);
        }

        @Override
        public GroupStub[] newArray(int i) {
            return new GroupStub[i];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(iconName);
        parcel.writeString(sound);
    }

    private GroupStub(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        iconName = parcel.readString();
        icon = Group.getIcon(iconName);
        sound = parcel.readString();
    }
}
