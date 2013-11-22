package com.gpeal.kitchensink;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private Class[] mFragmentClasses = {
            SpiralMenuFragment.class
    };
    private int mIndex;

    public ItemDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndex = -1;
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            String fragmentName = getArguments().getString(ARG_ITEM_ID);
            for (int i = 0; i < mFragmentClasses.length; i++) {
                if (mFragmentClasses[i].toString().equals(fragmentName)) {
                    mIndex = i;
                }
            }
            if (mIndex == -1) {
                throw new RuntimeException("Couldn't find fragment for " + fragmentName);
            }
        } else {
            throw new RuntimeException("Arguments must contain ARG_ITEM_ID");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        return rootView;
    }
}
