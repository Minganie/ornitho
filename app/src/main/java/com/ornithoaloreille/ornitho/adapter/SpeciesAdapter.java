package com.ornithoaloreille.ornitho.adapter;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.model.SpeciesStub;

/**
 * Created by Myriam on 2016-11-03.
 */
public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.ViewHolder> {
    private static final String TAG = GroupAdapter.class.getSimpleName();
    private Cursor cursor;
    private Resources resources;
    private SpeciesClickListener listener = null;


    //----------------------------------------------------------------------------------------------
    // LISTENER

    public interface SpeciesClickListener {
        void speciesClicked(SpeciesStub species);
    }

    public void setListener(SpeciesClickListener l) {
        listener = l;
    }


    //----------------------------------------------------------------------------------------------
    // VIEW HOLDER
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView speciesName;
        private SpeciesStub species;

        public ViewHolder(CardView itemView) {
            super(itemView);
            speciesName = (TextView) itemView.findViewById(R.id.csgn_name);
            itemView.setOnClickListener(this);  // Do this cause Cardview has a onClick of its own
        }

        @Override
        public void onClick(View v) {
//            Log.d(TAG, "View holder clicked");
            if(speciesName!= null && listener != null) {
                listener.speciesClicked(species);
            }
        }
    }


    //----------------------------------------------------------------------------------------------
    // ADAPTER
    public SpeciesAdapter(Cursor c, Resources r) {
        cursor = c;
        resources = r;
    }

    private SpeciesAdapter() {

    }

    public static SpeciesAdapter getEmptyAdapter() {
        return new SpeciesAdapter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_species_group_and_name, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        SpeciesStub species = SpeciesStub.getSpeciesStub(cursor);

        holder.species = species;
        Drawable drawable = species.getGroupStub().getIcon();
        int size = resources.getDimensionPixelSize(R.dimen.small_icon);
        drawable.setBounds(0, 0, size, size);
        holder.speciesName.setText(species.getName());
        holder.speciesName.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public int getItemCount() {
        if(cursor != null)
            return cursor.getCount();
        else
            return 0;
    }

//    public void closeCursor() {
//        if(cursor != null) {
//            cursor.close();
//            cursor = null;
//        }
//    }
}
