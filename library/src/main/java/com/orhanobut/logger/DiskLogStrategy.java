package com.orhanobut.logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import com.wj.logger.DiskUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Abstract class that takes care of background threading the file log operation on Android.
 * implementing classes are free to directly perform I/O operations there.
 */
public class DiskLogStrategy implements LogStrategy {
  public static String logFolder;//TODO 此处为新增变量， 方便外部获取存储地址。 by wangjian

  private final Handler handler;

  public DiskLogStrategy(Handler handler) {
    this.handler = handler;
  }

  @Override public void log(int level, String tag, String message) {
    // do nothing on the calling thread, simply pass the tag/msg to the background thread
    handler.sendMessage(handler.obtainMessage(level, message));
  }

  static class WriteHandler extends Handler {

    private final String folder;
    private final int maxFileSize;
    private final long maxSpaceLength;//TODO 此为新增变量 by wangjian

    /**
     * @param folder 存储路径
     *               @param maxFileSize 每个文件最大长度
     *                                  @param maxSpaceSize 存储空间最大长度 //TODO 此为新增参数 by wangjian
     * */
    WriteHandler(Looper looper, String folder, int maxFileSize, long maxSpaceSize) {
      super(looper);
      logFolder = folder;
      this.folder = folder;
      this.maxFileSize = maxFileSize;
      this.maxSpaceLength = maxSpaceSize;
    }

    @SuppressWarnings("checkstyle:emptyblock")
    @Override public void handleMessage(Message msg) {
      String content = (String) msg.obj;

      FileWriter fileWriter = null;
      File logFile = getLogFile(folder, "logs");

      try {
        fileWriter = new FileWriter(logFile, true);

        writeLog(fileWriter, content);

        fileWriter.flush();
        fileWriter.close();
      } catch (IOException e) {
        if (fileWriter != null) {
          try {
            fileWriter.flush();
            fileWriter.close();
          } catch (IOException e1) { /* fail silently */ }
        }
      }
    }

    /**
     * This is always called on a single background thread.
     * Implementing classes must ONLY write to the fileWriter and nothing more.
     * The abstract class takes care of everything else including close the stream and catching IOException
     *
     * @param fileWriter an instance of FileWriter already initialised to the correct file
     */
    private void writeLog(FileWriter fileWriter, String content) throws IOException {
      fileWriter.append(content);
    }

    private File getLogFile(String folderName, String fileName) {

      File folder = new File(folderName);
      if (!folder.exists()) {
        //TODO: What if folder is not created, what happens then?
        folder.mkdirs();
      }

      int newFileCount = 0;
      File newFile;
      File existingFile = null;

      newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
      while (newFile.exists()) {
        existingFile = newFile;
        newFileCount++;
        newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
        DiskUtils.checkSize(folderName, maxSpaceLength);//TODO 此处为新增操作，每次新建文件时进行log文件夹大小检查，超过MaxLogLength,就删除最老文件 by wangjian
      }

      if (existingFile != null) {
        if (existingFile.length() >= maxFileSize) {
          return newFile;
        }
        return existingFile;
      }

      return newFile;
    }
  }
}