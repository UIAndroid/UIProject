# UIProject
自定义UI控件，项目编码UTF-8
## UIAlert
仿IOS弹出框,目前包含
###提醒框
截图：
![](https://github.com/UIAndroid/UIProject/blob/master/UIAlert/Images/UIAlertDialog.jpg)
调用方法:
UIAlertDialog dialog = new UIAlertDialog.Builder(MainActivity.this)
.setTitle("提醒")
.setMessage("确定要关闭吗!")
.setPositiveButton("确定",
	new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog,int which) {
			Toast.makeText(getBaseContext(), "确定", Toast.LENGTH_SHORT).show();
		}
	})
.setNegativeButton("取消",
	new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog,int which) {
			Toast.makeText(getBaseContext(), "取消",Toast.LENGTH_SHORT).show();
		}
	}).create();
dialog.setCanceledOnTouchOutside(false);
dialog.show();
			
###日期选择弹出框  
截图：  
![日期选择弹出框](https://raw.githubusercontent.com/UIAndroid/UIProject/master/UIAlert/Images/DateDialog.png)  
调用方法：  

	DateDialog dateDialog = new DateDialog(mContext).builder();
	//dateDialog.setTitle("日期选择");
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
###短信验证弹出框  
截图：  
![短信验证弹出框](https://raw.githubusercontent.com/UIAndroid/UIProject/master/UIAlert/Images/SMSDialog.png)  
调用方法：  

	new SMSDialog(mContext).builder().setCanceledOnTouchOutside(true)
		.setPositiveButton(null, new OnSMSIdentifyListener() {
			@Override
			public void onSMSIdentify(String phone, String code) {
				// TODO Auto-generated method stub
			}
		}).show();  
###输入框弹出框  
截图：  
![输入框弹出框](https://raw.githubusercontent.com/UIAndroid/UIProject/master/UIAlert/Images/EditDialog.png)  
调用方法： 

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
###长进度条弹出框  
截图：  
![输入框弹出框](https://raw.githubusercontent.com/UIAndroid/UIProject/master/UIAlert/Images/ProgressDialog_run.png)
![输入框弹出框](https://raw.githubusercontent.com/UIAndroid/UIProject/master/UIAlert/Images/ProgressDialog_stop.png)  
调用方法（这里只是模拟下载进度框，具体用途可根据自己需要）：  
  
	mUpdateThread = new Thread(mUpdateRunnable);//定时线程
	mUpdateThread.start();
	mProgressDialog = new ProgressDialog(mContext).builder();
	mProgressDialog.setNegativeButton(null, new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(mUpdateThread.isAlive() || !mUpdateThread.isInterrupted()){
				mUpdateThread.interrupt();
				mUpdateThread = null;
				refresh = false; //点取消结束线程
			}
		}
				
	});
	mProgressDialog.setPositiveButton("暂停", new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(mProgressDialog.getPositiveButtonName().equals("暂停")){
				mProgressDialog.setPositiveButtonName("继续下载");
				progress = mProgressDialog.getProgress();
				refresh = false; //暂停下载
			} else {
				mProgressDialog.setPositiveButtonName("暂停");
				refresh = true;
				mUpdateThread = new Thread(mUpdateRunnable);
				mUpdateThread.start(); //继续下载
			}
		}
				
	});
	mProgressDialog.show();
