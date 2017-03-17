package com.wtc.xmut.taoschool.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

/**
 * @author jh
 * @date 2015-6-9上午11:46:33
 * @introduce
 */
public class PhotoUtils {

    /* 头像名称 */
    public static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int PHOTO_REQUEST_CAMERA = 0x0000001;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 0x0000002;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 0x0000003;// 结果

    /**
     * 删除默认名称图片
     */
    public static boolean deleteImage() {
        return deleteImage(PHOTO_FILE_NAME);
    }

    /**
     * 按图片名称删除图片
     */
    public static boolean deleteImage(String photoName) {
        if (FileUtil.isFileExist(photoName)) {
            return new File(FileUtil.savePath, photoName).delete();
        }
        return false;
    }

    /**
     * 打开相机功能
     */
    public static void openCameraStartActivityForResult(Activity mActivity) {
        onStartActivityForResult(PhotoUtils.camera(), mActivity,
                PhotoUtils.PHOTO_REQUEST_CAMERA);
    }

    /**
     * 打开相册功能
     */
    public static void openGalleryStartActivityForResult(Activity mActivity) {
        onStartActivityForResult(PhotoUtils.gallery(), mActivity,
                PhotoUtils.PHOTO_REQUEST_GALLERY);
    }

    /**
     * @param intent      意图
     * @param mActivity
     * @param requestCode 请求码
     */
    public static void onStartActivityForResult(Intent intent,
                                                Activity mActivity, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * 相机获取图片返回后调用
     */
    public static void onCameraActivityForResult(Activity mActivity,
                                                 int requestCode) {
        if (FileUtil.isSDCanUse()) {
            Uri uri = Uri.fromFile(new File(FileUtil.savePath,
                    PhotoUtils.PHOTO_FILE_NAME));
            Intent intent = crop(uri);
            onStartActivityForResult(intent, mActivity, requestCode);
        } else {
            ToastUtils.showToast(mActivity, "未找到存储卡，无法存储照片！");
        }


    }

    /**
     * 相册获取图片返回后调用
     *
     * @param data 得到图片的全路径
     */
    public static void onGalleryActivityForResult(Intent data,
                                                  Activity mActivity, int requestCode) {
        Intent intent = crop(data.getData());
        onStartActivityForResult(intent, mActivity, requestCode);
    }

    /**
     * bitmap转为file
     *
     * @param bitmap
     */
    public static File getImageFile(Bitmap bitmap) {
        return getImageFile(bitmap, PhotoUtils.PHOTO_FILE_NAME);
    }

    /**
     * bitmap转为file
     *
     * @param bitmap
     * @param photoName 定义文件名
     */
    public static File getImageFile(Bitmap bitmap, String photoName) {
        FileUtil.saveBitmap(bitmap, photoName);
        return FileUtil.getFile(photoName);
    }

    /**
     * bitmap转为byte[]
     *
     * @param photoName 定义文件名
     */
    public static byte[] getImageByte(String photoName) throws IOException {

        return PostFile.readFileImage(photoName);
    }

    /*
     * 从相册获取
     */
    public static Intent gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }

    /*
     * 从相机获取
     */
    public static Intent camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (FileUtil.isSDCanUse()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(FileUtil.savePath, PHOTO_FILE_NAME)));
        }
        return intent;
    }

    /**
     * 剪切图片
     *
     * @param uri
     */
    public static Intent crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX",100);
        intent.putExtra("outputY", 100);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        return intent;
    }
}
