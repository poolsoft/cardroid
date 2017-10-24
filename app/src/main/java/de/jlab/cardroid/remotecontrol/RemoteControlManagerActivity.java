package de.jlab.cardroid.remotecontrol;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import de.jlab.cardroid.R;
import de.jlab.cardroid.storage.CarDroidDataContract;
import de.jlab.cardroid.storage.CarDroidDataOpenHelper;

public class RemoteControlManagerActivity extends AppCompatActivity {

    private RemoteControlCursorAdapter remoteControlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control_manager);

        CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(this);

        dataHelper.createOrUpdateRemoteControl("Steering wheel remote", 128);
        dataHelper.createOrUpdateRemoteButton("Volume up", 0, 128);
        dataHelper.createOrUpdateRemoteButton("Volume down", 1, 128);
        dataHelper.createOrUpdateRemoteButton("Previous", 2, 128);
        dataHelper.createOrUpdateRemoteButton("Next", 3, 128);
        dataHelper.createOrUpdateRemoteButton("Play/Pause", 4, 128);
        dataHelper.createOrUpdateRemoteButton("Voice", 5, 128);
        dataHelper.createOrUpdateRemoteButton("SRC", 6, 128);

        Cursor testCursor = dataHelper.listRemoteControls();


        final ExpandableListView remoteControlList = (ExpandableListView) findViewById(R.id.remote_control_list);
        this.remoteControlAdapter = new RemoteControlCursorAdapter(dataHelper.listRemoteControls(), this);
        remoteControlList.setAdapter(this.remoteControlAdapter);

        this.remoteControlAdapter.setSecondaryItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = remoteControlList.getPositionForView(v);
                Cursor cursor = RemoteControlManagerActivity.this.remoteControlAdapter.getCursor();
                cursor.moveToPosition(position);
                long id = cursor.getLong(cursor.getColumnIndex(CarDroidDataContract.RemoteControl._ID));

                CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(RemoteControlManagerActivity.this);
                dataHelper.deleteRemoteControl(id);
                RemoteControlManagerActivity.this.remoteControlAdapter.notifyDataSetChanged();
            }
        });

        remoteControlList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO show fragment with buttons
            }
        });
    }



}
