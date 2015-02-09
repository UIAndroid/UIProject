# UIProject
自定义UI控件，项目编码UTF-8
## UIAlert
仿IOS弹出框,目前包含
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
