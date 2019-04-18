package com.tssss.bysj.widget;

/**
 * 一个标准弹窗需要的数据
 * <p>
 * 标题、弹窗发生的事件、提示内容详情
 */
public class DialogModel {
    /**
     * 标题
     */
    private String title;
    /**
     * 时间戳
     */
    private String time;
    /**
     * 文本内容
     */
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Builder 构建者模式
     */
    public static class Builder {
        private DialogModel dialogModel;

        public Builder() {
            this.dialogModel = new DialogModel();
        }

        /**
         * 标题
         */
        private String title;
        /**
         * 时间戳
         */
        private String time;
        /**
         * 文本内容
         */
        private String content;

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getTime() {
            return time;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public DialogModel build() {
            this.dialogModel.setTitle(this.title);
            this.dialogModel.setTime(this.time);
            this.dialogModel.setContent(this.content);
            return this.dialogModel;
        }
    }
}
