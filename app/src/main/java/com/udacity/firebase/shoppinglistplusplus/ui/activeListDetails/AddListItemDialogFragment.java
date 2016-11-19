package com.udacity.firebase.shoppinglistplusplus.ui.activeListDetails;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingListItem;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Lets user add new list item.
 */
public class AddListItemDialogFragment extends EditListDialogFragment {
    EditText mEditTextItemName;

    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when
     * adapter is created
     */
    public static AddListItemDialogFragment newInstance(ShoppingList shoppingList, String listId) {
        AddListItemDialogFragment addListItemDialogFragment = new AddListItemDialogFragment();

        Bundle bundle = newInstanceHelper(shoppingList, R.layout.dialog_add_item, listId);
        addListItemDialogFragment.setArguments(bundle);

        return addListItemDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_add_item, null);
        mEditTextItemName = (EditText) rootView.findViewById(R.id.edit_text_list_dialog);

        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        return super.createDialogHelper(R.string.positive_button_add_list_item);
    }

    /**
     * Adds new item to the current shopping list
     */
    @Override
    protected void doListEdit() {
        // TODO The code here should do the write which adds a new item to the list.
        // You should also update the list's last edit timestamp. You can do both of
        // these actions in one call to the server.
        String itemName = mEditTextItemName.getText().toString();
        if (!itemName.equals("")) {
            Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);
            Firebase itemsRef = new Firebase(Constants.FIREBASE_URL_SHOPPING_LIST_ITEMS).child(mListId);


            /* Make a map for the item you are adding */
            HashMap<String, Object> updatedItemToAddMap = new HashMap<String, Object>();

            /* Save push() to maintain same random Id */
            Firebase newitemsRef = itemsRef.push();
            String itemId = newitemsRef.getKey();

            /* Make a POJO for the item and immediately turn it into a HashMap */
            ShoppingListItem itemToAddObject = new ShoppingListItem(itemName);
            HashMap<String, Object> itemToAdd =
                (HashMap<String, Object>) new ObjectMapper().convertValue(itemToAddObject, Map.class);
            /* Add the item to the update map*/

            updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/"
                + mListId + "/" + itemId, itemToAdd);

                            /* Make the timestamp for last changed */
            HashMap<String, Object> changedTimestampMap = new HashMap<>();
            changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                            /* Add the updated timestamp */
            updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_ACTIVE_LISTS +
                "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

                            /* Do the update */
            firebaseRef.updateChildren(updatedItemToAddMap);

            /* Close the dialog fragment */
            AddListItemDialogFragment.this.getDialog().cancel();
        }

    }
}
