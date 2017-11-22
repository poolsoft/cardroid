package de.jlab.cardroid.remotecontrol;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.jlab.cardroid.R;
import de.jlab.cardroid.remotecontrol.actions.Action;
import de.jlab.cardroid.remotecontrol.actions.Actions;
import de.jlab.cardroid.storage.CarDroidDataOpenHelper;

public class RemoteButtonEditDialog {

    private static final String LOG_TAG = "RemoteButtonEditDialog";

    private Context context;
    private ViewGroup actionSettings;

    public RemoteButtonEditDialog(Context context) {
        this.context = context;
    }

    private static class ActionItem {
        private String label;
        public String identifier;

        public ActionItem(String identifier, Context context) {
            int resourceId = context.getResources().getIdentifier("action_type_" + identifier, "string", context.getPackageName());
            this.label = resourceId != 0 ? context.getString(resourceId) : identifier;
            this.identifier = identifier;
        }

        public String toString() {
            return this.label;
        }
    }

    private void showActionSettings(String identifier, HashMap<String, String> propertyMap) {
        Action action = null;
        try {
            action = Actions.createAction(identifier, propertyMap, this.context);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Cannot create action " + identifier, e);
        }

        if (action != null) {

        }
        else {
            // TODO clear view
        }
    }

    public void show(final RemoteButton button) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);
        final View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_remote_button_edit, null);

        final EditText nameField = (EditText)view.findViewById(R.id.button_name);
        final Spinner actionSpinner = (Spinner)view.findViewById(R.id.button_action);
        this.actionSettings = (ViewGroup)view.findViewById(R.id.action_settings);

        nameField.setText(button.getName());

        List<String> actionsIdentifiers = Actions.getIdentifierList();
        final List<ActionItem> actions = new ArrayList<>();
        ActionItem selection = new ActionItem(null, this.context);
        actions.add(selection);
        for (String actionIdentifier : actionsIdentifiers) {
            ActionItem item = new ActionItem(actionIdentifier, this.context);
            actions.add(item);
            if (actionIdentifier.equals(button.getAction())) {
                selection = item;
            }
        }
        final ArrayAdapter<ActionItem> actionAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_dropdown_item_1line, actions);
        actionSpinner.setAdapter(actionAdapter);

        actionSpinner.setSelection(actionAdapter.getPosition(selection));
        actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ActionItem item = actionAdapter.getItem(position);
                if (item != null) {
                    CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(RemoteButtonEditDialog.this.context);
                    HashMap<String, String> propertyMap = dataHelper.getButtonActionProperties(button.getSerialId(), button.getRemoteId());
                    showActionSettings(item.identifier, propertyMap);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogBuilder.setView(view);
        dialogBuilder.setTitle(context.getString(R.string.remote_button_edit_title, button.getRemoteId(), button.getSerialId()));
        dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(context);
                ActionItem action = (ActionItem)actionSpinner.getSelectedItem();
                dataHelper.createOrUpdateRemoteButton(nameField.getText().toString(), action.identifier, button.getSerialId(), button.getRemoteId());
            }
        });
        dialogBuilder.setNegativeButton(android.R.string.cancel, null);
        dialogBuilder.show();
    }

}
