package com.gpeal.kitchensink;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class ItemListActivity extends FragmentActivity
        implements ItemListFragment.Callbacks {

    public static final Class[] FRAGMENT_CLASSES = {
            SpiralMenuFragment.class
    };
    public static final String EXTRA_FRAGMENT_CLASS_INDEX = "fragment class index";

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;

            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(int position) {

        if (mTwoPane) {
            Fragment fragment;
            try {
                fragment = (Fragment) FRAGMENT_CLASSES[position].newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemListActivity.EXTRA_FRAGMENT_CLASS_INDEX, position);
            startActivity(detailIntent);
        }
    }

    @Override
    public String[] getListItems() {
        String[] items = new String[FRAGMENT_CLASSES.length];
        for (int i = 0; i < FRAGMENT_CLASSES.length; i++) {
            items[i] = FRAGMENT_CLASSES[i].getSimpleName();
        }
        return items;
    }
}
