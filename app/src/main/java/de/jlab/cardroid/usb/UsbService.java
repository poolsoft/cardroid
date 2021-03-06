package de.jlab.cardroid.usb;

import android.app.Service;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Binder;

import java.util.Objects;

public abstract class UsbService extends Service {

    public class UsbServiceBinder extends Binder {
        public boolean isConnected() {
            return UsbService.this.isConnected();
        }

        public void disconnect() {
            UsbService.this.disconnectDevice();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized (this) {
            if (intent != null) {
                if (Objects.equals(UsbManager.ACTION_USB_DEVICE_ATTACHED, intent.getAction())) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (!this.connectDevice(device)) {
                        stopSelf();
                        return START_NOT_STICKY;
                    }
                    return START_STICKY;
                }
                if (Objects.equals(UsbManager.ACTION_USB_DEVICE_DETACHED, intent.getAction())) {
                    this.disconnectDevice();
                    stopSelf();
                    return START_NOT_STICKY;
                }
            }
        }

        return this.isConnected() ? START_STICKY : START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disconnectDevice();
    }

    protected abstract boolean isConnected();
    protected abstract boolean connectDevice(UsbDevice device);
    protected abstract void disconnectDevice();

}
