package net.nc.uialert;

import net.nc.uialert.listener.OnDatePickListener;
import net.nc.uialert.listener.OnEditListener;
import net.nc.uialert.listener.OnSMSIdentifyListener;
import net.nc.uialert.utils.ValidateUtils;
import net.nc.uialert.widget.DateDialog;
import net.nc.uialert.widget.EditDialog;
import net.nc.uialert.widget.SMSDialog;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private Context mContext;
	private Button btnDate;
	private Button btnSMS;
	private Button btnEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = MainActivity.this;
		
		btnDate = (Button) findViewById(R.id.btn_date);
		btnDate.setOnClickListener(this);
		
		btnSMS = (Button) findViewById(R.id.btn_sms);
		btnSMS.setOnClickListener(this);
		
		btnEdit = (Button) findViewById(R.id.btn_edit);
		btnEdit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_date:
			DateDialog dateDialog = new DateDialog(mContext).builder();
//			dateDialog.setTitle("日期选择");
			dateDialog.setCanceledOnTouchOutside(true);
			dateDialog.setPositiveButton(null, new OnDatePickListener(){

				@Override
				public void onDatePick(int year, int month, int day) {
					Toast.makeText(MainActivity.this, 
							year+"年"+month+"月"+day+"日", Toast.LENGTH_SHORT).show();
				}
				
			});
			dateDialog.setDate(2000, 2, 31);
			dateDialog.show();
			break;
		case R.id.btn_sms:
			new SMSDialog(mContext).builder().setCanceledOnTouchOutside(true)
					.setPositiveButton(null, new OnSMSIdentifyListener() {

						@Override
						public void onSMSIdentify(String phone, String code) {
							// TODO Auto-generated method stub

						}
					}).show();
			break;
		case R.id.btn_edit:
			EditDialog mEditDialog = new EditDialog(mContext).builder();
			mEditDialog.setTitle("邮箱验证");
			mEditDialog.setHint("请输入邮箱");
			mEditDialog.setCanceledOnTouchOutside(true);
			mEditDialog.setPositiveButton("去验证", new OnEditListener(){

				@Override
				public void onEdit(String content) {
					if(TextUtils.isEmpty(content)){
						Toast.makeText(mContext, "您还未输入内容", Toast.LENGTH_SHORT).show();
					} else if(!ValidateUtils.isEmail(content)) {
						Toast.makeText(mContext, "请输入正确的邮箱号", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
					}
				}
				
			});
			mEditDialog.show();
			break;

		default:
			break;
		}
	}

}
