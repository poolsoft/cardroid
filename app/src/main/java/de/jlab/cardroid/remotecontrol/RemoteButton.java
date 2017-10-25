package de.jlab.cardroid.remotecontrol;

public class RemoteButton {

    private long id;
    private long remoteId;
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

    public void setRemoteId(long remoteControlId) {
        this.remoteId = remoteControlId;
    }

    public long getRemoteId() {
        return this.remoteId;
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
