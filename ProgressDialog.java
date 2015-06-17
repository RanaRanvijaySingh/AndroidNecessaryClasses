package com.headsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.headsup.R;
import com.headsup.customviews.TextViewPlus;

/**
 * Class to create custom progress dialog
 * 
 */
public class ProgressDialog {
	private ProgressDialogListener mListener;
	private Dialog mDialog = null;
	private Context mContext;

	public ProgressDialog(Context context, ProgressDialogListener listener) {
		this.mListener = listener;
		this.mContext = context;

	}

	/**
	 * method to show custom progress dialog
	 * 
	 * @param title
	 * @param message
	 * @param isIconVisible
	 */
	public void showProgressDialog(String title, String message,
			Boolean isIconVisible) {
		mDialog = new Dialog(mContext);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(R.layout.progress_dialog);
		mDialog.setCanceledOnTouchOutside(false);
		TextViewPlus titleText = (TextViewPlus) mDialog
				.findViewById(R.id.dialogTitleView);
		TextViewPlus messageText = (TextViewPlus) mDialog
				.findViewById(R.id.dialogMessageView);
		ImageView iconImage = (ImageView) mDialog
				.findViewById(R.id.dialogIconView);
		View dialogDividerView = (View)mDialog.findViewById(R.id.dialogDividerView);
		if (title != null) {
			titleText.setText(title);
			titleText.setVisibility(View.VISIBLE);
			dialogDividerView.setVisibility(View.VISIBLE);
		}
		if (message != null) {
			messageText.setText(message);
			messageText.setVisibility(View.VISIBLE);
		}
		if (isIconVisible) {
			iconImage.setVisibility(View.VISIBLE);
		}
		mDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				mListener.onDialogCancel();

			}
		});
		mDialog.show();

	}

	/**
	 * Function to set the dialog box to be cancelable or not.
	 * @param isCancelable
	 */
	public void setCancelable(boolean isCancelable){
		if (mDialog != null) {
			mDialog.setCancelable(isCancelable);
		}
	}
	
	/**
	 * method to hide progress dialog
	 */
	public void dismiss() {
		if (mDialog != null)
			mDialog.dismiss();

	}

	/**
	 * Interface to listen Progress Dialog events (Cancel)
	 * 
	 */
	public interface ProgressDialogListener {
		public void onDialogCancel();

	}

}
