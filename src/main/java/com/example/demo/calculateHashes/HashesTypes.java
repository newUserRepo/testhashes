package com.example.demo.calculateHashes;

import java.util.List;

public final class HashesTypes {

    private final List<String> crc32;
    private final List<String> md5AndSha;
    private final List<String> adler32;
    private final List<String> haval256;

    public HashesTypes(final Builder builder) {
        crc32 = builder.crc32;
        md5AndSha = builder.md5AndSha;
        adler32 = builder.adler32;
        haval256 = builder.haval256;
    }

    public List<String> getCrc32() {
        return crc32;
    }

    public List<String> getMd5AndSha() {
        return md5AndSha;
    }

    public List<String> getAdler32() { return adler32;}

    public List<String> getHaval256() { return haval256;}

    public static class Builder {
        private List<String> crc32;
        private List<String> md5AndSha;
        private List<String> adler32;
        private List<String> haval256;

        public Builder setCrc32(final List<String> crc32) {
            this.crc32 = crc32;
            return this;
        }
        public Builder setMd5AndSha(final List<String> md5AndSha) {
            this.md5AndSha = md5AndSha;
            return this;
        }
        public Builder setAdler32(final List<String> adler32) {
            this.adler32 = adler32;
            return this;
        }
        public Builder setHaval256(final List<String> haval256) {
            this.haval256 = haval256;
            return this;
        }
        public HashesTypes build() {
            return new HashesTypes(this);
        }
    }
}
