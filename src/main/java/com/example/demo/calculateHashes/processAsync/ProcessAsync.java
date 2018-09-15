package com.example.demo.calculateHashes.processAsync;

import com.example.demo.MyUI;
import com.example.demo.calculateHashes.HashesTypes;
import com.example.demo.calculateHashes.createGridTransactions.GridLogic;
import com.example.demo.util.TimeCount;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.RichTextArea;
import java.nio.file.Path;

public final class ProcessAsync {

    private final MyUI ui;
    private final Path path;
    private final ProgressBar bar;
    private final RichTextArea richTextArea;
    private final HashesTypes hashesTypes;
    private final TimeCount timeCount;
    private GridLogic gridLogic;

    private ProcessAsync(final Builder builder) {
        this.path = builder.path;
        this.bar = builder.progressBar;
        this.richTextArea = builder.richTextArea;
        this.timeCount = builder.timeCount;
        this.gridLogic = builder.gridLogic;
        this.ui = builder.ui;
        if(builder.hashesTypes == null) {
            throw new RuntimeException("Hashes it`s required");
        }
        this.hashesTypes = builder.hashesTypes;
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

    public HashesTypes getHashes() {
        return hashesTypes;
    }

    public TimeCount getTimeCount() {return timeCount;}

    public GridLogic getGridLogic() {
        return gridLogic;
    }
    public MyUI getUI() {
        return ui;
    }

    @Override
    public String toString() {
        return "ProcessAsync{" +
                "path=" + path +
                ", bar=" + bar +
                ", richTextArea=" + richTextArea +
                ", hashes=" + hashesTypes +
                ", grid = "  + gridLogic +
                '}';
    }

    public static class Builder {
        private Path path;
        private ProgressBar progressBar;
        private RichTextArea richTextArea;
        private HashesTypes hashesTypes;
        private TimeCount timeCount;
        private GridLogic gridLogic;
        private MyUI ui;

        public Builder setPath(final Path path) {
            this.path = path;
            return this;
        }
        public Builder setProgressBar(final ProgressBar progressBar) {
            this.progressBar = progressBar;
            return this;
        }
        public Builder setRichTextAreaResult(final RichTextArea richTextArea) {
            this.richTextArea = richTextArea;
            return this;
        }
        public Builder setHashes(final HashesTypes hashesTypes) {
            this.hashesTypes = hashesTypes;
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
        public Builder setUI(final MyUI ui) {
            this.ui = ui;
            return this;
        }
        public ProcessAsync build() {
            return new ProcessAsync(this);
        }
    }
}
