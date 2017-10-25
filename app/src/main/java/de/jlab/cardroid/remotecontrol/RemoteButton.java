package de.jlab.cardroid.remotecontrol;

public class RemoteButton {

    private long id;
    private long remoteControlId;
    private long serialId;
    private String name;
    private String action;

    public void setName(String name) {
        this.name = name;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSerialId(long serialId) {
        this.serialId = serialId;
    }

    public void setRemoteControlId(long remoteControlId) {
        this.remoteControlId = remoteControlId;
    }

    public long getRemoteControlId() {
        return this.remoteControlId;
    }

    public long getSerialId() {
        return this.serialId;
    }

    public String getName() {
        return this.name;
    }

    public String getAction() {
        return this.action;
    }

}
