package cn.silen_dev.lantransmission.core.transmission;

public class Transmission {
    private int type;
    private String fileName;
    private String message;
    private long length;
    private long time;
    private int userId;
    private String savePath;
    private String sendPath;
    private int status;
    private int sr;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSendPath() {
        return sendPath;
    }

    public void setSendPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }
}
