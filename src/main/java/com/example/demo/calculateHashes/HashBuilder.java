package com.example.demo.calculateHashes;

public final class HashBuilder {

    public String filename;
    public String hashtype;
    public String hashresult;
    public String size;
    public String time;
    public String hour;

    public HashBuilder setFileName(final String fileName) {
        this.filename = fileName;
        return this;
    }

    public HashBuilder setHashType(final String hashType) {
        this.hashtype = hashType;
        return this;
    }

    public HashBuilder setHashResult(final String hashResult) {
        this.hashresult = hashResult;
        return this;
    }

    public HashBuilder setSize(final String size) {
        this.size = size;
        return this;
    }

    public HashBuilder setTime(final String time) {
        this.time = time;
        return this;
    }

    public HashBuilder setHour(final String hour) {
        this.hour = hour;
        return this;
    }

    public Hash build() {
        return new Hash(this);
    }
}
