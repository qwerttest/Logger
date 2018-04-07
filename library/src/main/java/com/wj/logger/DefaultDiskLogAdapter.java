package com.wj.logger.library;

import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;

public class DefaultDiskLogAdapter implements LogAdapter {

    private final FormatStrategy formatStrategy;

    /**
     * @param builder 构造器
     */
    private DefaultDiskLogAdapter(Builder builder) {
        formatStrategy = CsvFormatStrategy.newBuilder().tag("Logger").logDiskPath(builder.diskPath).logDiskMaxLength(builder.diskMaxSpace).logFileMaxLength(builder.fileMaxLength).build();
    }

    @Override
    public boolean isLoggable(int priority, String tag) {
        return true;
    }

    @Override
    public void log(int priority, String tag, String message) {
        formatStrategy.log(priority, tag, message);
    }

    public static final class Builder {
        private String diskPath;//存储路径
        private long diskMaxSpace = 10 * 1024 * 1024;//默认最大存储空间为10m,单位为byte
        private int fileMaxLength = 500 * 1024;//单文件最大长度，默认为500k

        public Builder() {
        }

        public DefaultDiskLogAdapter build() {
            return new DefaultDiskLogAdapter(this);
        }

        public Builder diskPath(String path) {
            this.diskPath = path;
            return this;
        }

        public Builder diskMaxSpace(long maxSpace) {
            this.diskMaxSpace = maxSpace;
            return this;
        }

        public Builder fileMaxLength(int maxLength) {
            this.fileMaxLength = maxLength;
            return this;
        }
    }
}