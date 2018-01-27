package com.share.locker.common;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Jordan on 26/01/2018.
 */

public class ImageUtil {

    public static void main(String[] arg) {

        /*//test生成压缩图
        DrawableTypeRequest glide = Glide.with(this).load(itemData.getImgUri());
        Bitmap bitmap = glide.asBitmap().into(1080,1080).get();*/
    }

    /**
     * 按照宽度缩放图片，并且保存，返回保存的路径
     * @param context
     * @param fromImgUri
     * @param targetWidth 目标文件宽度
     * @return 保存的文件对象
     */
    public static File zoomImgAndSave(Context context, Uri fromImgUri, int targetWidth) {
        try {
            String fromImgPath = getImageFilePath(context,fromImgUri);

            //只解析图片边缘，获取原始图片宽高
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;      //只解析图片边沿，获取宽高
            BitmapFactory.decodeFile(fromImgPath, options);
            final int fromWidth = options.outWidth;
            final int fromHeight = options.outHeight;
            if(fromWidth <= targetWidth){
                //无需缩放，直接返回原文件
                return new File(fromImgPath);
            }else{
                //按宽缩放,计算新文件的高
                int targetHeight = (int)(fromHeight * (1.0*targetWidth / fromWidth));    //等比例缩小高度

                //用Glide写入目标图片文件。Glide可以做圆角等特殊处理
                String targetFileName = getImageFileName(context, fromImgUri);
                FileOutputStream targetFileOut = context.openFileOutput(targetFileName, context.MODE_PRIVATE);
                Bitmap bitmap = Glide.with(context)
                        .load(fromImgUri)
                        .asBitmap()
                        .fitCenter()
                        .into(targetWidth, targetHeight)
                        .get();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, targetFileOut);
                targetFileOut.flush();
                return new File(Constants.TMP_FILE_PATH + targetFileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 生成二维码
     * @param str
     * @return
     */
    public static Bitmap encodeAsBitmap(String str,int width,int height){
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE,width,height);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e){
            e.printStackTrace();
        } catch (IllegalArgumentException iae){ // ?
            return null;
        }

        // 如果不使用 ZXing Android Embedded 的话，要写的代码

//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
    }

    //////////////////////////////////////////////////////////

    /**
     * 批量删除零食图片文件
     * @param imgFileList
     */
    public static void deleteTmpImages(List<File> imgFileList){
        if(imgFileList != null){
            for(File imgFile : imgFileList){
                if(imgFile.exists() && imgFile.getPath().startsWith(Constants.TMP_FILE_PATH)){
                    imgFile.delete();
                }
            }
        }
    }

    /**
     * 根据Uri读取图片文件名
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getImageFileName(Context context, Uri uri) {
        String filePath = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(column_index);
        }
        cursor.close();

        return filePath.substring(filePath.lastIndexOf("/")+1);
    }
    /**
     * 根据Uri读取图片文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getImageFilePath(Context context, Uri uri) {
        String filePath = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(column_index);
        }
        cursor.close();

        return filePath;
    }
}
