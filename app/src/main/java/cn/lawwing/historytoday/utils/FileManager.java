package cn.lawwing.historytoday.utils;

import java.io.File;

import android.os.Environment;

/**
 * Created by lawwing on 2017/11/14.
 */

public class FileManager
{
    /**
     * 应用程序在SD卡上的主目录名称
     */
    private final static String APP_FOLDER_NAME = "HistoryToday";
    
    private final static String SAVE_FILE_FOLDER_NAME = "saveFiles";
    
    /**
     * 获取app在sd卡上的主目录
     *
     * @return 成功则返回目录，失败则返回null
     */
    public static File getAppFolder()
    {
        if (SDCardUtils.isSDCardEnable())
        {
            
            File appFolder = new File(Environment.getExternalStorageDirectory(),
                    APP_FOLDER_NAME);
            return createOnNotFound(appFolder);
            
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 创建目录
     *
     * @param folder
     * @return 创建成功则返回目录，失败则返回null
     */
    private static File createOnNotFound(File folder)
    {
        if (folder == null)
        {
            return null;
        }
        
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        
        if (folder.exists())
        {
            return folder;
        }
        else
        {
            return null;
        }
    }
    
    public static File getSaveFolder()
    {
        File appFolder = getAppFolder();
        if (appFolder != null)
        {
            File photoFolder = new File(appFolder, SAVE_FILE_FOLDER_NAME);
            return createOnNotFound(photoFolder);
        }
        else
        {
            return null;
        }
    }
}
