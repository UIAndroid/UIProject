package net.nc.uialert;

import net.nc.uialert.listener.OnDatePickListener;
import net.nc.uialert.listener.OnSMSIdentifyListener;
import net.nc.uialert.widget.DateDialog;
import net.nc.uialert.widget.SMSDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	private Context mContext;
	private Button btnDate;
	private Button btnSMS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = MainActivity.this;
		
		btnDate = (Button) findViewById(R.id.btn_date);
		btnDate.setOnClickListener(this);
		
		btnSMS = (Button) findViewById(R.id.btn_sms);
		btnSMS.setOnClickListener(this);
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

		default:
			break;
		}
	}

}
