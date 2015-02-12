package net.nc.uialert.widget;

import net.nc.uialert.R;
import net.nc.uialert.utils.ValidateUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 类名：TimerTextView<br>
 * 类描述：定时TextView<br>
 * 创建人：howtoplay<br>
 */
public class TimerTextView extends TextView implements OnClickListener {

	private Context mContext;

	private TypedArray mTypedArray;

	private static int defaultNumber = 10;
	private int totalSeconds = defaultNumber;
	private int tmpSecondes;

	public TimerTextView(Context context) {
		super(context);
		setOnClickListener(this);
		this.mContext = context;
	}

	public TimerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(this);
		this.mContext = context;
		mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.timerNumber);
		totalSeconds = mTypedArray.getInteger(
				R.styleable.timerNumber_textNumber, defaultNumber);
		mTypedArray.recycle();
	}

	public void setTimerNumber(int number) {
		totalSeconds = number;
	}

	private boolean isPhone = false;

	public void setPhone(String phone) {
		if (ValidateUtils.isMobile(phone) && !TextUtils.isEmpty(phone)) {
			isPhone = true;
		}
	}

	@Override
	public void onClick(View v) {
		if (isPhone) {
			tmpSecondes = totalSeconds;
			new AutomateTask().execute();
		} else {
			Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
		}
	}

	private class AutomateTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// 1、调用验证码接口
			// 此处添加调用验证码接口代码

			// 2、定时开始
			while (tmpSecondes >= 0) {
				publishProgress(tmpSecondes);
				try {
					Thread.sleep(1000);// 线程暂停1秒，单位毫秒
					tmpSecondes--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int leftSeconds = values[0];
			if (leftSeconds > 0) {
				setClickable(false);
				if (leftSeconds < 10) {
					setText("0" + leftSeconds + "秒后重发");
				} else {
					setText(leftSeconds + "秒后重发");
				}
			} else {
				setClickable(true);
				setText("获取验证码");
			}
		}
	}

}
