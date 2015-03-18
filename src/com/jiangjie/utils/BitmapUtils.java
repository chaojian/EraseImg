package com.jiangjie.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
public class BitmapUtils {
	
	
	/**
     * ����ͼƬ
     */
    public static void bitmapScale(Bitmap baseBitmap, Paint paint, float x, float y) {
        // ��ΪҪ��ͼƬ�Ŵ�����Ҫ���ݷŴ�ĳߴ����´���Bitmap
        Bitmap scaleBitmap = Bitmap.createBitmap(
                (int) (baseBitmap.getWidth() * x),
                (int) (baseBitmap.getHeight() * y), baseBitmap.getConfig());
        Canvas canvas = new Canvas(scaleBitmap);
        // ��ʼ��Matrix����
        Matrix matrix = new Matrix();
        // ���ݴ���Ĳ����������ű���
        matrix.setScale(x, y);
        // �������ű�������ͼƬdraw��Canvas��
        canvas.drawBitmap(baseBitmap, matrix,paint);
    }
    
    /**
     * ͼƬ��ת
     */
    public static void bitmapRotate(Bitmap baseBitmap, Paint paint,float degrees) {
        // ����һ����ԭͼһ����С��ͼƬ
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(),
                baseBitmap.getHeight(), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // ����ԭͼ������λ����ת
        matrix.setRotate(degrees, baseBitmap.getWidth() / 2,
                baseBitmap.getHeight() / 2);
        canvas.drawBitmap(baseBitmap, matrix, paint);
    }
	
    /**
     * ͼƬ�ƶ�
     */
    public static void bitmapTranslate(Bitmap baseBitmap, Paint paint, float dx, float dy) {
        // ��Ҫ�����ƶ��ľ���������ͼƬ�Ŀ���ͼ��С
        Bitmap afterBitmap = Bitmap.createBitmap(
                (int) (baseBitmap.getWidth() + dx),
                (int) (baseBitmap.getHeight() + dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // �����ƶ��ľ���
        matrix.setTranslate(dx, dy);
        canvas.drawBitmap(baseBitmap, matrix, paint);
    }
    
    /**
     * ��бͼƬ
     */
    public static void bitmapSkew(Bitmap baseBitmap, Paint paint, float dx, float dy) {
        // ����ͼƬ����б����������任��ͼƬ�Ĵ�С��
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth()
                + (int) (baseBitmap.getWidth() * dx), baseBitmap.getHeight()
                + (int) (baseBitmap.getHeight() * dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // ����ͼƬ��б�ı���
        matrix.setSkew(dx, dy);
        canvas.drawBitmap(baseBitmap, matrix, paint);
    }

    public static Bitmap decodeFromResource(Context context, int id) {
    	 Resources res = context.getResources();
    	 Bitmap bitmap = BitmapFactory.decodeResource(res,id).copy(Bitmap.Config.ARGB_8888, true);
    	 return bitmap;
    }   
    
    /**
     * ����ͼƬ��SD��
     */
	public static void saveToSdCard(String path, Bitmap bitmap) {
		if (null != bitmap && null != path && !path.equalsIgnoreCase("")) {
			try {
				File file = new File(path);
				FileOutputStream outputStream = null;
				//�����ļ�����д������
				outputStream = new FileOutputStream(new File(path), true);
				bitmap.compress(Bitmap.CompressFormat.PNG, 30, outputStream);
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		

		}
	}

    /**
     * ����bitmap
     */
	public static Bitmap duplicateBitmap(Bitmap bmpSrc, int width, int height) {
		if (null == bmpSrc) {
			return null;
		}

		int bmpSrcWidth = bmpSrc.getWidth();
		int bmpSrcHeight = bmpSrc.getHeight();

		Bitmap bmpDest = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		if (null != bmpDest) {
			Canvas canvas = new Canvas(bmpDest);
			Rect viewRect = new Rect();
			final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
			if (bmpSrcWidth <= width && bmpSrcHeight <= height) {
				viewRect.set(rect);
			} else if (bmpSrcHeight > height && bmpSrcWidth <= width) {
				viewRect.set(0, 0, bmpSrcWidth, height);
			} else if (bmpSrcHeight <= height && bmpSrcWidth > width) {
				viewRect.set(0, 0, width, bmpSrcWidth);
			} else if (bmpSrcHeight > height && bmpSrcWidth > width) {
				viewRect.set(0, 0, width, height);
			}
			canvas.drawBitmap(bmpSrc, rect, viewRect, null);
		}

		return bmpDest;
	}

    /**
     * ����bitmap
     */
	public static Bitmap duplicateBitmap(Bitmap bmpSrc) {
		if (null == bmpSrc) {
			return null;
		}

		int bmpSrcWidth = bmpSrc.getWidth();
		int bmpSrcHeight = bmpSrc.getHeight();

		Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight,
				Config.ARGB_8888);
		if (null != bmpDest) {
			Canvas canvas = new Canvas(bmpDest);
			final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);

			canvas.drawBitmap(bmpSrc, rect, rect, null);
		}

		return bmpDest;
	}

    /**
     * bitmapת�ֽ���
     */
	public static byte[] bitampToByteArray(Bitmap bitmap) {
		byte[] array = null;
		try {
			if (null != bitmap) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
				array = os.toByteArray();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}

    /**
     * �ֽ���תbitmap
     */
	public static Bitmap byteArrayToBitmap(byte[] array) {
		if (null == array) {
			return null;
		}

		return BitmapFactory.decodeByteArray(array, 0, array.length);
	}

}
