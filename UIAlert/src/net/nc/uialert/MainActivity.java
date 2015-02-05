package net.nc.uialert;

import net.nc.uialert.listener.OnDatePickListener;
import net.nc.uialert.widget.DateDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	private Button btnDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnDate = (Button) findViewById(R.id.btn_date);
		btnDate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_date:
			DateDialog dateDialog = new DateDialog(MainActivity.this).builder();
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

		default:
			break;
		}
	}

}
