package com.wtc.xmut.taoschool.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.wtc.xmut.taoschool.MyApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	// 保存到SD卡
	public static String sdState = Environment.getExternalStorageState();
	public static String savePath = Environment.getExternalStorageDirectory()
			+ File.separator + "Android/data/"+ MyApplication.mContext.getPackageName()+ File.separator;

	public static boolean isSDCanUse() {
		if (sdState.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/*
	 * 在SD卡上创建目录
	 */
	public static File creatSDDir() {
		if (isSDCanUse()) {
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir;
		} else {
			return null;
		}
	}

	/*
	 * 判断SD卡上的文件夹是否存在
	 */
	public static boolean isFileExist(String fileName) {
		File sdDir = creatSDDir();
		File file = new File(sdDir, fileName);
		return file.exists();
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static File getFile(String fileName) {
		File file = new File(FileUtil.savePath, fileName);
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}

	/*
	 * 在SD卡上创建文件
	 */
	public static File createSDFile(String fileName) {
		File sdDir = creatSDDir();
		File file = new File(sdDir, fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	/*
	 * 将一个InputSteam里面的数据写入到SD卡中
	 */
	public static File write2SDFromInput(String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			file = createSDFile(fileName);
			output = new FileOutputStream(file);
			int count = 0;
			int len = 0;
			byte buffer[] = new byte[4096];
			while ((count = input.read(buffer)) != -1) {
				len += count;
				output.write(buffer, 0, count);
			}
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	// 保存图片
	public static void saveBitmap(Bitmap bitmap, String imageName) {
		File PicName;
		if (isSDCanUse()) {
			// 获得sd卡根目录
			try {
				PicName = createSDFile(imageName);
				FileOutputStream fos = new FileOutputStream(PicName);
				if (PicName.getName().endsWith(".png")) {
					bitmap.compress(CompressFormat.PNG, 100, fos);
				} else if (PicName.getName().endsWith(".jpg")) {
					bitmap.compress(CompressFormat.JPEG, 100, fos);
				}
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 从SD卡取
	public static Bitmap getBitmap(String imageName) {
		Bitmap bitmap = null;
		File imagePic = null;
		if (isSDCanUse()) {
			imagePic = createSDFile(imageName);
			if (imagePic.exists()) {
				try {
					bitmap = BitmapFactory.decodeStream(new FileInputStream(
							imagePic));
				} catch (FileNotFoundException e) {
					// e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	// 将SD卡文件删除
	public static void deleteFile(File file) {
		if (isSDCanUse()) {
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				}
				// 如果它是一个目录
				else if (file.isDirectory()) {
					// 声明目录下所有的文件 files[];
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
				file.delete();
			}
		}
	}

	public static File operaFileData(String fileName, byte[] by)
	{
		{
			FileOutputStream fileout = null;
			File file = new File(fileName);
			if (file.exists())
			{
				file.delete();
			}
			try
			{
				fileout = new FileOutputStream(file);
				fileout.write(by, 0, by.length);
				fileout.close();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			return file;
		}

	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static File getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath+fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			fos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}


	/**
	 * 打开文件
	 * @param file
	 */
	public static void openFile(Context context, File file){

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		//获取文件file的MIME类型
		String type = getMIMEType(file);
		//设置intent的data和Type属性。
		intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
		//跳转
		try{
			context.startActivity(intent);     //这里最好try一下，有可能会报错。 //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * @param file
	 */
	private static String getMIMEType(File file) {

		String type="*/*";
		String fName = file.getName();
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if(dotIndex < 0){
			return type;
		}
    /* 获取文件的后缀名*/
		String end=fName.substring(dotIndex,fName.length()).toLowerCase();
		if(end=="")return type;
		//在MIME和文件类型的匹配表中找到对应的MIME类型。
		for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if(end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	private final static String[][] MIME_MapTable={
			//{后缀名，MIME类型}
			{".3gp",    "video/3gpp"},
			{".apk",    "application/vnd.android.package-archive"},
			{".asf",    "video/x-ms-asf"},
			{".avi",    "video/x-msvideo"},
			{".bin",    "application/octet-stream"},
			{".bmp",    "image/bmp"},
			{".c",  "text/plain"},
			{".class",  "application/octet-stream"},
			{".conf",   "text/plain"},
			{".cpp",    "text/plain"},
			{".doc",    "application/msword"},
			{".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
			{".xls",    "application/vnd.ms-excel"},
			{".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
			{".exe",    "application/octet-stream"},
			{".gif",    "image/gif"},
			{".gtar",   "application/x-gtar"},
			{".gz", "application/x-gzip"},
			{".h",  "text/plain"},
			{".htm",    "text/html"},
			{".html",   "text/html"},
			{".jar",    "application/java-archive"},
			{".java",   "text/plain"},
			{".jpeg",   "image/jpeg"},
			{".jpg",    "image/jpeg"},
			{".js", "application/x-javascript"},
			{".log",    "text/plain"},
			{".m3u",    "audio/x-mpegurl"},
			{".m4a",    "audio/mp4a-latm"},
			{".m4b",    "audio/mp4a-latm"},
			{".m4p",    "audio/mp4a-latm"},
			{".m4u",    "video/vnd.mpegurl"},
			{".m4v",    "video/x-m4v"},
			{".mov",    "video/quicktime"},
			{".mp2",    "audio/x-mpeg"},
			{".mp3",    "audio/x-mpeg"},
			{".mp4",    "video/mp4"},
			{".mpc",    "application/vnd.mpohun.certificate"},
			{".mpe",    "video/mpeg"},
			{".mpeg",   "video/mpeg"},
			{".mpg",    "video/mpeg"},
			{".mpg4",   "video/mp4"},
			{".mpga",   "audio/mpeg"},
			{".msg",    "application/vnd.ms-outlook"},
			{".ogg",    "audio/ogg"},
			{".pdf",    "application/pdf"},
			{".png",    "image/png"},
			{".pps",    "application/vnd.ms-powerpoint"},
			{".ppt",    "application/vnd.ms-powerpoint"},
			{".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
			{".prop",   "text/plain"},
			{".rc", "text/plain"},
			{".rmvb",   "audio/x-pn-realaudio"},
			{".rtf",    "application/rtf"},
			{".sh", "text/plain"},
			{".tar",    "application/x-tar"},
			{".tgz",    "application/x-compressed"},
			{".txt",    "text/plain"},
			{".wav",    "audio/x-wav"},
			{".wma",    "audio/x-ms-wma"},
			{".wmv",    "audio/x-ms-wmv"},
			{".wps",    "application/vnd.ms-works"},
			{".xml",    "text/plain"},
			{".z",  "application/x-compress"},
			{".zip",    "application/x-zip-compressed"},
			{"",        "*/*"}
	};



}
