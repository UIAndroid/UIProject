package net.nc.uialert.listener;

/**
 * 类名：OnSMSIdentifyListener<br>
 * 类描述：短信验证回调接口<br>
 * 创建人：howtoplay<br>
 */
public interface OnSMSIdentifyListener {
	
	public void onSMSIdentify(String phone, String code);

}
