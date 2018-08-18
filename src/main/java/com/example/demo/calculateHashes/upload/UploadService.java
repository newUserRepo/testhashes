package com.example.demo.calculateHashes.upload;

import com.example.demo.MyUI;
import com.vaadin.ui.*;

public final class UploadService {

    private final MyUI ui;
    private final ProgressBar progressBar;
    private final Button btnInterrupt;
    private final RichTextArea richTextArea;

    private UploadService(final Builder builder) {
        this.ui = builder.ui;
        this.progressBar = builder.progressBar;
        this.btnInterrupt = builder.btnInterrupt;
        this.richTextArea = builder.richTextArea;
    }

    public MyUI getUi() {
        return ui;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Button getBtnInterrupt() {
        return btnInterrupt;
    }

    public RichTextArea getRichTextArea() {
        return richTextArea;
    }

    public static class Builder {
        private MyUI ui;
        private ProgressBar progressBar;
        private Button btnInterrupt;
        private RichTextArea richTextArea;

        public Builder setUI(final MyUI ui) {
            this.ui = ui;
            return this;
        }

        public Builder setProgressBar(final ProgressBar progressBar) {
            this.progressBar = progressBar;
            return this;
        }

        public Builder setBtnInterrupt(final Button btnInterrupt) {
            this.btnInterrupt = btnInterrupt;
            return this;
        }

        public Builder setRichTextArea(final RichTextArea richTextArea) {
            this.richTextArea = richTextArea;
            return this;
        }
        public UploadService build() {
            return new UploadService(this);
        }
    }
}
