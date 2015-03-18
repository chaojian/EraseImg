package com.jiangjie.ps;

import com.jiangjie.utils.PaintConstants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageTouchView extends ImageView{
	public Matrix matrix = new Matrix();

	Matrix savedMatrix = new Matrix();
	/** ��Ļ�ķֱ���*/
	private DisplayMetrics dm;
	/** ��ǰģʽ*/
	int mode = PaintConstants.MODE.NONE;

	/** �洢float���͵�x��yֵ����������µ������X��Y*/
	PointF prev = new PointF();
	PointF curPosition = new PointF();
	PointF mid = new PointF();
	float dist = 1f;

	float oldRotation = 0;  
	float oldDistX = 1f;
	float oldDistY = 1f;

	/**λͼ����*/
	private Bitmap bitmap = null;
	private Paint paint;
	private Context context;

	private Path path;
	private Path tempPath;
	//����һ���ڴ��е�ͼƬ����ͼƬ����Ϊ������
	Bitmap cacheBitmap = null;

	//����cacheBitmap�ϵ�Canvas����
	Canvas cacheCanvas = null;
	private Paint cachePaint = null;

	private String TAG = "APP";

	int x = 0;  
	int y = 0;  


	public ImageTouchView(Context context) {
		super(context);
	}

	public ImageTouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		Log.i(TAG, "ImageTouchView(Context context, AttributeSet attrs)=>");

		setupView();
	}

	@Override
	protected void onDraw(Canvas canvas) {				
		super.onDraw(canvas);

		if(mode == PaintConstants.MODE.COLORING){
			canvas.drawPath(tempPath, paint);
		}

	}

	public void setupView(){

		//��ȡ��Ļ�ֱ���,��Ҫ���ݷֱ�����ʹ��ͼƬ����
		dm = getContext().getResources().getDisplayMetrics();
		//����MyImageView����ȡbitmap����
		BitmapDrawable bd = (BitmapDrawable)this.getDrawable();
		if(bd != null){
			bitmap = bd.getBitmap();
			//			bitmap = setBitmapAlpha(bitmap, 100);
			center(true, true);
		}	
		setCoverBitmap(bitmap);
		this.setImageMatrix(matrix);

		this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Matrix matrixTemp = new Matrix();
				matrixTemp.set(matrix);
				//view�Ĵ��������ת��
				matrixTemp.invert(matrixTemp);
				Log.i(TAG, "Touch screen.");

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				// ���㰴��
				case MotionEvent.ACTION_DOWN:
					savedMatrix.set(matrix);
					prev.set(event.getX(), event.getY());

					float[] pointPrevInit = new float[]{prev.x, prev.y};
					matrixTemp.mapPoints(pointPrevInit);
					path.moveTo(pointPrevInit[0], pointPrevInit[1]);
					tempPath.moveTo(event.getX(), event.getY());

					mode = PaintConstants.MODE.DRAG;
					Log.i(TAG, "ACTION_DOWN=>.");
					break;
					// ���㰴��
				case MotionEvent.ACTION_POINTER_DOWN:
					dist = spacing(event);
					oldRotation = rotation(event);  
					oldDistX = spacingX(event);
					oldDistY = spacingY(event);
					// �����������������10�����ж�Ϊ���ģʽ
					if (spacing(event) > 10f) {
						savedMatrix.set(matrix);
						midPoint(mid, event);
						mode = PaintConstants.MODE.ZOOM;
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.i(TAG, "ACTION_UP=>.");
					if(mode == PaintConstants.MODE.COLORING){
						cachePaint.setColor(PaintConstants.PEN_COLOR);
						cachePaint.setStrokeWidth(PaintConstants.PEN_SIZE);
						cachePaint.setAlpha(PaintConstants.TRANSPARENT);  
						cachePaint.setMaskFilter(new BlurMaskFilter(5, PaintConstants.BLUR_TYPE));
						
						cacheCanvas.drawPath(path, cachePaint);
						path.reset();
						tempPath.reset();
					}
					break;

				case MotionEvent.ACTION_POINTER_UP:
					mode = PaintConstants.MODE.NONE;
					break;

				case MotionEvent.ACTION_MOVE:
					if(!PaintConstants.SELECTOR.KEEP_IMAGE){
						if (mode == PaintConstants.MODE.DRAG) {
							matrix.set(savedMatrix);
							matrix.postTranslate(event.getX() - prev.x, event.getY() - prev.y);
						} else if (mode == PaintConstants.MODE.ZOOM) {
							float rotation = (rotation(event) - oldRotation)/2;  
							float newDistX = spacingX(event);
							float newDistY = spacingY(event); 
							float scaleX = newDistX-oldDistX;
							float scaleY = newDistY-oldDistY;

							float newDist = spacing(event);
							if (newDist > 10f) {
								matrix.set(savedMatrix);
								float tScale = newDist / dist;
								tScale = tScale>1?1+((tScale-1)/2):1-(1-tScale)/2;
								if(PaintConstants.SELECTOR.KEEP_SCALE){
									matrix.postScale(tScale, tScale, mid.x, mid.y);// �s��  
								}else{
									if(Math.abs(scaleX)>=Math.abs(scaleY)){
										matrix.postScale(tScale, 1, mid.x, mid.y);// �s��  
									}else{
										matrix.postScale(1, tScale, mid.x, mid.y);// �s��
									}
								}
								if(PaintConstants.SELECTOR.HAIR_RURN)
									matrix.postRotate(rotation, mid.x, mid.y);// ���D 
							}
						}
					}else{
						float[] pointPrev = new float[]{prev.x, prev.y};
						float[] pointStop= new float[]{event.getX(), event.getY()};


						//view�Ĵ��������ת��
						matrixTemp.mapPoints(pointPrev);
						matrixTemp.mapPoints(pointStop);	

						if(PaintConstants.SELECTOR.COLORING){
							//Ⱦɫ����
							mode = PaintConstants.MODE.COLORING;
							paint.reset();
							paint = new Paint(Paint.DITHER_FLAG);
							paint.setColor(Color.RED);
							//���û��ʷ��
							paint.setStyle(Paint.Style.STROKE);
							paint.setStrokeWidth(1);
							//�����
							paint.setAntiAlias(true);
							paint.setDither(true);				
							paint.setColor(PaintConstants.PEN_COLOR);
							paint.setStrokeWidth(PaintConstants.PEN_SIZE);

							path.quadTo(pointPrev[0],pointPrev[1],pointStop[0],pointStop[1]);
							tempPath.quadTo(prev.x, prev.y,event.getX(), event.getY());
							
							// ���¿�ʼ���λ��
							prev.set(event.getX(), event.getY());

							ImageTouchView.this.setImageBitmap(cacheBitmap); 

						}else if(PaintConstants.SELECTOR.ERASE){
							//��Ƥ������

							mode = PaintConstants.MODE.ERASE;

							paint.reset();
							paint.setColor(Color.TRANSPARENT);
							paint.setAntiAlias(false);
							paint.setStyle(Paint.Style.STROKE);
							paint.setStrokeWidth(16);
							paint.setStrokeJoin(Paint.Join.ROUND);
							paint.setStrokeCap(Paint.Cap.ROUND);
							paint.setAlpha(0);   
							paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
							paint.setStrokeWidth(PaintConstants.ERASE_SIZE);

							prev.set(event.getX(), event.getY());
							
							cacheCanvas.drawLine(pointPrev[0],pointPrev[1],pointStop[0],pointStop[1], paint);
							ImageTouchView.this.setImageBitmap(cacheBitmap); 
						}
					}
				}
				ImageTouchView.this.setImageMatrix(matrix);
				invalidate();

				return true;
			}
		});
	}

	/**
	 * �����������
	 */
	protected void center(boolean horizontal, boolean vertical) {
		RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			// ͼƬС����Ļ��С���������ʾ��������Ļ���Ϸ������������ƣ��·�������������
			int screenHeight = dm.heightPixels;
			if (height < screenHeight) {
				deltaY = (screenHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < screenHeight) {
				deltaY = this.getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int screenWidth = dm.widthPixels;
			if (width < screenWidth) {
				deltaX = (screenWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < screenWidth) {
				deltaX = screenWidth - rect.right;
			}
		}
		matrix.postTranslate(deltaX, deltaY);
	} 

	private float spacingX(MotionEvent event) {  
		float x = event.getX(0) - event.getX(1);  
		return x;
	}   
	private float spacingY(MotionEvent event) {  
		float y = event.getY(0) - event.getY(1);  
		return y; 
	}      
	// ȡ��ת�Ƕ�  
	private float rotation(MotionEvent event) {  
		double delta_x = (event.getX(0) - event.getX(1));  
		double delta_y = (event.getY(0) - event.getY(1));  
		double radians = Math.atan2(delta_y, delta_x);  
		return (float) Math.toDegrees(radians);  
	}  

	/**
	 * ����ľ���
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * ������е�
	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}


	/** 
	 *  
	 * @param bm 
	 * @note set cover bitmap , which  overlay on background.  
	 */  
	private void setCoverBitmap(Bitmap bitmap) {  
		// setting paint  
		paint = new Paint();  

		cacheBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);  
		cacheCanvas = new Canvas();
		cacheCanvas.setBitmap(cacheBitmap);
		cacheCanvas.drawBitmap( bitmap, 0, 0, null);  
		
		path = new Path();
		tempPath = new Path();

		//���û��ʵ���ɫ
		cachePaint = new Paint();
		//���û��ʷ��
		cachePaint.setStyle(Paint.Style.STROKE);
		//�����
		cachePaint.setAntiAlias(true);
		cachePaint.setStrokeJoin(Paint.Join.ROUND);
		cachePaint.setStrokeCap(Paint.Cap.ROUND);
		cachePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
		//���û���ģ��Ч��
		cachePaint.setMaskFilter(new BlurMaskFilter(5, PaintConstants.BLUR_TYPE));

	} 

}







