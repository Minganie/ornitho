package com.ornithoaloreille.ornitho.model;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.ornithoaloreille.ornitho.R;

/**
 * Created by Myriam on 2016-10-28.
 */

public class DrawerEntry {
    private int id;
    private Drawable icon;
    private String name;
    private static Resources resources;
    public static DrawerEntry[] entries;

    public static void setResources(Resources r) {
        resources = r;
        entries = new DrawerEntry[]{
                new DrawerEntry(0, ResourcesCompat.getDrawable(resources, R.drawable.ic_groups, null), resources.getString(R.string.groups)),
                new DrawerEntry(1, ResourcesCompat.getDrawable(resources, R.drawable.ic_warbler, null), resources.getString(R.string.species)),
                new DrawerEntry(2, ResourcesCompat.getDrawable(resources, R.drawable.ic_quiz, null), resources.getString(R.string.quizz))
        };
    }

    public DrawerEntry(int i, Drawable d, String s) {
        id = i;
        icon = d;
        name = s;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
