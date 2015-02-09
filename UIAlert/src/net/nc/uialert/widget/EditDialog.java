package net.nc.uialert.widget;

import net.nc.uialert.R;
import net.nc.uialert.listener.OnEditListener;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class EditDialog {
	
	private Context mContext;
	
	private static Dialog dialog = null;
	
	private int themeColor;
	
	private boolean titleState = false;
	private boolean cancelState = false;
	
	private TextView txtTitle, txtCancel, txtOK;
	private View divide;
	private EditText edtContent;
	
	public EditDialog(Context context) {
		this.mContext = context;
	}
	
	public EditDialog builder(){
		View view = View.inflate(mContext, R.layout.dialog_edit, null);
		
		dialog = new Dialog(mContext, R.style.AlertViewStyle);
		dialog.setContentView(view);
		
		txtTitle = (TextView) view.findViewById(R.id.txt_title);
		divide = (View) view.findViewById(R.id.include_title_divide);
		txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
		txtOK = (TextView) view.findViewById(R.id.txt_ok);
		
		edtContent = (EditText) view.findViewById(R.id.edt_content);
		
		return this;
	}
	
	public EditDialog setTitle(String title) {
		titleState = true;
		if ("".equals(title)) {
			txtTitle.setText("标题");
		} else {
			txtTitle.setText(title);
		}
		return this;
	}
	
	public EditDialog setThemeColor(int color){
		themeColor = mContext.getResources().getColor(color);
		txtOK.setTextColor(themeColor);
		return this;
	}
	
	public EditDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}
	
	public EditDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}
	
	public EditDialog setHint(String hint){
		edtContent.setHint(hint);
		return this;
	}
	
	public EditDialog setNegativeButton(String text,
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
	
	public EditDialog setPositiveButton(String text, final OnEditListener onEditListener) {
		if (TextUtils.isEmpty(text)) {
			txtOK.setText("确定");
		} else {
			txtOK.setText(text);
		}
		txtOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onEditListener.onEdit(edtContent.getText().toString());
			}
			
		});
		return this;
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
