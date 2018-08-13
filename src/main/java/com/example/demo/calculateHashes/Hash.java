package com.example.demo.calculateHashes;

public class Hash {

    private String filename;
    private String hashtype;
    private String hashresult;
    private Integer length;
    private String time;
    private String hour;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHashtype() {
        return hashtype;
    }

    public void setHashtype(String hashtype) {
        this.hashtype = hashtype;
    }

    public String getHashresult() {
        return hashresult;
    }

    public void setHashresult(String hashresult) {
        this.hashresult = hashresult;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

}
