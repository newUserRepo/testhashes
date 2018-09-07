package com.example.demo.calculateHashes.processAsync;

import com.example.demo.calculateHashes.createGridTransactions.GridLogic;
import com.example.demo.util.TimeCount;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.RichTextArea;

import java.nio.file.Path;
import java.util.List;

public final class ProcessAsync {
    //final Path path, final ProgressBar bar, final Label labelResult, final String hash
    private final Path path;
    private final ProgressBar bar;
    private final RichTextArea richTextArea;
    private final List<String> hashes;
    private final TimeCount timeCount;
    private GridLogic gridLogic;

    private ProcessAsync(final Builder builder) {
        this.path = builder.path;
        this.bar = builder.progressBar;
        this.richTextArea = builder.richTextArea;
        this.timeCount = builder.timeCount;
        this.gridLogic = builder.gridLogic;
        if(builder.hashes == null) {
            throw new RuntimeException("Hashes it`s required");
        }
        this.hashes = builder.hashes;
    }

    public Path getPath() {
        return path;
    }

    public ProgressBar getBar() {
        return bar;
    }

    public RichTextArea getRichTextAreaResult() {
        return richTextArea;
    }

    public List<String> getHashes() {
        return hashes;
    }

    public TimeCount getTimeCount() {return timeCount;}

    public GridLogic getGridLogic() {
        return gridLogic;
    }
    @Override
    public String toString() {
        return "ProcessAsync{" +
                "path=" + path +
                ", bar=" + bar +
                ", richTextArea=" + richTextArea +
                ", hashes=" + hashes +
                ", grid = "  + gridLogic +
                '}';
    }

    public static class Builder {
        private Path path;
        private ProgressBar progressBar;
        private RichTextArea richTextArea;
        private List<String> hashes;
        private TimeCount timeCount;
        private GridLogic gridLogic;

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
        public Builder setTimeCount(final TimeCount timeCount) {
            this.timeCount = timeCount;
            return this;
        }
        public Builder setGridLogic(final GridLogic gridLogic) {
            this.gridLogic = gridLogic;
            return this;
        }
        public ProcessAsync build() {
            return new ProcessAsync(this);
        }
    }
}
