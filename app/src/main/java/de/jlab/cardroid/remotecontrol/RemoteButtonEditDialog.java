package de.jlab.cardroid.remotecontrol;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
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
        ActionItem selection = new ActionItem(null, context);
        actions.add(selection);
        for (String actionIdentifier : actionsIdentifiers) {
            ActionItem item = new ActionItem(actionIdentifier, context);
            actions.add(item);
            if (actionIdentifier.equals(button.getAction())) {
                selection = item;
            }
        }
        ArrayAdapter<ActionItem> actionAdapter = new ArrayAdapter<ActionItem>(context, android.R.layout.simple_dropdown_item_1line, actions);
        actionSpinner.setAdapter(actionAdapter);

        actionSpinner.setSelection(actionAdapter.getPosition(selection));

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
