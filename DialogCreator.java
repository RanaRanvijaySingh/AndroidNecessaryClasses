package com.headsup.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;

import com.headsup.R;
import com.headsup.customviews.ButtonTextPlus;
import com.headsup.customviews.TextViewPlus;

public class DialogCreator implements OnClickListener{

	private final String TAG = "DialogCreator";
	private Context mContext;
	private Dialog mDialog;
	private View viewDialog,viewCustom;
	private OnDialogButtonClickListener mDialogButtonClickListener;
	private OnViewListener mOnViewListener;

	public DialogCreator(Context mContext,OnDialogButtonClickListener mDialogButtonClickListener){
		this.mContext = mContext;
		mDialog = new Dialog(mContext);
		this.mDialogButtonClickListener = mDialogButtonClickListener;
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewDialog = inflater.inflate(R.layout.dialog_view_holder, null);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	}

	/**
	 * Function to set on view listener to make the view in the dialog responsive.
	 * @param mOnViewListener object of OnViewListener
	 */
	public void setOnViewListener(OnViewListener mOnViewListener){
		this.mOnViewListener = mOnViewListener;
		this.mOnViewListener.onViewListener(viewCustom);
	}

	/**
	 * Function to create new dialog box with new view.
	 * @param dialogEnterEmail view object which is needed
	 * @param title that need to be displayed at the top of the dialog box
	 * @param positiveButton string object for positive button 
	 * @param negativeButton string object for negative button
	 */
	public void createDialogWithView(int viewId,String title, String positiveButton, String negativeButton){
		if (viewDialog!=null){
			LinearLayout linearLayoutViewHolder = (LinearLayout)viewDialog.findViewById(R.id.linearLayoutViewHolder);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (viewId != 0) {
				viewCustom = inflater.inflate(viewId, null);
				if (viewCustom != null && linearLayoutViewHolder != null) {
					linearLayoutViewHolder.addView(viewCustom,layoutParams);
					setTitle(title,Gravity.CENTER);
					setPositiveNegativeButton(positiveButton,negativeButton);
					mDialog.setContentView(viewDialog,layoutParams);
					mDialog.show();		
				}
			}else {
				setTitle(title,Gravity.CENTER);
				setPositiveNegativeButton(positiveButton,negativeButton);
				mDialog.setContentView(viewDialog,layoutParams);
				mDialog.show();		
			}
		}
	}

	/**
	 * Function to set the positive and negative button.
	 * @param positiveButton what positive button says
	 * @param negativeButton what negative button says
	 */
	public void setPositiveNegativeButton(String positiveButton,String negativeButton) {
		if (viewDialog != null) {
			ButtonTextPlus buttonNegative	= (ButtonTextPlus)viewDialog.findViewById(R.id.buttonNegative);
			ButtonTextPlus buttonPositive	= (ButtonTextPlus)viewDialog.findViewById(R.id.buttonPositive);
			buttonNegative.setText(negativeButton);
			buttonPositive.setText(positiveButton);
			buttonNegative.setOnClickListener(this);
			buttonPositive.setOnClickListener(this);
		}
	}

	/**
	 * FUnction to set the title in the dialog box
	 * @param title string object 
	 * @param gravity position representing the title position. eg. Gravity.CENTER
	 */
	public void setTitle(String title, int gravity) {
		if (viewDialog != null) {
			TextViewPlus textViewTitle = (TextViewPlus)viewDialog.findViewById(R.id.textViewTitle);
			if (title != null && !title.equalsIgnoreCase("")) {
				textViewTitle.setText(title);
			}else{
				textViewTitle.setVisibility(View.GONE);
			}
			if (gravity != 0) {
				textViewTitle.setGravity(gravity);
			}
		}
	}

	/**
	 * Function to show the dialog box.
	 */
	public void show(){
		if (mDialog != null) {
			mDialog.show();
		}
	}
	
	/**
	 * Function to dismiss the dialog box.
	 */
	public void dismiss(){
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.buttonNegative:
			LogUtils.LOGI(TAG, "Clicked on negative button");
			mDialogButtonClickListener.onClickNegativeButton(viewCustom);
			dismiss();
			break;
		case R.id.buttonPositive:
			LogUtils.LOGI(TAG, "Clicked on positive button");
			mDialogButtonClickListener.onClickPositiveButton(viewCustom);
			break;

		default:
			break;
		}
	}
	
	public interface OnViewListener{
		public void onViewListener(View userView);
	}
	
	/**
	 * Interface to hand the on click listener for the buttons.
	 */
	public interface OnDialogButtonClickListener{
		public void onClickPositiveButton(View userView);
		public void onClickNegativeButton(View userView);
	}
}
