/**
 * 
 */
package net.nc.uialert.widget;

import net.nc.uialert.R;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author chenjn
 * @date 上午9:48:56
 */
public class UIAlertDialog {

	private Context mContext;
	private LinearLayout mTitlePanel;
	private LinearLayout mContentPanel;
	private LinearLayout mButtonPanel;
	private ScrollView mMessagePanel;
	private TextView mTitleText;
	private TextView mMessageText;
	private ListView mSingleSelectList;
	private Button mNegativeBtn;
	private Button mNeutralBtn;
	private Button mPositiveBtn;
	private View mDividerLeft;// 按钮间的左分割线
	private View mDividerRight;// 按钮间的右分割线
	private CharSequence mTitle;
	private CharSequence mMessage;
	private CharSequence mNegative;
	private CharSequence mNeutral;
	private CharSequence mPositive;
	private DialogInterface.OnClickListener mNegativeListener;
	private DialogInterface.OnClickListener mNeutralListener;
	private DialogInterface.OnClickListener mPositiveListener;
	private DialogInterface.OnClickListener mItemOnClickListener;
	private int mNegativeColor;
	private int mNeutralColor;
	private int mPositiveColor;
	private CharSequence[] mItems;
	private Dialog dialog;
	private int mDefautTheme = R.style.AlertViewStyle;

	/**
	 * @param context
	 */
	public UIAlertDialog(Context context) {
		mContext = context;
		initLayout();

	}

	/**
	 * @param context
	 * @param theme
	 */
	public UIAlertDialog(Context context, int theme) {
		mContext = context;
		mDefautTheme = theme;
		initLayout();
	}

