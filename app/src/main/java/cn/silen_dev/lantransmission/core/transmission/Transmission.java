package cn.silen_dev.lantransmission.core.transmission;

public class Transmission {
    static int PICTURE=1; //图片类型
    static int VEDIO=2;     //视频类型
    static int FILE=3;      //文件类型
    static int DEVICE=4;    //设备类型

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

    public Transmission(int ty,String fn,String m,long len,long t,int id,String save,String send,int sta,int s)
    {
        this.type=ty;
        this.fileName=fn;
        this.message=m;
        this.length=len;
        this.time=t;
        this.userId=id;
        this.savePath=save;
        this.sendPath=send;
        this.status=sta;
        this.sr=s;
    }

    public Transmission(String name,int id,int s)
    {
        this.fileName=name;
        this.status=s;
        this.userId=id;
    }
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

    public void setSr(int sr) {this.sr = sr;
    }
}
