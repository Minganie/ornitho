package com.ornithoaloreille.ornitho.data;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.ornithoaloreille.ornitho.model.Group;
import com.ornithoaloreille.ornitho.model.GroupStub;
import com.ornithoaloreille.ornitho.model.Species;
import com.ornithoaloreille.ornitho.model.SpeciesStub;
import com.ornithoaloreille.ornitho.view.MainActivity;

/**
 * Created by Myriam on 2016-11-05.
 */

public class AsyncGetSpecies extends AsyncTask<SpeciesStub, Void, Species> {
    private MainActivity mainActivity;

    public AsyncGetSpecies(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Species doInBackground(SpeciesStub... speciesStubs) {
        Cursor cursor = mainActivity.getContentResolver().query(
                Uri.parse(OrnithoDB.PROVIDER + "species/" + speciesStubs[0].getId() + "/species"),
                null,
                null,
                null,
                null
        );

        Species species = Species.getSpecies(speciesStubs[0], cursor);

        return species;
    }

    @Override
    protected void onPostExecute(Species species) {
        mainActivity.selectItem(species);
    }
}