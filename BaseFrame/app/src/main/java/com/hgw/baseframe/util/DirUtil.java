package com.hgw.baseframe.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import com.hgw.baseframe.constants.Constants;

import java.io.File;

/**
 * 描述：APP本地目录结构
 * (项目启动时，需要获取权限读写储存卡权限，再调用DirUtil.initDirs(this)，方法初始化目录就行了，一般放在应用的启动页)
 * @author hgw
 */
public class DirUtil {
    /** 根目录 */
    public static final String ROOT = Constants.FILEROOTPATH;
    /** 日志保存路径 */
    public static String PATH_LOG = ROOT + "/logfile/";
    /** 缓存目录 */
    public static final String PATH_CACHE= ROOT + "/cache/";
    /** 临时文件目录 */
    public static final String WEIXIETONG_TEMP = ROOT + "/temp/";
    /** 相机照片目录 */
    public static final String PATH_CAMERA = ROOT + "/camera/";
    /** 剪裁后的图片目录 */
    public static final String PATH_CROP = ROOT + "/crop/";
    /** 图片保存路径 */
    public static final String PATH_IMAGE_SAVE = ROOT + "/image/";
    /** 下载文件保存目录 */
    public static final String PATH_DOWNLOAD = ROOT + "/download/";
    /** 压缩图片保存目录 */
    public static final String PATH_IMAGE_COMPRESS = ROOT + "/compress/";
    
    
    /**
     * 初始化目录
     */
    public static void initDirs(Context context) {
    	if(hasSDCard(context)){
	        mkdir(ROOT);
	        mkdir(PATH_LOG);
            mkdir(PATH_CACHE);
	        mkdir(WEIXIETONG_TEMP);
	        mkdir(PATH_CROP);
	        mkdir(PATH_IMAGE_SAVE);
	        mkdir(PATH_CAMERA);
	        mkdir(PATH_DOWNLOAD);
	        mkdir(PATH_IMAGE_COMPRESS);
    	}
    }
    
    /**
     * 新建目录
     * 
     * @param path
     */
    public static void mkdir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * 查看是否存在sdcard
     * @param context
     * @return
     */
    public static boolean hasSDCard(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.e("Test","权限不允许或者SDCard不存在，初始化目录失败");
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 判断SD卡空间是否够用
     * 
     * @return
     */
    public static boolean isSDCardSpaceEnough() {
        StatFs fs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        // keep one free block
        return fs.getAvailableBlocks() > 1;
    }
    
    /**
     * 文件夹删除
     * 
     * @param path
     * path为该目录的路径
     */
    public static boolean deleteDirectory(String path) {
        // 删除目录及其下面的所有子文件和子文件夹，注意一个目录下如果还有其他文件或文件夹
        // 则直接调用delete方法是不行的，必须待其子文件和子文件夹完全删除了才能够调用delete
        boolean flag = true;
        File dirFile = new File(path);
        if (!dirFile.isDirectory()) {
            return flag;
        }
        File[] files = dirFile.listFiles();
        // 删除该文件夹下的文件和文件夹
        for (File file : files) {
            // Delete file.
            if (file.isFile()) {
                flag = deleteFile(file);
            } else if (file.isDirectory()) {// Delete folder
                flag = deleteDirectory(file.getAbsolutePath());
            }
            // 只要有一个失败就立刻不再继续
            if (!flag) {
                break;
            }
        }
        // 删除空目录
        flag = dirFile.delete();
        return flag;
    }

    /**
     * 文件删除
     * 
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        return file.delete();
    }
}
