package cn.lawwing.historytoday.utils;

import java.io.File;
import java.io.RandomAccessFile;

import android.util.Log;

/**
 * Created by lawwing on 2017/11/14.
 */

public class FileUtils
{
    
    public static void writeTxtFile(String strcontent, String strFilePath)
    {
        // 每次写入时，都换行写
        String strContent = strcontent + "\n";
        try
        {
            File file = new File(strFilePath);
            if (!file.exists())
            {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        }
        catch (Exception e)
        {
            Log.e("TestFile", "Error on write File.");
        }
    }
}
