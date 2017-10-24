package de.jlab.cardroid.car;

import de.jlab.cardroid.usb.CarSystemSerialPacket;

public class RemoteControl extends CarSystem {
    private int controlId;
    private int buttonId;

    @Override
    protected void updateDataFromPacket(CarSystemSerialPacket packet) {
        this.controlId = packet.readUnsignedByte(0);
        this.buttonId = packet.readUnsignedByte(0);
    }

    public int getControlId() {
        return this.controlId;
    }
    public int getButtonId() {
        return this.buttonId;
    }
}
