package de.jlab.cardroid.car;

import java.lang.*;

import de.jlab.cardroid.usb.CarSystemSerialPacket;

public enum CarSystemFactory {
    CLIMATE_CONTROL(0x63, ClimateControl.class),
    GEAR_BOX(0x67, GearBox.class),
    REMOTE_CONTROL(0x72, RemoteControl.class);

    private byte identifier;
    private Class<? extends CarSystem> carSystemClass;

    CarSystemFactory(int identifier, Class<? extends CarSystem> carSystemClass) {
        this.identifier = (byte)identifier;
        this.carSystemClass   = carSystemClass;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public static CarSystemFactory getType(CarSystemSerialPacket packet) throws UnknownCarSystemException {
        byte identifier = packet.getId();
        for (CarSystemFactory carSystemType : CarSystemFactory.values()) {
            if (identifier == carSystemType.identifier) {
                return carSystemType;
            }
        }

        throw new UnknownCarSystemException(identifier);
    }

    public static CarSystemFactory getType(CarSystem carSystem) throws UnknownCarSystemException {
        for (CarSystemFactory carSystemType : CarSystemFactory.values()) {
            if (carSystem.getClass() == carSystemType.carSystemClass) {
                return carSystemType;
            }
        }

        throw new UnknownCarSystemException(carSystem);
    }

    public static CarSystem getCarSystem(CarSystemFactory systemType) {
        Class<? extends CarSystem> carSystemClass = systemType.carSystemClass;
        try {
            return carSystemClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            throw new InstantiationException("Cannot create instance of " + carSystemClass.getSimpleName(), e);
        } catch (IllegalAccessException e) {
            throw new InstantiationException("Cannot access " + carSystemClass.getSimpleName(), e);
        }
    }
}
