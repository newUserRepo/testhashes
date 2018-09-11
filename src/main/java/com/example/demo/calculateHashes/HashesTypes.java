package com.example.demo.calculateHashes;

import java.util.List;

public final class HashesTypes {

    private final List<String> crc32;
    private final List<String> md5AndSha;

    public HashesTypes(final Builder builder) {
        crc32 = builder.crc32;
        md5AndSha = builder.md5AndSha;
    }

    public List<String> getCrc32() {
        return crc32;
    }

    public List<String> getMd5AndSha() {
        return md5AndSha;
    }

    public static class Builder {
        private List<String> crc32;
        private List<String> md5AndSha;

        public Builder setCrc32(final List<String> crc32) {
            this.crc32 = crc32;
            return this;
        }
        public Builder setMd5AndSha(final List<String> md5AndSha) {
            this.md5AndSha = md5AndSha;
            return this;
        }

        public HashesTypes build() {
            return new HashesTypes(this);
        }
    }
}
