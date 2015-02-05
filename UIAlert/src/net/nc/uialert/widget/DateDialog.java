package net.nc.uialert.widget;

import net.nc.uialert.R;
import net.nc.uialert.listener.OnDatePickListener;
import net.simonvt.numberpicker.NumberPicker;
import net.simonvt.numberpicker.NumberPicker.OnValueChangeListener;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 类名：DateDialog<br>
 * 类描述：日期选择弹出框<br>
 * 创建人：howtoplay<br>
 */
public class DateDialog {

	private Context mContext;
	
	private static Dialog dialog = null;
	
	private TextView txtTitle, txtCancel, txtOK;
	private NumberPicker npYear, npMonth, npDay;
	
	private boolean titleState = false;
	private boolean cancelState = false;
	
	public DateDialog(Context context) {
		this.mContext = context;
	}
	
	public DateDialog builder() {
		View view = View.inflate(mContext, R.layout.dialog_date, null);
		
		dialog = new Dialog(mContext, R.style.AlertViewStyle);
		dialog.setContentView(view);
		
		txtTitle = (TextView) view.findViewById(R.id.txt_title);
		txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
		txtOK = (TextView) view.findViewById(R.id.txt_ok);
		
		npYear = (NumberPicker) view.findViewById(R.id.np_year);
		npMonth = (NumberPicker) view.findViewById(R.id.np_month);
		npDay = (NumberPicker) view.findViewById(R.id.np_day);
		
		npYear.setMaxValue(2014);
		npYear.setMinValue(1900);
		npYear.setValue(1985);
		
		npMonth.setMaxValue(12);
		npMonth.setMinValue(1);
		
		npDay.setMaxValue(31);
		npDay.setMinValue(1);
		
		npYear.setOnValueChangedListener(new OnValueChangeListener(){

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				if(npMonth.getValue() == 2){
					if (picker.getValue() % 4== 0 && picker.getValue() % 100 != 0 
							|| picker.getValue() % 400 == 0) { 
						npDay.setMaxValue(29);
						npDay.setMinValue(1);
					} else {
						npDay.setMaxValue(28);
						npDay.setMinValue(1);
					}
					
				}
			}
			
		});
		
		npMonth.setOnValueChangedListener(new OnValueChangeListener(){

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				if(picker.getValue() == 2){
					if (npYear.getValue() % 4== 0 && npYear.getValue() % 100 != 0 
							|| npYear.getValue() % 400 == 0) { 
						npDay.setMaxValue(29);
						npDay.setMinValue(1);
					} else {
						npDay.setMaxValue(28);
						npDay.setMinValue(1);
					}
					
				} else if(picker.getValue() == 1 || picker.getValue() == 3 ||
						picker.getValue() == 5 || picker.getValue() == 7 ||
						picker.getValue() == 8 || picker.getValue() == 10 ||
						picker.getValue() == 12){
					npDay.setMaxValue(31);
					npDay.setMinValue(1);
				} else {
					npDay.setMaxValue(30);
					npDay.setMinValue(1);
				}
			}
			
		});

		return this;
	}
	
	public DateDialog setTitle(String title) {
		titleState = true;
		if ("".equals(title)) {
			txtTitle.setText("日期");
		} else {
			txtTitle.setText(title);
		}
		return this;
	}
	
	public DateDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}
	
	public DateDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}
	
	public DateDialog setNegativeButton(String text,
			final OnClickListener... listener) {
		cancelState = true;
		if (TextUtils.isEmpty(text)) {
			txtCancel.setText("取消");
		} else {
			txtCancel.setText(text);
		}
		txtCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener.length > 0){
					listener[0].onClick(v);
				}
				dialog.dismiss();
			}
			
		});
		return this;
	}
	
	public DateDialog setPositiveButton(String text, final OnDatePickListener listener) {
		if (TextUtils.isEmpty(text)) {
			txtOK.setText("确定");
		} else {
			txtOK.setText(text);
		}
		txtOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onDatePick(npYear.getValue(), 
						npMonth.getValue(), npDay.getValue());
				dialog.dismiss();
			}
			
		});
		return this;
	}
	
	public DateDialog setDate(int year, int month, int day){
		if(year <= npYear.getMaxValue() && year >= npYear.getMinValue()){
			npYear.setValue(year);
		}
		if(month <= npMonth.getMaxValue() && month >= npMonth.getMinValue()){
			npMonth.setValue(month);
			if(month == 2){
				if (year % 4== 0 && year % 100 != 0 || year % 400 == 0) { 
					npDay.setMaxValue(29);
					npDay.setMinValue(1);
				} else {
					npDay.setMaxValue(28);
					npDay.setMinValue(1);
				}
				
			} else if(month == 1 || month == 3 || month == 5 || npMonth.getValue() == 7 
					|| month == 8 || month == 10 || month == 12){
				npDay.setMaxValue(31);
				npDay.setMinValue(1);
			} else {
				npDay.setMaxValue(30);
				npDay.setMinValue(1);
			}
		}
		if(day <= npDay.getMaxValue() && day >= npDay.getMinValue()){
			npDay.setValue(day);
		}
		return this;
	}
	
	private void setLayout() {
		if (!titleState) {
			txtTitle.setVisibility(View.GONE);
		} else {
			txtTitle.setVisibility(View.VISIBLE);
		}
		
		if(!cancelState) {
			setNegativeButton(null);
		}
	}
	
	public void show() {
		setLayout();
		dialog.show();
	}

}
