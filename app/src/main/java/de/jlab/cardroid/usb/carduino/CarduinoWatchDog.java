package de.jlab.cardroid.usb.carduino;

import android.content.Context;
import android.hardware.usb.UsbDevice;

import de.jlab.cardroid.usb.UsbService;
import de.jlab.cardroid.usb.UsbWatchDog;

public class CarduinoWatchDog extends UsbWatchDog {
    public CarduinoWatchDog(Context context) {
        super(context);
    }

    @Override
    protected boolean shouldWatchDevice(UsbDevice device) {
        return device.getVendorId() != 0x067B && device.getProductId() != 0x2303 || device.getVendorId() == 0x16C0 && device.getProductId() == 0x0487;
    }

    @Override
    protected Class<? extends UsbService> getServiceClass() {
        return CarduinoService.class;
    }
}
