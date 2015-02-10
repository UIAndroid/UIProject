package net.nc.uialert.widget;

import net.nc.uialert.R;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 类名：ProgressDialog<br>
 * 类描述：进度条弹出框<br>
 * 创建人：howtoplay<br>
 */
public class ProgressDialog {

private Context mContext;
	
	private static Dialog dialog = null;
	
	private int themeColor;
	
	private boolean titleState = false;
	private boolean cancelState = false;
	
	private TextView txtTitle, txtCancel, txtOK;
	private NumberProgressBar npbProgress;
	private View divide;
	
	public ProgressDialog(Context context) {
		this.mContext = context;
	}
	
	public ProgressDialog builder(){
		View view = View.inflate(mContext, R.layout.dialog_progress_normal, null);
		
		dialog = new Dialog(mContext, R.style.AlertViewStyle);
		dialog.setContentView(view);
		
		txtTitle = (TextView) view.findViewById(R.id.txt_title);
		divide = (View) view.findViewById(R.id.include_title_divide);
		txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
		txtOK = (TextView) view.findViewById(R.id.txt_ok);
		
		npbProgress = (NumberProgressBar) view.findViewById(R.id.npb_progress);
		
		return this;
	}
	
	public ProgressDialog setTitle(String title) {
		titleState = true;
		if ("".equals(title)) {
			txtTitle.setText("标题");
		} else {
			txtTitle.setText(title);
		}
		return this;
	}
	
	public ProgressDialog setProgress(int progress){
		npbProgress.setProgress(progress);
		return this;
	}
	
	public int getProgress(){
		return npbProgress.getProgress();
	}
	
	public ProgressDialog setThemeColor(int color){
		themeColor = mContext.getResources().getColor(color);
		txtOK.setTextColor(themeColor);
		return this;
	}
	
	public ProgressDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}
	
	public ProgressDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}
	
	public ProgressDialog setNegativeButton(String text,
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
	
	public ProgressDialog setPositiveButton(String text, final OnClickListener... onClickListener) {
		if (TextUtils.isEmpty(text)) {
			txtOK.setText("确定");
		} else {
			txtOK.setText(text);
		}
		txtOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(onClickListener.length > 0){
					onClickListener[0].onClick(v);
				}
			}
			
		});
		return this;
	}
	
	public ProgressDialog setPositiveButtonName(String text){
		txtOK.setText(text);
		return this;
	}
	
	public String getPositiveButtonName(){
		return txtOK.getText().toString();
	}
	
	private void setLayout() {
		if (!titleState) {
			txtTitle.setVisibility(View.GONE);
			divide.setVisibility(View.GONE);
		} else {
			txtTitle.setVisibility(View.VISIBLE);
			divide.setVisibility(View.VISIBLE);
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
