package com.jiangjie.ps;

import com.jiangjie.utils.PaintConstants;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.BlurMaskFilter.Blur;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SettingsActivity extends Activity {
	
	private static final String pen_color[] = {"��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ"};
	private static final String pen_size[] = {"1","2","4","8","16","32"};
	private static final String erase_size[] = {"1","2","4","8","16","32"};
	private static final String blur_type[] = {"����ģ�� ","��˹ģ��","�ⲿģ��","ƽ��ģ��"};
	
	private Spinner penColorSpinner, penSizeSpinner, eraseSizeSpinner, blurTypeSpinner;
	private ArrayAdapter<String> penSizeAdapter, penColorAdapter, eraseAdapter, blurTypeAdapter;
	private SeekBar transparentSeekBar =null;
	private TextView transparentTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		penColorSpinner = (Spinner) findViewById(R.id.pen_color);
		penSizeSpinner = (Spinner) findViewById(R.id.pen_size);
		blurTypeSpinner = (Spinner) findViewById(R.id.blur_type);
		eraseSizeSpinner = (Spinner) findViewById(R.id.erase_size);
		
		transparentSeekBar = (SeekBar)findViewById(R.id.transparent);
		transparentSeekBar.setMax(255);
		transparentTextView = (TextView)findViewById(R.id.transparent_text);
		
		//����ѡ������ArrayAdapter��������
		penColorAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, pen_color);
		penColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//��adapter ��ӵ�spinner��
		penColorSpinner.setAdapter(penColorAdapter);
		
		//����ѡ������ArrayAdapter��������
		penSizeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, pen_size);
		penSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//��adapter ��ӵ�spinner��
		penSizeSpinner.setAdapter(penSizeAdapter);
		
		//����ѡ������ArrayAdapter��������
		blurTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, blur_type);
		blurTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//��adapter ��ӵ�spinner��
		blurTypeSpinner.setAdapter(blurTypeAdapter);
		
		//����ѡ������ArrayAdapter��������
		eraseAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, erase_size);
		eraseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//��adapter ��ӵ�spinner��
		eraseSizeSpinner.setAdapter(eraseAdapter);
		
		penColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				switch(index){
				case 0: PaintConstants.PEN_COLOR = Color.RED; break;
				case 1: PaintConstants.PEN_COLOR = Color.BLACK; break;
				case 2: PaintConstants.PEN_COLOR = Color.BLUE; break;
				case 3: PaintConstants.PEN_COLOR = Color.GREEN; break;
				case 4: PaintConstants.PEN_COLOR = Color.GRAY; break;
				case 5: PaintConstants.PEN_COLOR = Color.YELLOW; break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		penSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				PaintConstants.PEN_SIZE = Integer.valueOf(pen_size[index]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		blurTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				switch(index){
				case 0: PaintConstants.BLUR_TYPE = Blur.INNER; break;
				case 1: PaintConstants.BLUR_TYPE = Blur.NORMAL; break;
				case 2: PaintConstants.BLUR_TYPE = Blur.OUTER; break;
				case 3: PaintConstants.BLUR_TYPE = Blur.SOLID; break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		eraseSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				PaintConstants.ERASE_SIZE = Integer.valueOf(erase_size[index]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		transparentSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				PaintConstants.TRANSPARENT = seekBar.getProgress();
				transparentTextView.setText("͸���ȵ���("+PaintConstants.TRANSPARENT+"):");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		});

		
	}
}

