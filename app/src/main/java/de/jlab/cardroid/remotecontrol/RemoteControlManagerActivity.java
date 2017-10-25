package de.jlab.cardroid.remotecontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import de.jlab.cardroid.R;
import de.jlab.cardroid.storage.CarDroidDataOpenHelper;
import de.jlab.cardroid.storage.CursorEntityFactory;

public class RemoteControlManagerActivity extends AppCompatActivity {

    private RemoteControlCursorAdapter remoteControlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control_manager);

        CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(this);

        dataHelper.createOrUpdateRemoteControl("Steering wheel remote", 128);
        dataHelper.createOrUpdateRemoteButton("Volume up", null, 0, 128);
        dataHelper.createOrUpdateRemoteButton("Volume down", null, 1, 128);
        dataHelper.createOrUpdateRemoteButton("Previous", null, 2, 128);
        dataHelper.createOrUpdateRemoteButton("Next", null, 3, 128);
        dataHelper.createOrUpdateRemoteButton("Play/Pause", null, 4, 128);
        dataHelper.createOrUpdateRemoteButton("Voice", null, 5, 128);
        dataHelper.createOrUpdateRemoteButton("SRC", null, 6, 128);

        final ExpandableListView remoteControlList = (ExpandableListView) findViewById(R.id.remote_control_list);
        this.remoteControlAdapter = new RemoteControlCursorAdapter(dataHelper.listRemoteControls(), this);
        remoteControlList.setAdapter(this.remoteControlAdapter);

        this.remoteControlAdapter.setSecondaryItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pos = remoteControlList.getExpandableListPosition(remoteControlList.getPositionForView(v));
                long remoteControlId = remoteControlAdapter.getGroupId(
                        ExpandableListView.getPackedPositionGroup(pos)
                );

                CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(RemoteControlManagerActivity.this);
                dataHelper.deleteRemoteControl(remoteControlId);
                RemoteControlManagerActivity.this.remoteControlAdapter.notifyDataSetChanged();
            }
        });

        this.remoteControlAdapter.setSecondaryChildClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pos = remoteControlList.getExpandableListPosition(remoteControlList.getPositionForView(v));
                long remoteButtonId = remoteControlAdapter.getChildId(
                        ExpandableListView.getPackedPositionGroup(pos),
                        ExpandableListView.getPackedPositionChild(pos)
                );

                CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(RemoteControlManagerActivity.this);
                dataHelper.deleteRemoteButton(remoteButtonId);
                RemoteControlManagerActivity.this.remoteControlAdapter.notifyDataSetChanged();
            }
        });

        remoteControlList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CursorEntityFactory<RemoteButton> buttonFactory = new CursorEntityFactory<RemoteButton>(RemoteButton.class);
                RemoteButton button = buttonFactory.createEntity(remoteControlAdapter.getChildrenCursor(remoteControlAdapter.getCursor()));
                long remoteControlId = remoteControlAdapter.getGroupId(groupPosition);
                RemoteButtonEditDialog.showDialog(button, RemoteControlManagerActivity.this);
                return true;
            }
        });
    }



}
