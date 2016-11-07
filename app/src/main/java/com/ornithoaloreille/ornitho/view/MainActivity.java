package com.ornithoaloreille.ornitho.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ornithoaloreille.ornitho.R;
import com.ornithoaloreille.ornitho.adapter.DrawerEntryAdapter;
import com.ornithoaloreille.ornitho.adapter.GroupAdapter;
import com.ornithoaloreille.ornitho.adapter.SpeciesAdapter;
import com.ornithoaloreille.ornitho.data.AsyncGetGroup;
import com.ornithoaloreille.ornitho.data.AsyncGetSpecies;
import com.ornithoaloreille.ornitho.model.DrawerEntry;
import com.ornithoaloreille.ornitho.model.Group;
import com.ornithoaloreille.ornitho.model.GroupStub;
import com.ornithoaloreille.ornitho.model.Species;
import com.ornithoaloreille.ornitho.model.SpeciesStub;

public class MainActivity extends Activity implements
        DrawerEntryAdapter.DrawerClickListener,
        GroupAdapter.GroupClickListener,
        SpeciesAdapter.SpeciesClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private RecyclerView drawer;
    private ActionBarDrawerToggle drawerToggle;

    // To restore state
    private int where;
    private Group group;
    private Species species;

    private static final int GROUP_FRAGMENT = 3;
    private static final int SPECIES_FRAGMENT = 4;
    private static final int QUIZ_FRAGMENT = 5;


    //----------------------------------------------------------------------------------------------
    // LISTENERS
    @Override
    public void drawerClicked(DrawerEntry entry) {
//        Log.d(TAG, "Drawer entry clicked: " + entry.getName());
        selectItem(entry.getId());
    }

    @Override
    public void groupClicked(GroupStub group) {
//        Log.d(TAG, "Group clicked got into MainActivity: " + group.toString());
        AsyncGetGroup asyncGetGroup = new AsyncGetGroup(this);
        asyncGetGroup.execute(group);
    }

    @Override
    public void speciesClicked(SpeciesStub species) {
        Log.d(TAG, "Species clicked got into MainActivity: " + species.toString());
        AsyncGetSpecies asyncGetSpecies = new AsyncGetSpecies(this);
        asyncGetSpecies.execute(species);
    }


    //----------------------------------------------------------------------------------------------
    // ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set static resource reference on model classes
        DrawerEntry.setResources(getResources());
        Group.setResources(getResources());

        // do all the pesky find view by id
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer = (RecyclerView) findViewById(R.id.drawer);

        // Nav Drawer
        DrawerEntryAdapter adapter = new DrawerEntryAdapter(DrawerEntry.entries, getResources());
        drawer.setAdapter(adapter);
        drawer.setLayoutManager(new LinearLayoutManager(this));
        adapter.setListener(this);

        // Open/close drawer button
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
        // Pad the app icon
        ImageView v = (ImageView) findViewById(android.R.id.home);
        v.setPadding(15, 0, 0, 0);

        // restore state
        if(savedInstanceState == null) {
            selectItem(0);
        } else {
            selectItem(savedInstanceState.getInt("where"), savedInstanceState);
        }
    }


    //----------------------------------------------------------------------------------------------
    // ACTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                //TODO make a settings activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //----------------------------------------------------------------------------------------------
    // NAV DRAWER
    private void switchFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment).
                addToBackStack(null).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                commit();
    }

    private void selectItem(int position) {
        where = position;
        String actionBarTitle;
        Fragment fragment;
        switch (position) {
            case 0:
                GroupListFragment fg = new GroupListFragment();
                fg.setContext(this);
                actionBarTitle = fg.TITLE;
                fragment = fg;
                break;
            case 1:
                SpeciesListFragment fs = new SpeciesListFragment();
                fs.setContext(this);
                actionBarTitle = fs.TITLE;
                fragment = fs;
                break;
            case 2:
            default:
                QuizListFragment fq = new QuizListFragment();
                fq.setContext(this);
                actionBarTitle = fq.TITLE;
                fragment = fq;
                break;
        }

        if(getActionBar() != null)
            getActionBar().setTitle(actionBarTitle);

        switchFragment(fragment);

        drawerLayout.closeDrawer(drawer);
    }

    private void selectItem(int position, Bundle bundle) {
        where = position;
        String actionBarTitle;
        Fragment fragment;
        switch (position) {
            case GROUP_FRAGMENT:
                group = bundle.getParcelable("group");
                GroupFragment gf = new GroupFragment();
                gf.setGroup(group);
                gf.setContext(this);
                actionBarTitle = group.getName();
                fragment = gf;
                break;
            case SPECIES_FRAGMENT:
            default:
                species = bundle.getParcelable("species");
                SpeciesFragment sf = new SpeciesFragment();
                sf.setSpecies(species);
                actionBarTitle = species.getName();
                fragment = sf;
                break;
        }

        if(getActionBar() != null)
            getActionBar().setTitle(actionBarTitle);

        switchFragment(fragment);
    }

    public void selectItem(Group group) {
        where = GROUP_FRAGMENT;
        this.group = group;
        GroupFragment groupFragment = new GroupFragment();
        groupFragment.setGroup(group);
        groupFragment.setContext(this);

        if(getActionBar() != null)
            getActionBar().setTitle(group.getName());

        switchFragment(groupFragment);
    }

    public void selectItem(Species species) {
//        Log.d(TAG, "Selecting " + species.toString());
        where = SPECIES_FRAGMENT;
        this.species = species;
        SpeciesFragment speciesFragment = new SpeciesFragment();
        speciesFragment.setSpecies(species);

        if(getActionBar() != null)
            getActionBar().setTitle(species.getName());

        switchFragment(speciesFragment);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean drawerOpened = drawerLayout.isDrawerOpen(drawer);
        // Modify visibility of action bar icons according to drawer opened or closed
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    //----------------------------------------------------------------------------------------------
    // ACTIVITY METHODS
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("where", where);
        switch (where) {
            case GROUP_FRAGMENT:
                savedInstanceState.putParcelable("group", group);
                break;
            case SPECIES_FRAGMENT:
                savedInstanceState.putParcelable("species", species);
                break;
        }
    }
}
