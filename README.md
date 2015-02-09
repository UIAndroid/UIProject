# UIProject
自定义UI控件，项目编码UTF-8
## UIAlert
仿IOS弹出框,目前包含
###日期选择弹出框  
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
	dateDialog.show();·
###短信验证弹出框  
调用方法：  

	new SMSDialog(mContext).builder().setCanceledOnTouchOutside(true)
		.setPositiveButton(null, new OnSMSIdentifyListener() {
			@Override
			public void onSMSIdentify(String phone, String code) {
				// TODO Auto-generated method stub
			}
		}).show();
