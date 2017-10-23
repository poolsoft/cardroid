package de.jlab.cardroid.remotecontrol;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.jlab.cardroid.R;
import de.jlab.cardroid.storage.CarDroidDataContract;
import de.jlab.cardroid.storage.CarDroidDataOpenHelper;

public class RemoteControlCursorAdapter extends CursorTreeAdapter {

    private Context context;
    private View.OnClickListener mSecondaryClickListener;

    static class ViewHolder {
        TextView name;
        TextView details;
        ImageView delete;
    }

    public RemoteControlCursorAdapter(Cursor cursor, Context context) {
        super(cursor, context);
        this.context = context;
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        long remoteControlId = groupCursor.getLong(groupCursor.getColumnIndex(CarDroidDataContract.RemoteControl._ID));

        CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(this.context);
        final SQLiteDatabase db = dataHelper.getReadableDatabase();
        return db.query(CarDroidDataContract.RemoteControl.TABLE_NAME, new String[]{CarDroidDataContract.RemoteControl._ID, CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME}, null, new String[0], null, null, CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME);
    }

    @Override
    protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_listitem_remote_control, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.details = (TextView) view.findViewById(R.id.details);
        holder.delete = (ImageView) view.findViewById(R.id.delete);

        holder.delete.setOnClickListener(mSecondaryClickListener);
        view.setTag(holder);
        return view;
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
        // Get ViewHolder from view to avoid findViewById calls!
        ViewHolder holder = (ViewHolder)view.getTag();
        // Extract properties from cursor
        holder.name.setText(cursor.getString(cursor.getColumnIndexOrThrow(CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME)));
        holder.details.setText(cursor.getString(cursor.getColumnIndexOrThrow(CarDroidDataContract.RemoteControl._ID)));
    }

    @Override
    protected View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent) {
        return null;
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {

    }

    public void setSecondaryItemClickListener(View.OnClickListener secondaryClickListener) {
        mSecondaryClickListener = secondaryClickListener;
    }

    /*
    private View.OnClickListener mSecondaryClickListener;

    public RemoteControlCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_listitem_remote_control, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.address = (TextView) view.findViewById(R.id.address);
        holder.delete = (ImageView) view.findViewById(R.id.delete);

        holder.delete.setOnClickListener(mSecondaryClickListener);
        view.setTag(holder);
        return view;
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get ViewHolder from view to avoid findViewById calls!
        ViewHolder holder = (ViewHolder)view.getTag();
        // Extract properties from cursor
        holder.name.setText(cursor.getString(cursor.getColumnIndexOrThrow(CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME)));
        holder.address.setText(cursor.getString(cursor.getColumnIndexOrThrow(CarDroidDataContract.RemoteControl.COLUMN_NAME_SERIAL_ID)));
    }

    public void setSecondaryItemClickListener(View.OnClickListener secondaryClickListener) {
        mSecondaryClickListener = secondaryClickListener;
    }
    */
}
