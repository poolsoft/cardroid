package de.jlab.cardroid.remotecontrol;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import de.jlab.cardroid.R;
import de.jlab.cardroid.remotecontrol.actions.Actions;
import de.jlab.cardroid.storage.CarDroidDataOpenHelper;

public class RemoteButtonEditDialog {

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

    public static void showDialog(final RemoteButton button, final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_remote_button_edit, null);

        final EditText nameField = (EditText)view.findViewById(R.id.button_name);
        final Spinner actionSpinner = (Spinner)view.findViewById(R.id.button_action);

        nameField.setText(button.getName());

        List<String> actionsIdentifiers = Actions.getIdentifierList();
        final List<ActionItem> actions = new ArrayList<>();
        for (String actionIdentifier : actionsIdentifiers) {
            actions.add(new ActionItem(actionIdentifier, context));
        }
        ArrayAdapter<ActionItem> actionAdapter = new ArrayAdapter<ActionItem>(context, android.R.layout.simple_spinner_item, actions);
        actionSpinner.setAdapter(actionAdapter);

        dialogBuilder.setView(view);
        dialogBuilder.setTitle(context.getString(R.string.remote_button_edit_title, button.getRemoteControlId(), button.getSerialId()));
        dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(context);
                ActionItem action = (ActionItem)actionSpinner.getSelectedItem();
                dataHelper.createOrUpdateRemoteButton(nameField.getText().toString(), action.identifier, button.getSerialId(), button.getRemoteControlId());
            }
        });
        dialogBuilder.setNegativeButton(android.R.string.cancel, null);
        dialogBuilder.show();
    }

}
