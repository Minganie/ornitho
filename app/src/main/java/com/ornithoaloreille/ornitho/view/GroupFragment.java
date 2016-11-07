package com.ornithoaloreille.ornitho.view;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.adapter.SpeciesAdapter;
import com.ornithoaloreille.ornitho.model.Group;
import com.ornithoaloreille.ornitho.model.SpeciesStub;
import com.ornithoaloreille.ornitho.model.Subgroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    private static final String TAG = GroupFragment.class.getSimpleName();

    private Context context;
    private Group group;
    private SpeciesAdapter.SpeciesClickListener listener = null;


    //----------------------------------------------------------------------------------------------
    // LISTENER

    public void setListener(SpeciesAdapter.SpeciesClickListener l) {
        listener = l;
    }

    public GroupFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ViewGroup root = (ViewGroup) view.findViewById(R.id.group_fragment_root);

        TextView nameView = (TextView) view.findViewById(R.id.group_name_view);

        if(nameView != null && group != null) {
            nameView.setText(group.getName());
            Drawable icon = group.getIcon();
            int size = context.getResources().getDimensionPixelSize(R.dimen.large_icon);
            icon.setBounds(0, 0, size, size);
            nameView.setCompoundDrawables(group.getIcon(), null, null, null);
        }

        for(Subgroup subgroup : group.getSubgroups()) {
            TextView v = (TextView) inflater.inflate(R.layout.subgroup_view, root, false);
            v.setText(subgroup.getDescription());
            root.addView(v);

            for(final SpeciesStub speciesStub : subgroup.getSpecies()) {
                CardView cv = (CardView) inflater.inflate(R.layout.card_species_name_and_description, root, false);

                // Set the species CardView content
                TextView name = (TextView) cv.findViewById(R.id.csnd_name);
                name.setText(speciesStub.getName());
                TextView description = (TextView) cv.findViewById(R.id.csnd_description);
                description.setText(speciesStub.getDescription());

                // Set a click listener on it
                cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listener != null) {
                            listener.speciesClicked(speciesStub);
                        }
                    }
                });

                // Add it to layout
                root.addView(cv);
            }
        }

        return view;
    }

    public void setGroup(Group group) {
        this.group = group;
//        Log.d(TAG, "In Group Fragment setGroup is: " + group.toString());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (SpeciesAdapter.SpeciesClickListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SpeciesAdapter.SpeciesClickListener) context;
    }
}
