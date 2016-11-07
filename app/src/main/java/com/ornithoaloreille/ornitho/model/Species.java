package com.ornithoaloreille.ornitho.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Myriam on 2016-10-28.
 */

public class Species implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String sound;
    private String wiki;
    private GroupStub groupStub;
    private ArrayList<SpeciesStub> similarSpecies = new ArrayList<>();

    public static final String[] PROJECTION = new String[] { "_id", "name", "description", "sound", "wiki", "group_id", "group_name", "group_icon", "group_sound"};

    private Species(SpeciesStub speciesStub) {
        id = speciesStub.getId();
        name = speciesStub.getName();
        description = speciesStub.getDescription();
        sound = speciesStub.getName();
        wiki = speciesStub.getWiki();
        groupStub = speciesStub.getGroupStub();
    }

    public String toString() {
        return "Species #" + id + ": " + name + " - " + description.substring(0, Math.min(40, description.length())) + "...";
    }


    //----------------------------------------------------------------------------------------------
    // STATIC METHODS

    public static Species getSpecies(SpeciesStub speciesStub, Cursor cursor) {

        Species species = new Species(speciesStub);

        while(cursor.moveToNext()) {
            species.similarSpecies.add(SpeciesStub.getSpeciesStub(cursor));
        }

        return species;
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

    public List<SpeciesStub> getSimilarSpecies() {
        return similarSpecies;
    }


    //----------------------------------------------------------------------------------------------
    // PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Species> CREATOR = new Parcelable.Creator<Species>() {

        @Override
        public Species createFromParcel(Parcel parcel) {
            return new Species(parcel);
        }

        @Override
        public Species[] newArray(int i) {
            return new Species[i];
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
        parcel.writeList(similarSpecies);
    }

    private Species(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        description = parcel.readString();
        sound = parcel.readString();
        wiki = parcel.readString();
        groupStub = parcel.readParcelable(GroupStub.class.getClassLoader());
        similarSpecies = parcel.readArrayList(SpeciesStub.class.getClassLoader());
    }

}
