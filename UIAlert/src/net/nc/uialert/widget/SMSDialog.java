package net.nc.uialert.widget;

import net.nc.uialert.R;
import net.nc.uialert.listener.OnSMSIdentifyListener;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 类名：SMSDialog<br>
 * 类描述：短信验证弹出框<br>
 * 创建人：howtoplay<br>
 */
public class SMSDialog {
	
	private Context mContext;
	
	private static Dialog dialog = null;
	
	private int themeColor;
	
	private boolean titleState = false;
	private boolean cancelState = false;
	
	private TimerTextView txtGetCode;
	private TextView txtTitle, txtCancel, txtOK;
	private View divide;
	private EditText edtPhone, edtCode;
	
	public SMSDialog(Context context) {
		this.mContext = context;
	}
	
	public SMSDialog builder(){
		View view = View.inflate(mContext, R.layout.dialog_sms, null);
		
		dialog = new Dialog(mContext, R.style.AlertViewStyle);
		dialog.setContentView(view);
		
		txtTitle = (TextView) view.findViewById(R.id.txt_title);
		divide = (View) view.findViewById(R.id.include_title_divide);
		txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
		txtOK = (TextView) view.findViewById(R.id.txt_ok);
		
		edtPhone = (EditText) view.findViewById(R.id.edt_phone);
		edtCode = (EditText) view.findViewById(R.id.edt_code);
		
		txtGetCode = (TimerTextView) view.findViewById(R.id.txt_get_code);
		
		edtPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void afterTextChanged(Editable s) {
				txtGetCode.setPhone(edtPhone.getText().toString());
			}  
			
		});
		return this;
	}
	
	public SMSDialog setTitle(String title) {
		titleState = true;
		if ("".equals(title)) {
			txtTitle.setText("短信验证");
		} else {
			txtTitle.setText(title);
		}
		return this;
	}
	
	public SMSDialog setThemeColor(int color){
		themeColor = mContext.getResources().getColor(color);
		txtTitle.setTextColor(themeColor);
		txtGetCode.setTextColor(themeColor);
		txtOK.setTextColor(themeColor);
		return this;
	}
	
	public SMSDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}
	
	public SMSDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}
	
	public SMSDialog setNegativeButton(String text,
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
	
	public SMSDialog setPositiveButton(String text, final OnSMSIdentifyListener onSMSIdentifyListener) {
		if (TextUtils.isEmpty(text)) {
			txtOK.setText("确定");
		} else {
			txtOK.setText(text);
		}
		txtOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String phone = edtPhone.getText().toString();
				String code = edtCode.getText().toString();
				if(TextUtils.isEmpty(phone)){
					Toast.makeText(mContext, "请输入手机号码", Toast.LENGTH_SHORT).show();
				} else if(TextUtils.isEmpty(code)){
					Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
				} else {
					onSMSIdentifyListener.onSMSIdentify(edtPhone.getText().toString(),
							edtCode.getText().toString());
				}
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
