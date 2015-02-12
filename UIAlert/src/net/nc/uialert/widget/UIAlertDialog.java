package net.nc.uialert.widget;

import net.nc.uialert.R;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class UIAlertDialog extends Dialog {

	public UIAlertDialog(Context context, int theme) {
		super(context, theme);
	}

	public UIAlertDialog(Context context) {
		super(context);
	}

	public void setLayout(double width, double height) {
		getWindow().setLayout((int) width, (int) height);
	}

	public void setAnimStyle(int styleId) {
		if (getWindow() != null) {
			getWindow().setWindowAnimations(styleId);
		}
	}

	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {

		private static final int DEFAULT_STYLE = R.style.AlertViewStyle;

		private LinearLayout mTitlePanel;
		private TextView mTitleTextView;
		private CharSequence mTitleText;

		private LinearLayout mContentPanel;
		private ScrollView mMessagePanel;
		private TextView mMessageTextView;
		private CharSequence mMessageText;

		private View mCustomView;

		private LinearLayout mButtonPanel;
		private TextView mPositiveButton;
		private TextView mNegativeButton;
		private TextView mNeutralButton;
		private View mDividerLeftView;
		private View mDividerRightView;
		private CharSequence mPositiveText;
		private CharSequence mNegativeText;
		private CharSequence mNeutralText;
		private DialogInterface.OnClickListener mPositiveButtonListener;
		private DialogInterface.OnClickListener mNegativeButtonListener;
		private DialogInterface.OnClickListener mNeutralButtonListener;

		private Context mContext;

		private UIAlertDialog mDialog;

		public Builder(Context context) {
			this.mContext = context;
		}

		/**
		 * Set the Dialog title.
		 * 
		 * @param titleId
		 * @return
		 */
		public Builder setTitle(int titleId) {
			this.mTitleText = getText(mContext, titleId);
			return this;
		}

		/**
		 * Set the Dialog title.
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(CharSequence title) {
			this.mTitleText = title;
			return this;
		}

		/**
		 * Set the Dialog message.
		 * 
		 * @param messageId
		 * @return
		 */
		public Builder setMessage(int messageId) {
			this.mMessageText = getText(mContext, messageId);
			return this;
		}

		/**
		 * Set the Dialog message.
		 * 
		 * @param message
		 * @return
		 */
		public Builder setMessage(CharSequence message) {
			this.mMessageText = message;
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * custom view will remove message view and be added to the Dialog.
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.mCustomView = v;
			return this;
		}

		/**
		 * Set the text to display in positive button and a listener to be
		 * invoked when it is pressed.
		 * 
		 * @param textId
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(int textId,
				DialogInterface.OnClickListener listener) {
			this.mPositiveText = getText(mContext, textId);
			this.mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * Set the text to display in positive button and a listener to be
		 * invoked when it is pressed.
		 * 
		 * @param text
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(CharSequence text,
				DialogInterface.OnClickListener listener) {
			this.mPositiveText = text;
			this.mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * Set the text to display in negative button and a listener to be
		 * invoked when it is pressed.
		 * 
		 * @param textId
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(int textId,
				DialogInterface.OnClickListener listener) {
			this.mNegativeText = getText(mContext, textId);
			this.mNegativeButtonListener = listener;
			return this;
		}

		/**
		 * Set the text to display in negative button and a listener to be
		 * invoked when it is pressed.
		 * 
		 * @param text
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(CharSequence text,
				DialogInterface.OnClickListener listener) {
			this.mNegativeText = text;
			this.mNegativeButtonListener = listener;
			return this;
		}

		/**
		 * Set the text to display in neutral button and a listener to be
		 * invoked when it is pressed.*
		 * 
		 * @param textId
		 * @param listener
		 * @return
		 */
		public Builder setNeutralButton(int textId,
				DialogInterface.OnClickListener listener) {
			this.mNeutralText = getText(mContext, textId);
			this.mNeutralButtonListener = listener;
			return this;
		}

		/**
		 * Set the text to display in neutral button and a listener to be
		 * invoked when it is pressed.
		 * 
		 * @param text
		 * @param listener
		 * @return
		 */
		public Builder setNeutralButton(CharSequence text,
				DialogInterface.OnClickListener listener) {
			this.mNeutralText = text;
			this.mNeutralButtonListener = listener;
			return this;
		}

		/**
		 * Create the dialog.
		 */
		public UIAlertDialog create() {

			mDialog = new UIAlertDialog(mContext, DEFAULT_STYLE);

			LayoutInflater inflater = LayoutInflater.from(mContext);
			View contentView = inflater.inflate(R.layout.layout_uidialog, null);

			setupTitlePanel(contentView); // set title panel

			setupContentPanel(contentView); // set content panel

			setupButtonPanel(contentView); // set button panel

			setupCustomView(contentView); // set custom view

			mDialog.setContentView(contentView);

			setupWindowParams();

			return mDialog;
		}

		private void setupWindowParams() {
			mDialog.setAnimStyle(R.style.AlertViewStyle);

			WindowManager wManager = mDialog.getWindow().getWindowManager();
			double width = wManager.getDefaultDisplay().getWidth() * 0.8;
			mDialog.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
		}

		/**
		 * Setup title panel layout.
		 */
		private void setupTitlePanel(View contentView) {
			mTitlePanel = (LinearLayout) contentView
					.findViewById(R.id.title_panel);
			mTitleTextView = (TextView) contentView.findViewById(R.id.title);

			if (!TextUtils.isEmpty(mTitleText)) {
				mTitleTextView.setText(mTitleText);
			} else {
				mTitlePanel.setVisibility(View.GONE);
			}
		}

		/**
		 * Setup content panel layout.
		 */
		private void setupContentPanel(View contentView) {
			mContentPanel = (LinearLayout) contentView
					.findViewById(R.id.content_panel);
			mMessageTextView = (TextView) contentView
					.findViewById(R.id.message);
			mMessagePanel = (ScrollView) contentView
					.findViewById(R.id.message_panel);

			if (!TextUtils.isEmpty(mMessageText)) {
				mMessageTextView.setText(mMessageText);
			} else {
				mMessagePanel.setVisibility(View.GONE);
			}
		}

		/**
		 * Setup button panel layout.
		 */

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		private void setupButtonPanel(View contentView) {
			mPositiveButton = (TextView) contentView
					.findViewById(R.id.btn_positive);
			mNegativeButton = (TextView) contentView
					.findViewById(R.id.btn_negative);
			mNeutralButton = (TextView) contentView
					.findViewById(R.id.btn_neutral);
			mDividerLeftView = contentView.findViewById(R.id.view_divider_left);
			mDividerRightView = contentView
					.findViewById(R.id.view_divider_right);
			mButtonPanel = (LinearLayout) contentView
					.findViewById(R.id.button_panel);

			boolean showPositive = false;
			boolean showNegative = false;
			boolean showNeutral = false;

			// set the confirm button visible
			if (!TextUtils.isEmpty(mPositiveText)) {
				showPositive = true;
				mPositiveButton.setText(mPositiveText);
				mPositiveButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mPositiveButtonListener != null) {
							mPositiveButtonListener.onClick(mDialog,
									DialogInterface.BUTTON_POSITIVE);
						}
						mDialog.dismiss();
					}
				});
			} else {
				mPositiveButton.setVisibility(View.GONE);
			}

			// set the cancel button visible
			if (!TextUtils.isEmpty(mNegativeText)) {
				showNegative = true;
				mNegativeButton.setText(mNegativeText);
				mNegativeButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mNegativeButtonListener != null) {
							mNegativeButtonListener.onClick(mDialog,
									DialogInterface.BUTTON_NEGATIVE);
						}
						mDialog.dismiss();
					}
				});
			} else {
				mNegativeButton.setVisibility(View.GONE);
			}

			// set the neutral button visible
			if (!TextUtils.isEmpty(mNeutralText)) {
				showNeutral = true;
				mNeutralButton.setText(mNeutralText);
				mNeutralButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mNeutralButtonListener != null) {
							mNeutralButtonListener.onClick(mDialog,
									DialogInterface.BUTTON_NEUTRAL);
						}
						mDialog.dismiss();
					}
				});
			} else {
				mNeutralButton.setVisibility(View.GONE);
			}

			// set the divider visible
			if (!showNegative) {
				mDividerLeftView.setVisibility(View.GONE);
				if (!showNeutral) {
					mDividerRightView.setVisibility(View.GONE);
					if (showPositive) {
						mPositiveButton.setBackground(getDrawable(mContext,
								R.drawable.selector_bottom));
					}
				} else {
					if (showPositive) {
						mNeutralButton.setBackground(getDrawable(mContext,
								R.drawable.selector_bottom_left));
					} else {
						mNeutralButton.setBackground(getDrawable(mContext,
								R.drawable.selector_bottom));
					}
				}
			} else {
				if (!showNeutral) {
					mDividerLeftView.setVisibility(View.GONE);
					if (showPositive) {
						mNegativeButton.setBackground(getDrawable(mContext,
								R.drawable.selector_bottom_left));
					} else {
						mDividerRightView.setVisibility(View.GONE);
						mNegativeButton.setBackground(getDrawable(mContext,
								R.drawable.selector_bottom));
					}
				} else {
					if (!showPositive) {
						mDividerRightView.setVisibility(View.GONE);
						mNeutralButton.setBackground(getDrawable(mContext,
								R.drawable.selector_bottom_right));
					}
				}
			}

			// set the button panel layout visible
			if (!showPositive && !showNegative && !showNeutral) {
				mButtonPanel.setVisibility(View.GONE);
			}
		}

		/**
		 * Setup custom view.
		 */
		private void setupCustomView(View contentView) {
			if (mCustomView != null) {
				mContentPanel.setVisibility(View.VISIBLE);
				mContentPanel.removeAllViews();
				mContentPanel.addView(contentView, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}

		public UIAlertDialog show() {
			mDialog = create();
			mDialog.show();
			return mDialog;
		}

		private CharSequence getText(Context context, int resId) {
			if (context == null) {
				return null;
			}
			try {
				return context.getText(resId);
			} catch (Exception e) {
				android.util.Log.e("test",
						"getText exception: " + e.getMessage());
			}
			return null;
		}

		public Drawable getDrawable(Context context, int iconId) {
			if (context == null) {
				return null;
			}
			try {
				return context.getResources().getDrawable(iconId);
			} catch (Exception e) {
				android.util.Log.e("test",
						"getDrawable exception: " + e.getMessage());
			}
			return null;
		}
	}

}
