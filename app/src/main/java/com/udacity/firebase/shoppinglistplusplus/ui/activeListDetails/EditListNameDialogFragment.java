package com.udacity.firebase.shoppinglistplusplus.ui.activeListDetails;

import android.app.Dialog;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.HashMap;

import static com.udacity.firebase.shoppinglistplusplus.utils.Constants.FIREBASE_PROPERTY_LIST_NAME;
import static com.udacity.firebase.shoppinglistplusplus.utils.Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED;
import static com.udacity.firebase.shoppinglistplusplus.utils.Constants.KEY_LIST_NAME;

/**
 * Lets user edit the list name for all copies of the current list
 */
public class EditListNameDialogFragment extends EditListDialogFragment {
    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    public String shoppingListName;
    private Firebase shoppingListRef;

    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when
     * adapter is created
     */
    public static EditListNameDialogFragment newInstance(ShoppingList shoppingList) {
        EditListNameDialogFragment editListNameDialogFragment = new EditListNameDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList, R.layout.dialog_edit_list);
        // TODO add any values you need here from the shopping list to make this change.
        // Once you put a value in the bundle, it available to you in onCreate
        bundle.putString(FIREBASE_PROPERTY_LIST_NAME, shoppingList.getListName());
        editListNameDialogFragment.setArguments(bundle);
        return editListNameDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Extract any arguments you put in the bundle when the newInstance method
        // created the dialog. You can store these in an instance variable so that they
        // are available to you.
        //shoppingListName = savedInstanceState.getString(KEY_LIST_NAME);
        shoppingListName = getArguments().getString(FIREBASE_PROPERTY_LIST_NAME);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        Dialog dialog = super.createDialogHelper(R.string.positive_button_edit_item);
        // TODO You can use the helper method in the superclass I made (EditListDialogFragment)
        // called helpSetDefaultValueEditText. This will allow you to set what text the
        // user sees when the dialog opens.
        helpSetDefaultValueEditText(shoppingListName);
        return dialog;
    }

    /**
     * Changes the list name in all copies of the current list
     */
    protected void doListEdit() {
        // TODO Do the actual edit operation here.
        // Remember, you need to update the timestampLastChanged for
        // the shopping list.
        final String inputListName = mEditTextForList.getText().toString();
        if (!inputListName.equals("")) {

            if (shoppingListName != null) {
                if (!inputListName.equals(shoppingListName)) {

                    shoppingListRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);
                    HashMap<String, Object> activeListUpdate = new HashMap<>();
                    activeListUpdate.put(FIREBASE_PROPERTY_LIST_NAME, inputListName);


                    HashMap<String, Object> changedTimestampMap = new HashMap<>();
                    changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    activeListUpdate.put(FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);
                    shoppingListRef.updateChildren(activeListUpdate);
                }
            }
        }
    }
}

