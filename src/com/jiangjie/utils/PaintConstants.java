package com.jiangjie.utils;

import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Color;

/**
 * ������漰��һЩ����
 */
public class PaintConstants {

	private PaintConstants() {

	}
	
	public static final float TOUCH_TOLERANCE = 4;
	
	public static int PEN_SIZE = 16;
	
	public static int PEN_COLOR = Color.RED;
	
	public static int ERASE_SIZE = 4;
	
	public static Blur BLUR_TYPE = BlurMaskFilter.Blur.NORMAL;
	
	//��͸���ȵĴ�С
	
	public static int TRANSPARENT = 15;
	/**
     *ͼƬ���ű�����ֵ
     */
	public static final class SCALE{
		/** ������ű���*/
		public static final float MAX_SCALE = 15f;
		/** ��С���ű���*/
		public static final float MIN_SCALE = 1.0f;
	}
	
	/**
     *����ģʽ��ѡ��
     */
	public static class SELECTOR{
		//�Ƿ������ת
		public static boolean HAIR_RURN = false;
		//�Ƿ񱣳ֱ���
		public static boolean KEEP_SCALE = true;
		//�̶�
		public static boolean KEEP_IMAGE = false;
		//��Ⱦ
		public static boolean COLORING = false;
		//��Ƥ��
		public static boolean ERASE = false;
	}
	
	/**
     *״̬
     */
	public static final class MODE{
		/** ��ʼ״̬*/
		public static final int NONE = 0;
		/** �϶�*/
		public static final int DRAG = 1;
		/** ����*/
		public static final int ZOOM = 2;
		/**����*/
		public static final int ERASE = 2;
		/** Ⱦɫ*/
		public static final int COLORING = 2;
	}

	/**
     * �洢ͼƬ��Ĭ��·��
     */
	public static final class PATH {
		public static final String SAVE_PATH = "/sdcard/paintPad";
	}

	/**
     * �������͵�ѡ��
     */
	public static final class PEN_TYPE {
		public static final int PLAIN_PEN = 1;
		public static final int ERASER = 2;
		public static final int BLUR = 3;
	}

	/**
     * Ĭ�ϻ��ʵ���ɫ�ͱ���ɫ
     */
	public static final class DEFAULT{
		public static final int PEN_COLOR = Color.BLACK;
		public static final int BACKGROUND_COLOR = Color.WHITE;
	}
}


















