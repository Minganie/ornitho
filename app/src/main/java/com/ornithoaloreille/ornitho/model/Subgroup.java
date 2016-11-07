package com.ornithoaloreille.ornitho.model;

import android.database.Cursor;

import com.ornithoaloreille.ornitho.data.OrnithoDBContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Myriam on 2016-10-28.
 */

public class Subgroup {
    private int id;
    private String description;
    private GroupStub group;
    private List<SpeciesStub> speciesStubs = new ArrayList<>();

    public static final String[] PROJECTION = new String[] { "_id", "description", "group_id", "group_name", "group_icon", "group_sound" };

    private Subgroup(int id, String description, GroupStub group) {
        this.id = id;
        this.description = description;
        this.group = group;
    }

    public String toString() {
        return "Subgroup #" + id + ": " + description.substring(0, 50) + "...";
    }


    //----------------------------------------------------------------------------------------------
    // STATIC METHODS

    public static Subgroup getSubgroup(Cursor subgroupCursor, Cursor speciesCursor) {
        int i = subgroupCursor.getInt(OrnithoDBContract.Subgroup.ID);
        String d = subgroupCursor.getString(OrnithoDBContract.Subgroup.DESCRIPTION);

        int gi = subgroupCursor.getInt(OrnithoDBContract.Subgroup.GROUP_ID);
        String gn = subgroupCursor.getString(OrnithoDBContract.Subgroup.GROUP_NAME);
        String gd = subgroupCursor.getString(OrnithoDBContract.Subgroup.GROUP_ICON);
        String gs = subgroupCursor.getString(OrnithoDBContract.Subgroup.GROUP_SOUND);
        GroupStub group = new GroupStub(gi, gn, gd, gs);

        Subgroup subgroup = new Subgroup(i, d, group);

        while(speciesCursor.moveToNext()) {
            subgroup.speciesStubs.add(SpeciesStub.getSpeciesStub(speciesCursor));
        }

        return subgroup;
    }


    //----------------------------------------------------------------------------------------------
    // GETTERS

    public String getDescription() {
        return description;
    }

    public List<SpeciesStub> getSpecies() {
        return speciesStubs;
    }
}
