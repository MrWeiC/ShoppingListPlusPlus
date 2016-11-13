package com.udacity.firebase.shoppinglistplusplus.ui.activeLists;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;

/**
 * Populates the list_view_active_lists inside ShoppingListsFragment
 */
public class ActiveListAdapter extends FirebaseListAdapter<ShoppingList> {
    private TextView mTextViewListName, mTextViewListOwner;
    private TextView mTextViewEditTime;

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */
    public ActiveListAdapter(Activity activity, Class<ShoppingList> modelClass, int modelLayout,
                             Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    /**
     * Protected method that populates the view attached to the adapter (list_view_active_lists)
     * with items inflated from single_active_list.xml populateView also handles data changes and
     * updates the listView accordingly
     */
    @Override
    protected void populateView(View view, ShoppingList list) {
        // TODO This is where you need to populate the single_active_list layout with
        // the data in the current shopping list. It should be similar to what you
        // were displaying in ShoppingListsFragment
        mTextViewListName = (TextView) view.findViewById(R.id.text_view_list_name);
        mTextViewListOwner = (TextView) view.findViewById(R.id.text_view_created_by_user);
        //mTextViewEditTime = (TextView) view.findViewById(R.id.text_view_edit_time);
        // If there was data, take the TextViews and set the appropriate values.
        mTextViewListName.setText(list.getListName());
        mTextViewListOwner.setText(list.getOwner());

    }

}
