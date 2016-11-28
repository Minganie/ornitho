package com.ornithoaloreille.ornitho.adapter;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.model.Group;
import com.ornithoaloreille.ornitho.model.GroupStub;

/**
 * Created by Myriam on 2016-10-28.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private static final String TAG = GroupAdapter.class.getSimpleName();
    private Cursor cursor;
    private Resources resources;
    private GroupClickListener clickedListener = null;


    //----------------------------------------------------------------------------------------------
    // LISTENER

    public interface GroupClickListener {
        void groupClicked(GroupStub group);
    }
    public void setClickedListener(GroupClickListener l) {
        clickedListener = l;
    }


    //----------------------------------------------------------------------------------------------
    // VIEW HOLDER
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView groupName;
        private GroupStub group;

        public ViewHolder(CardView itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.card_group_view);
            itemView.setOnClickListener(this);  // Do this cause Cardview has a onClick of its own
        }

        @Override
        public void onClick(View v) {
//            Log.d(TAG, "View holder clicked: " + group.getName());
            if(groupName != null && clickedListener != null) {
//                Log.d(TAG, "In Group Adapter clicked, group is: " + group.toString());
                clickedListener.groupClicked(group);
            }
        }
    }


    //----------------------------------------------------------------------------------------------
    // ADAPTER
    public GroupAdapter(Cursor c, Resources r) {
        cursor = c;
        resources = r;
    }

    private GroupAdapter() {

    }

    public static GroupAdapter getEmptyAdapter() {
        return new GroupAdapter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_group, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        GroupStub group = GroupStub.getGroupStub(cursor);

        TextView name = holder.groupName;
        holder.group = group;

        name.setText(group.getName());
        Drawable icon = group.getIcon();
        icon.setBounds(0, 0, resources.getDimensionPixelSize(R.dimen.large_icon), resources.getDimensionPixelSize(R.dimen.large_icon));
        name.setCompoundDrawables(icon, null, null, null);
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
