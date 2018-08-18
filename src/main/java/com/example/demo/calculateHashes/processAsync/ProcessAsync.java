package com.example.demo.calculateHashes.processAsync;

import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.RichTextArea;

import java.nio.file.Path;
import java.util.List;

public final class ProcessAsync {
    //final Path path, final ProgressBar progressBar, final Label labelResult, final String hash
    private final Path path;
    private final ProgressBar progressBar;
    private final RichTextArea richTextArea;
    private final List<String> hashes;

    private ProcessAsync(final Builder builder) {
        this.path = builder.path;
        this.progressBar = builder.progressBar;
        this.richTextArea = builder.richTextArea;
        if(builder.hashes == null) {
            throw new RuntimeException("Hashes it`s required");
        }
        this.hashes = builder.hashes;
    }

    public Path getPath() {
        return path;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public RichTextArea getRichTextAreaResult() {
        return richTextArea;
    }

    public List<String> getHashes() {
        return hashes;
    }

    @Override
    public String toString() {
        return "ProcessAsync{" +
                "path=" + path +
                ", progressBar=" + progressBar +
                ", richTextArea=" + richTextArea +
                ", hashes=" + hashes +
                '}';
    }

    public static class Builder {
        private Path path;
        private ProgressBar progressBar;
        private RichTextArea richTextArea;
        private List<String> hashes;

        public Builder setPath(final Path path) {
            this.path = path;
            return this;
        }
        public Builder setProgressBar(final ProgressBar progressBar) {
            this.progressBar = progressBar;
            return this;
        }
        public Builder setLabelResult(final RichTextArea richTextArea) {
            this.richTextArea = richTextArea;
            return this;
        }
        public Builder setHashes(final List<String> hashes) {
            this.hashes = hashes;
            return this;
        }

        public ProcessAsync build() {
            return new ProcessAsync(this);
        }
    }
}
