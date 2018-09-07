package com.example.demo.calculateHashes;

public class HashBuilder {

    public String filename;
    public String hashtype;
    public String hashresult;
    public Integer length;
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

    public HashBuilder setLength(final Integer length) {
        this.length = length;
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