	private void initLayout() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_uidialog, null);
		dialog = new Dialog(mContext, mDefautTheme);
		dialog.setContentView(view);
		mTitlePanel = (LinearLayout) view.findViewById(R.id.title_panel);
		mTitleText = (TextView) view.findViewById(R.id.title);
		mContentPanel = (LinearLayout) view.findViewById(R.id.content_panel);
		mMessagePanel = (ScrollView) view.findViewById(R.id.message_panel);
		mMessageText = (TextView) view.findViewById(R.id.message);
		mSingleSelectList = (ListView) view.findViewById(R.id.single_select);
		mButtonPanel = (LinearLayout) view.findViewById(R.id.button_panel);
		mNegativeBtn = (Button) view.findViewById(R.id.btn_negative);
		mNeutralBtn = (Button) view.findViewById(R.id.btn_neutral);
		mPositiveBtn = (Button) view.findViewById(R.id.btn_positive);
		mDividerLeft = (View) view.findViewById(R.id.view_divider_left);
		mDividerRight = (View) view.findViewById(R.id.view_divider_right);

	}

	private void setLayout() {
		setTitlePanel();
		setMessagePanel();
		setButtonPanel();
		setListView();
	}

	public void setTitle(int resId) {
		mTitle = getText(mContext, resId);
	}

	public void setTitle(CharSequence title) {
		mTitle = title;
	}

	private void setTitlePanel() {
		if (TextUtils.isEmpty(mTitle)) {
			mTitlePanel.setVisibility(View.GONE);
		} else {
			mTitleText.setText(mTitle);
		}
	}

	public void setMessage(int resId) {
		mMessage = getText(mContext, resId);
	}

	public void setMessage(CharSequence message) {
		mMessage = message;
	}

	private void setMessagePanel() {
		if (TextUtils.isEmpty(mMessage)) {
			mMessagePanel.setVisibility(View.GONE);
		} else {
			mMessageText.setText(mMessage);
			mSingleSelectList.setVisibility(View.GONE);
		}
	}

	public void setNegativeButton(int resId,
			DialogInterface.OnClickListener listener, int... color) {
		mNegative = getText(mContext, resId);
		mNegativeListener = listener;
		mNegativeColor = color.length > 0 ? color[0] : -1;// color为数组,若color长度>0,取第一个值
	}

	public void setNegativeButton(CharSequence negative,
			DialogInterface.OnClickListener listener, int... color) {
		mNegative = negative;
		mNegativeListener = listener;
		mNegativeColor = color.length > 0 ? color[0] : -1;
	}

	public void setNeutralButton(int resId,
			DialogInterface.OnClickListener listener, int... color) {
		mNeutral = getText(mContext, resId);
		mNeutralListener = listener;
		mNeutralColor = color.length > 0 ? color[0] : -1;
	}

	public void setNeutralButton(CharSequence neutral,
			DialogInterface.OnClickListener listener, int... color) {
		mNeutral = neutral;
		mNeutralListener = listener;
		mNeutralColor = color.length > 0 ? color[0] : -1;
	}

	public void setPositiveButton(int resId,
			DialogInterface.OnClickListener listener, int... color) {
		mPositive = getText(mContext, resId);
		mPositiveListener = listener;
		mPositiveColor = color.length > 0 ? color[0] : -1;
	}

	public void setPositiveButton(CharSequence positive,
			DialogInterface.OnClickListener listener, int... color) {
		mPositive = positive;
		mPositiveListener = listener;
		mPositiveColor = color.length > 0 ? color[0] : -1;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setButtonPanel() {
		boolean bNegative = true;
		boolean bNeutral = true;
		boolean bPositive = true;
		if (TextUtils.isEmpty(mNegative)) {
			mNegativeBtn.setVisibility(View.GONE);
			bNegative = false;
		} else {
			mNegativeBtn.setText(mNegative);
			if (-1 != mNegativeColor) {
				mNegativeBtn.setTextColor(mNegativeColor);
			}
			if (null != mNegativeListener) {
				mNegativeBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mNegativeListener.onClick(dialog,
								DialogInterface.BUTTON_NEGATIVE);
						dialog.dismiss();

					}
				});
			}
		}
		if (TextUtils.isEmpty(mNeutral)) {
			mNeutralBtn.setVisibility(View.GONE);
			bNeutral = false;
		} else {
			mNeutralBtn.setText(mNeutral);
			if (-1 != mNeutralColor) {
				mNeutralBtn.setTextColor(mNeutralColor);
			}
			if (null != mNeutralListener) {
				mNeutralBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mNeutralListener.onClick(dialog,
								DialogInterface.BUTTON_NEUTRAL);
						dialog.dismiss();

					}
				});
			}
		}
		if (TextUtils.isEmpty(mPositive)) {
			mPositiveBtn.setVisibility(View.GONE);
			bPositive = false;
		} else {
			mPositiveBtn.setText(mPositive);
			if (-1 != mPositiveColor) {
				mPositiveBtn.setTextColor(mPositiveColor);
			}
			if (null != mPositiveListener) {
				mPositiveBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mPositiveListener.onClick(dialog,
								DialogInterface.BUTTON_POSITIVE);
						dialog.dismiss();

					}
				});
			}
		}

		if (!bNegative) {
			mDividerLeft.setVisibility(View.GONE);
			if (!bNeutral) {
				mDividerRight.setVisibility(View.GONE);
				if (bPositive) {
					mPositiveBtn.setBackground(getDrawable(mContext,
							R.drawable.selector_bottom));
				}
			} else {
				if (bPositive) {
					mNeutralBtn.setBackground(getDrawable(mContext,
							R.drawable.selector_bottom_left));
				} else {
					mNeutralBtn.setBackground(getDrawable(mContext,
							R.drawable.selector_bottom));
				}
			}
		} else {
			if (!bNeutral) {
				mDividerLeft.setVisibility(View.GONE);
				if (bPositive) {
					mNegativeBtn.setBackground(getDrawable(mContext,
							R.drawable.selector_bottom_left));
				} else {
					mDividerRight.setVisibility(View.GONE);
					mNegativeBtn.setBackground(getDrawable(mContext,
							R.drawable.selector_bottom));
				}
			} else {
				if (!bPositive) {
					mDividerRight.setVisibility(View.GONE);
					mNeutralBtn.setBackground(getDrawable(mContext,
							R.drawable.selector_bottom_right));
				}
			}
		}

		// set the button panel layout visible
		if (!bPositive && !bNegative && !bNeutral) {
			mButtonPanel.setVisibility(View.GONE);
		}
	}

	public void setItems(int itemsId, final OnClickListener listener) {
		mItems = getTextArray(mContext, itemsId);
		mItemOnClickListener = listener;
	}

	public void setItems(CharSequence[] items, final OnClickListener listener) {
		mItems = items;
		mItemOnClickListener = listener;
	}

	private ItemAdapter mAdapter;

	private void setListView() {
		IOnItemClickListener onItemClickListener = null;
		if (null != mItemOnClickListener) {
			onItemClickListener = new IOnItemClickListener() {

				@Override
				public void onItemClick(View view, int position) {
					mItemOnClickListener.onClick(dialog, position);
					dialog.dismiss();
				}
			};
		}
		if (null != mItems && mItems.length > 0) {
			mAdapter = new ItemAdapter(mContext, mItems, onItemClickListener);
		}
		mSingleSelectList.setAdapter(mAdapter);
		if (null == mAdapter) {
			mSingleSelectList.setVisibility(View.GONE);
		}
	}

	public void show() {
		setLayout();
		dialog.show();
	}

	private CharSequence getText(Context context, int resId) {
		if (null == context) {
			return null;
		}
		return context.getText(resId);
	}

	private Drawable getDrawable(Context context, int icon) {
		if (null == context) {
			return null;
		}
		return context.getResources().getDrawable(icon);
	}

	private CharSequence[] getTextArray(Context context, int arrayId) {
		if (null == context) {
			return null;
		}
		return context.getResources().getTextArray(arrayId);
	}

	public void setCanceledOnTouchOutside(boolean b) {
		dialog.setCanceledOnTouchOutside(b);
	}

	public interface IOnItemClickListener {
		void onItemClick(View view, int position);
	}

	private static class ItemAdapter extends BaseAdapter {

		private Context mContext;
		private CharSequence[] mItems;
		private int mCheckedItem = -1;
		private IOnItemClickListener mOnItemClickListener;

		public ItemAdapter(Context context, CharSequence[] items,
				IOnItemClickListener listener) {
			mContext = context;
			mItems = items;
			mOnItemClickListener = listener;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			if (null == mItems) {
				return 0;
			}
			return mItems.length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			if (null == mItems) {
				return null;
			}
			return mItems[position];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.list_single_select_item, null);
			}
			TextView textView = (TextView) convertView
					.findViewById(R.id.single_select_item);

			textView.setText(mItems[position].toString());
			textView.setTag(position);
			textView.setOnClickListener(listener);
			return convertView;
		}

		private View.OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int position = (Integer) v.getTag();
				mCheckedItem = position;
				notifyDataSetChanged();
				if (null != mOnItemClickListener) {
					mOnItemClickListener.onItemClick(v, position);
				}
			}
		};

	}
}
