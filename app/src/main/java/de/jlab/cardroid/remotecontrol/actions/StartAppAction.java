package de.jlab.cardroid.remotecontrol.actions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.HashMap;

import de.jlab.cardroid.R;

public class StartAppAction implements Action {
    private String packageName;

    @Override
    public void init(HashMap<String, String> propertyMap) {
        this.packageName = propertyMap.get("start_app_package");
    }

    @Override
    public boolean execute(Context context) throws ActionException {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            throw new ActionException(context.getString(R.string.action_exception_app_not_found, packageName));
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }

    public ActionSettings createActionSettings(Context context) {
        return null;
    }
}
