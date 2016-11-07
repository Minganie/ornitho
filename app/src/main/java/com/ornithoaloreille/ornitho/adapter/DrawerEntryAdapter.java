package com.ornithoaloreille.ornitho.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.model.DrawerEntry;

/**
 * Created by Myriam on 2016-10-28.
 */

public class DrawerEntryAdapter extends RecyclerView.Adapter<DrawerEntryAdapter.ViewHolder> {
    private static final String TAG = DrawerEntry.class.getSimpleName();
    private DrawerEntry[] drawerEntries = new DrawerEntry[]{};
    private Resources resources;


    //----------------------------------------------------------------------------------------------
    // LISTENER

    public interface DrawerClickListener {
        void drawerClicked(DrawerEntry entry);
    }
    private DrawerClickListener listener = null;

    public void setListener(DrawerClickListener l) {
        listener = l;
    }


    //----------------------------------------------------------------------------------------------
    // VIEW HOLDER
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView entryName;
        private DrawerEntry drawerEntry;

        public ViewHolder(CardView itemView) {
            super(itemView);
            entryName = (TextView) itemView.findViewById(R.id.drawer_name);
            itemView.setOnClickListener(this);  // Do this cause Cardview has a onClick of its own
        }

        @Override
        public void onClick(View v) {
//            Log.d(TAG, "View holder clicked");
            if(drawerEntry != null && listener != null) {
                listener.drawerClicked(drawerEntry);
            }
        }
    }


    //----------------------------------------------------------------------------------------------
    // ADAPTER
    public DrawerEntryAdapter(DrawerEntry[] entries, Resources r) {
        drawerEntries = entries;
        resources = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_drawer, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DrawerEntry entry = drawerEntries[position];

        TextView name = holder.entryName;
        holder.drawerEntry = entry;
        name.setText(entry.getName());
        Drawable icon = entry.getIcon();
        icon.setBounds(0, 0, resources.getDimensionPixelSize(R.dimen.large_icon), resources.getDimensionPixelSize(R.dimen.large_icon));
        name.setCompoundDrawables(icon, null, null, null);
    }

    @Override
    public int getItemCount() {
        return drawerEntries.length;
    }

}
