package com.example.demo.calculateHashes;

public final class Hash {

    private final String filename;
    private final String hashtype;
    private final String hashresult;
    private final Integer length;
    private final String time;
    private final String hour;

    public Hash(final HashBuilder builder) {
        this.filename = builder.filename;
        this.hashtype = builder.hashtype;
        this.hashresult = builder.hashresult;
        this.length = builder.length;
        this.time = builder.time;
        this.hour = builder.hour;
    }

    public String getFilename() {
        return filename;
    }

   // public void setFilename(String filename) { this.filename = filename;}

    public String getHashtype() {
        return hashtype;
    }

   // public void setHashtype(String hashtype) {this.hashtype = hashtype;}

    public String getHashresult() {
        return hashresult;
    }

   // public void setHashresult(String hashresult) {this.hashresult = hashresult;}

    public Integer getLength() {
        return length;
    }

    //public void setLength(Integer length) {this.length = length;}

    public String getTime() {
        return time;
    }

    // public void setTime(String time) {this.time = time;}

    public String getHour() {
        return hour;
    }

   //public void setHour(String hour) {this.hour = hour;}

}
