package com.ornithoaloreille.ornitho.view;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.adapter.SpeciesAdapter;
import com.ornithoaloreille.ornitho.data.OrnithoDB;
import com.ornithoaloreille.ornitho.data.OrnithoDBContract;
import com.ornithoaloreille.ornitho.model.Species;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeciesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = SpeciesListFragment.class.getSimpleName();

    private Context context = null;
    private RecyclerView recyclerView = null;
    private SpeciesAdapter adapter = null;
    private SpeciesAdapter.SpeciesClickListener listener = null;

    public static String TITLE;
    private static final int SPECIES_STUB_LOADER = 1254;

    public SpeciesListFragment() {
        // Required empty public constructor
    }

    public void setContext(Context c) {
        context = c;
        TITLE = context.getString(R.string.species);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_species_list, container, false);

        // Set adapter and give it data...
        recyclerView = (RecyclerView) view;
        if(recyclerView != null) {
            recyclerView.setAdapter(SpeciesAdapter.getEmptyAdapter());
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (adapter != null)
                fillView();
        }

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getLoaderManager().initLoader(SPECIES_STUB_LOADER, null, this);
        listener = (SpeciesAdapter.SpeciesClickListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(SPECIES_STUB_LOADER, null, this);
        listener = (SpeciesAdapter.SpeciesClickListener) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.closeCursor();
    }

    private void fillView() {
        recyclerView.setAdapter(adapter);
        if(listener != null)
            adapter.setListener(listener);
    }


    //----------------------------------------------------------------------------------------------
    // CURSOR LOADER

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i == SPECIES_STUB_LOADER)
            return new CursorLoader(context, Uri.parse(OrnithoDB.PROVIDER + "species"), null, null, null, null);
        else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId() == SPECIES_STUB_LOADER) {
            adapter = new SpeciesAdapter(cursor, getResources());
            if(recyclerView != null) {
                fillView();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == SPECIES_STUB_LOADER && adapter != null) {
            adapter.closeCursor();
        }
    }
}
