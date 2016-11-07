package com.ornithoaloreille.ornitho.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ornithoaloreille.ornitho.data.OrnithoDBContract;

/**
 * Created by Myriam on 2016-11-05.
 */

public class SpeciesStub implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String sound;
    private String wiki;
    private GroupStub groupStub;

    private SpeciesStub(int id, String name, String description, String sound, String wiki, GroupStub groupStub) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sound = sound;
        this.wiki = wiki;
        this.groupStub = groupStub;
    }

    public String toString() {
        return "SpeciesStub #" + id + ": " + name + " - " + description.substring(0, 20) + "...";
    }


    //----------------------------------------------------------------------------------------------
    // STATIC METHODS

    public static SpeciesStub getSpeciesStub(Cursor cursor) {
        int i = cursor.getInt(OrnithoDBContract.Species.ID);
        String n = cursor.getString(OrnithoDBContract.Species.NAME);
        String d = cursor.getString(OrnithoDBContract.Species.DESCRIPTION);
        String s = cursor.getString(OrnithoDBContract.Species.SOUND);
        String w = cursor.getString(OrnithoDBContract.Species.WIKI);

        int gi = cursor.getInt(OrnithoDBContract.Species.GROUP_ID);
        String gn = cursor.getString(OrnithoDBContract.Species.GROUP_NAME);
        String gd = cursor.getString(OrnithoDBContract.Species.GROUP_ICON);
        String gs = cursor.getString(OrnithoDBContract.Species.GROUP_SOUND);
        GroupStub groupStub = new GroupStub(gi, gn, gd, gs);

        return new SpeciesStub(i, n, d, s, w, groupStub);
    }


    //----------------------------------------------------------------------------------------------
    // GETTERS

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSound() {
        return sound;
    }

    public String getWiki() {
        return wiki;
    }

    public GroupStub getGroupStub() {
        return groupStub;
    }


    //----------------------------------------------------------------------------------------------
    // PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SpeciesStub> CREATOR = new Parcelable.Creator<SpeciesStub>() {

        @Override
        public SpeciesStub createFromParcel(Parcel parcel) {
            return new SpeciesStub(parcel);
        }

        @Override
        public SpeciesStub[] newArray(int i) {
            return new SpeciesStub[i];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(sound);
        parcel.writeString(wiki);
        parcel.writeParcelable(groupStub, i);
    }

    private SpeciesStub(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        description = parcel.readString();
        sound = parcel.readString();
        wiki = parcel.readString();
        groupStub = parcel.readParcelable(GroupStub.class.getClassLoader());
    }
}
