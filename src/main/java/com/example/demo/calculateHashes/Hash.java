package com.example.demo.calculateHashes;

public final class Hash {

    private final String filename;
    private final String hashtype;
    private final String hashresult;
    private final String size;
    private final String time;
    private final String hour;

    public Hash(final HashBuilder builder) {
        this.filename = builder.filename;
        this.hashtype = builder.hashtype;
        this.hashresult = builder.hashresult;
        this.size = builder.size;
        this.time = builder.time;
        this.hour = builder.hour;
    }

    public String getFilename() {
        return filename;
    }

    public String getHashtype() {
        return hashtype;
    }

    public String getHashresult() {
        return hashresult;
    }

    public String getSize() { return size; }

    public String getTime() {
        return time;
    }

    public String getHour() {
        return hour;
    }

}
