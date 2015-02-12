package net.nc.uialert;

import net.nc.uialert.listener.OnDatePickListener;
import net.nc.uialert.listener.OnEditListener;
import net.nc.uialert.listener.OnSMSIdentifyListener;
import net.nc.uialert.utils.ValidateUtils;
import net.nc.uialert.widget.DateDialog;
import net.nc.uialert.widget.EditDialog;
import net.nc.uialert.widget.ProgressDialog;
import net.nc.uialert.widget.SMSDialog;
import net.nc.uialert.widget.UIAlertDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	private Button btnProgress;
	private Button btnAlert;

	private ProgressDialog mProgressDialog;
	private UpdateHandler mUpdateHandler;
	private Message msg;
	private Thread mUpdateThread;
	private UpdateRunnable mUpdateRunnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = MainActivity.this;

		btnAlert = (Button) findViewById(R.id.btn_alert);
		btnAlert.setOnClickListener(this);

		btnDate = (Button) findViewById(R.id.btn_date);
		btnDate.setOnClickListener(this);

		btnSMS = (Button) findViewById(R.id.btn_sms);
		btnSMS.setOnClickListener(this);

		btnEdit = (Button) findViewById(R.id.btn_edit);
		btnEdit.setOnClickListener(this);

		btnProgress = (Button) findViewById(R.id.btn_progress);
		btnProgress.setOnClickListener(this);

		mUpdateHandler = new UpdateHandler();
		mUpdateRunnable = new UpdateRunnable();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_alert:
			UIAlertDialog dialog = new UIAlertDialog.Builder(MainActivity.this)
					.setTitle("提醒")
					// .setMessage("确定要关闭吗!")
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(getBaseContext(), "确定",
											Toast.LENGTH_SHORT).show();
								}
							})
					.setNegativeButton(
							"取消",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(getBaseContext(), "取消",
											Toast.LENGTH_SHORT).show();
								}
							}).create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			break;
		case R.id.btn_date:
			DateDialog dateDialog = new DateDialog(mContext).builder();
			// dateDialog.setTitle("日期选择");
			dateDialog.setCanceledOnTouchOutside(true);
			dateDialog.setPositiveButton(null, new OnDatePickListener() {

				@Override
				public void onDatePick(int year, int month, int day) {
					Toast.makeText(MainActivity.this,
							year + "年" + month + "月" + day + "日",
							Toast.LENGTH_SHORT).show();
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
			mEditDialog.setPositiveButton("去验证", new OnEditListener() {

				@Override
				public void onEdit(String content) {
					if (TextUtils.isEmpty(content)) {
						Toast.makeText(mContext, "您还未输入内容", Toast.LENGTH_SHORT)
								.show();
					} else if (!ValidateUtils.isEmail(content)) {
						Toast.makeText(mContext, "请输入正确的邮箱号",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mContext, content, Toast.LENGTH_SHORT)
								.show();
					}
				}

			});
			mEditDialog.show();
			break;
		case R.id.btn_progress:
			mUpdateThread = new Thread(mUpdateRunnable);
			mUpdateThread.start();
			mProgressDialog = new ProgressDialog(mContext).builder();
			mProgressDialog.setNegativeButton(null, new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mUpdateThread.isAlive()
							|| !mUpdateThread.isInterrupted()) {
						mUpdateThread.interrupt();
						mUpdateThread = null;
						refresh = false;
					}
				}

			});
			mProgressDialog.setPositiveButton("暂停", new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mProgressDialog.getPositiveButtonName().equals("暂停")) {
						mProgressDialog.setPositiveButtonName("继续下载");
						progress = mProgressDialog.getProgress();
						refresh = false;
					} else {
						mProgressDialog.setPositiveButtonName("暂停");
						refresh = true;
						mUpdateThread = new Thread(mUpdateRunnable);
						mUpdateThread.start();
					}
				}

			});
			mProgressDialog.show();
			break;

		default:
			break;
		}
	}

	private boolean refresh = true;

	private class UpdateRunnable implements Runnable {

		@Override
		public void run() {
			while (progress <= 100 && refresh) {
				msg = new Message();
				msg.what = 1;
				msg.arg1 = progress;
				mUpdateHandler.sendMessage(msg);
				try {
					Thread.sleep(1000);// 线程暂停1秒，单位毫秒
					progress += 5;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressLint("HandlerLeak")
	private class UpdateHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				mProgressDialog.setProgress(msg.arg1);
				break;

			default:
				break;
			}
		}

	}

	private int progress = 0;

}
