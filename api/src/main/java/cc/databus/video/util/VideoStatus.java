package cc.databus.video.util;

public enum VideoStatus {
    OK(1),
    FORBIDDEN(2);

    public final int value;

    VideoStatus(int value) {
        this.value = value;
    }
}
