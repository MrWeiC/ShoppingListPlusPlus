package com.udacity.firebase.shoppinglistplusplus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.HashMap;

/**
 * Created by weichen on 10/29/16.
 */

public class ShoppingList {
    String listName;
    String owner;
    private HashMap<String, Object> timestampLastChanged;

    public ShoppingList() {
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    public String getListName() {
        return listName;
    }

    public String getOwner() {
        return owner;
    }

    public HashMap<String, Object> getDateCreated() {
        //If there is a dateCreated object already, then return that
        if (timestampLastChanged != null) {
            return timestampLastChanged;
        }
        //Otherwise make a new object set to ServerValue.TIMESTAMP
        HashMap<String, Object> timestampLastChangedObj = new HashMap<String, Object>();
        timestampLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        return timestampLastChangedObj;
    }

    public ShoppingList(String listName, String owner) {
        this.listName = listName;
        this.owner = owner;
        this.timestampLastChanged = this.getDateCreated();
    }

    // Use the method described in http://stackoverflow.com/questions/25500138/android-chat-crashes-on-datasnapshot-getvalue-for-timestamp/25512747#25512747
// to get the long values from the date object.
    @JsonIgnore
    public long getTimestampLastChangedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }
}
