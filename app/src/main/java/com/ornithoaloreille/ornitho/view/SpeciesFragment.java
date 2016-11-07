package com.ornithoaloreille.ornitho.view;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.model.Species;
import com.ornithoaloreille.ornitho.model.SpeciesStub;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeciesFragment extends Fragment {
    private static final String TAG = SpeciesFragment.class.getSimpleName();

    private Species species;

    public SpeciesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_species, container, false);
        ViewGroup root = (ViewGroup) view.findViewById(R.id.species_fragment_container);

        TextView nameView = (TextView) view.findViewById(R.id.species_fragment_name);
        TextView groupView = (TextView) view.findViewById(R.id.species_fragment_group);
        TextView descriptionView = (TextView) view.findViewById(R.id.species_fragment_description);

        nameView.setText(species.getName());

        Drawable icon = species.getGroupStub().getIcon();
        int size = getResources().getDimensionPixelSize(R.dimen.small_icon);
        icon.setBounds(0, 0, size, size);
        groupView.setCompoundDrawables(icon, null, null, null);
        groupView.setText(species.getGroupStub().getName());

        descriptionView.setText(species.getDescription());

        for(SpeciesStub similarSpecies : species.getSimilarSpecies()) {
            CardView cv = (CardView) inflater.inflate(R.layout.card_species_group_and_name, root, false);
            TextView tv = (TextView) cv.findViewById(R.id.csgn_name);

            Drawable drawable = similarSpecies.getGroupStub().getIcon();
            int i = getResources().getDimensionPixelSize(R.dimen.small_icon);
            drawable.setBounds(0, 0, i, i);
            tv.setCompoundDrawables(drawable, null, null, null);
            tv.setText(similarSpecies.getName());

            root.addView(cv);
        }

        return view;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}
