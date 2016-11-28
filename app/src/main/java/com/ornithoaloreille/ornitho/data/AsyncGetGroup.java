package com.ornithoaloreille.ornitho.data;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.ornithoaloreille.ornitho.model.Group;
import com.ornithoaloreille.ornitho.model.GroupStub;
import com.ornithoaloreille.ornitho.view.MainActivity;

/**
 * Created by Myriam on 2016-11-05.
 */

public class AsyncGetGroup extends AsyncTask<GroupStub, Void, Group> {
    private MainActivity mainActivity;

    public AsyncGetGroup(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Group doInBackground(GroupStub... groupStub) {
        Cursor subgroupCursor = mainActivity.getContentResolver().query(
                Uri.parse(OrnithoDB.PROVIDER + "groups/" + groupStub[0].getId() + "/subgroups"),
                null,
                null,
                null,
                null
        );

        Group group = Group.getGroup(groupStub[0], subgroupCursor, mainActivity);
        subgroupCursor.close();
        subgroupCursor = null;

        return group;
    }

    @Override
    protected void onPostExecute(Group group) {
        mainActivity.selectItem(group);
    }
}
