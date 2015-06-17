package com.headsup.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.headsup.activities.DashBoardActivity;
import com.headsup.model.TeamInvitesContactModel;
import com.headsup.model.TeamRoasterDataModel;
import com.headsup.model.TeamRoasterDataModel.DetailsModel;
import com.weboniselab.android.floatinglabel.FloatingLabeLayout;


/**
 * @author webonise
 *
 */
public class HelperClass {

	private final static String TAG = "HelperClass";
	private static final String STRING_SAPERATOR = "##";

	/**
	 * Function to convert a given name string into a formated name string.
	 * @param string name that needed to be converted.
	 * @return formated name string.
	 */
	public static String convertToProperNameFormat(String string){
		String stringFormated = "";
		if (HelperClass.isValidString(string)) {
			String stringUnFormated = string;
			char firstCharacter = Character.toUpperCase(stringUnFormated.charAt(0));
			stringFormated = ""+firstCharacter;
			for (int i = 1; i < stringUnFormated.length() ;i++) {
				char ch = stringUnFormated.charAt(i);
				stringFormated += ch;
			}
		}
		return stringFormated;
	}

	/**
	 * Function to convert the given size in dp.
	 * @param mContext context of the activity.
	 * @param iValue value the needed to be changed.
	 * @return value in dp
	 */
	public static int getSizeInDp(Context mContext,int iValue) {
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iValue, mContext.getResources().getDisplayMetrics()));
	}

	/**
	 * Function to convert the given size in sp.
	 * @param mContext context of the activity.
	 * @param iValue value the needed to be changed.
	 * @return value in sp
	 */
	public static int getSizeInSp(Context mContext,int iValue) {
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, iValue, mContext.getResources().getDisplayMetrics()));
	}

	/**
	 * Function to convert dp value to px value.
	 * @param mContext context of the class
	 * @param pixels value in pixels
	 * @return value of pixel converted to dp.
	 */
	public static float getDpFromPx(Context mContext , float pixels){
		return pixels / mContext.getResources().getDisplayMetrics().density;
	}

	/**
	 * Function to convert px value to dp value.
	 * @param mContext context of the class
	 * @param pixels value in pixels
	 * @return value in px converted from dp.
	 */
	public static float getPxFromDp(Context mContext , float densityPixel){
		return densityPixel * mContext.getResources().getDisplayMetrics().density;
	}

	/**
	 * Function to get a list of team invite model which is sorted based on the last name
	 * @param listTeamInvitesContactModel list of model which is not sorted.
	 * @return a sorted list of team invite model which is sorted based on the last name.
	 */
	public static List<TeamInvitesContactModel> getSortedListBasedOnLastName(List<TeamInvitesContactModel> listTeamInvitesContactModel){
		List<TeamInvitesContactModel> listContactModels = new ArrayList<TeamInvitesContactModel>();
		if (listTeamInvitesContactModel.size() > 0) {
			int size =  listTeamInvitesContactModel.size();
			String []unSortedLastName = new String[size];
			for (int i = 0; i < size; i++) {
				StringBuilder name = new StringBuilder();
				name.append(listTeamInvitesContactModel.get(i).getUser().getLastname());
				name.append(STRING_SAPERATOR);
				name.append(listTeamInvitesContactModel.get(i).getUser().getFirstname());
				name.append(STRING_SAPERATOR);
				name.append(listTeamInvitesContactModel.get(i).getUser().getEmail());
				unSortedLastName[i] = name.toString();
			}
			Arrays.sort(unSortedLastName);
			for (int i = 0; i < unSortedLastName.length; i++) {
				String []names = unSortedLastName[i].split(STRING_SAPERATOR);
				TeamRoasterDataModel mTeamRoasterDataModel = new TeamRoasterDataModel();
				DetailsModel detailsModelUser = mTeamRoasterDataModel.new DetailsModel();
				detailsModelUser.setFirstname(names[1]);
				detailsModelUser.setLastname(names[0]);
				detailsModelUser.setEmail(names[2]);
				TeamInvitesContactModel mTeamInvitesContactModel = new TeamInvitesContactModel();
				mTeamInvitesContactModel.setUser(detailsModelUser);
				listContactModels.add(mTeamInvitesContactModel);
			}
		}
		return listContactModels;
	}

	/**
	 * Function to validate a string and check if the string is valid or not.
	 * @param string string that need to be validated.
	 * @return true if the string is valid and false if the string is invalid.
	 */
	public static boolean isValidString(String string){
		return string!=null && !string.trim().equalsIgnoreCase("null") && !string.trim().equalsIgnoreCase("");
	}

	/**
	 * checks for network authentication errors as well as the service call exceptions
	 * @param response String
	 * @return boolean isValidResult
	 */
	public static  boolean isValidJsonResult(String response) { 
		return response!= null && !response.contains("<HTML") && !response.equals("") && !response.contains("<!DOCTYPE") && !response.contains("/>");
	}	

	public static LayoutParams getLayoutParameters(int gravity, Activity context){
		Activity mContext=context;
		int screenWidth, screenHight;
		LayoutParams lp = null;
		WindowManager windowManager = mContext.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		display.getRealSize(size);
		screenHight = size.y;
		screenWidth = size.x;
		int difference = screenHight - screenWidth;
		lp = new FrameLayout.LayoutParams(screenWidth, difference/2, gravity);
		LogUtils.LOGI(TAG, screenHight+ "<<<<<<<<<<<<<<>>>>>>>>>>>>>>>" +screenWidth);
		return lp;
	}

	/**
	 * @return matrix with correct configuration for the front camera.
	 * Function to set correct height orientation and rotation for saving the image
	 * in sd card.
	 */
	public static Matrix setOrientationForFrontCamera() {
		Matrix matrix = new Matrix();
		matrix.postRotate(-90);
		//for mirror images
		float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1};
		Matrix matrixMirrorY = new Matrix();
		matrixMirrorY.setValues(mirrorY);
		matrix.postConcat(matrixMirrorY);
		return matrix;
	}

	/**
	 * @param bitmap
	 * @param matrix
	 * @return
	 * Function to get a square image once cropped from a rectangular image..
	 */
	public static Bitmap getSquareImage(Bitmap bitmap, Matrix matrix) {
		if (bitmap.getWidth() >= bitmap.getHeight()){
			return Bitmap.createBitmap
					(bitmap,bitmap.getWidth()/2 - bitmap.getHeight()/2,0,bitmap.getHeight(),bitmap.getHeight(), matrix, false);
		}else{
			return Bitmap.createBitmap
					(bitmap,0,bitmap.getHeight()/2 - bitmap.getWidth()/2,bitmap.getWidth(),bitmap.getWidth() ,matrix, false);
		}
	}

	/**
	 * @param type 
	 * @return
	 * Create a File for saving an image or video 
	 */
	public static File getOutputMediaFile(int type){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		final int MEDIA_TYPE_IMAGE = 1;
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TeamsApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.
		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +"IMG_"+ timeStamp + ".jpg");
		} else {
			return null;
		}
		return mediaFile;
	}

	/**
	 * Function to get a proper time format.
	 * @param durationSeconds duration time in seconds.
	 * @return string object having proper time format.
	 */
	public static String getProperTimeFormat(int durationSeconds) {
		return String.format("%02d:%02d", (durationSeconds % 3600) / 60, (durationSeconds % 60));
	}

	/**
	 * Function to display the list of objects in the params.
	 * @param listParams list of name value pairs.
	 */
	public static void displayListParams(List<NameValuePair> listParams) {
		if (BuildConfig.IS_DEVELOPER_TESTING) {
			if (listParams != null && listParams.size() > 0) {
				LogUtils.LOGI(TAG, "Sending parameters............");
				for (int i = 0; i < listParams.size(); i++) {
					LogUtils.LOGV(TAG, listParams.get(i).getName()+ " : "+listParams.get(i).getValue());
				}
			}
		}
	}

	/**
	 * Function to show the message for the functionality that is still in development mode.
	 * @param mContext context of the activity.
	 */
	public static void displayDevelopmentMessage(DashBoardActivity mContext) {
		if (mContext!=null) {
			Toast.makeText(mContext, "This module is in development mode", Toast.LENGTH_SHORT).show();		
		}
	}

	/**
	 * Function to set the Configuration to US English.
	 */
	public static void setLocale(Activity context){
		Locale locale = new Locale("en", "US");
		Resources res = context.getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = locale;
		res.updateConfiguration(conf, dm);
	}

	/**
	 * Function to remove Phone Number Formatting and return only numbers as String.
	 */
	public static String removePhoneNumberFormatting(String phoneNumber){
		if (CheckNullOrBlank.isNotNullOrBlank(phoneNumber)) {
			return phoneNumber.replace("(", "").replace(")", "").replace("+", "").replace(" ", "").replace("-", "");
		} else {
			return "";
		}
	}

	/**
	 * Function to check if the device has a camera or not.
	 * @param context context of class.
	 * @return true if the camera is present and false otherwise.
	 */
	public static boolean isCameraPresent(Context context) {
		PackageManager packageManager = context.getPackageManager();
		return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) || packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
	}

	/**
	 * Function to set focus from one object to another.
	 * @param floatingLabelFrom present focused object.
	 * @param floatingLabelTo to which the focus has to be transfered.
	 */
	public static void setFloatingLabelFocusFromTo(FloatingLabeLayout floatingLabelFrom, final FloatingLabeLayout floatingLabelTo) {
		floatingLabelFrom.getEditText().setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
					floatingLabelTo.getEditText().setFocusableInTouchMode(true);
					floatingLabelTo.getEditText().requestFocus();
					return true;
				}
				return false;
			}
		});		
	}

	/**
	 * Function to remove duplicate elements from the list of TeamInvitesContactModel
	 * @param list - list of TeamInvitesContactModel having duplicate elements.
	 * @return unique list of TeamInvitesContactModel
	 */
	public static List<TeamInvitesContactModel> removeDuplicatesFrom(List<TeamInvitesContactModel> list) {
		HashSet<TeamInvitesContactModel> mHashSet = new HashSet<TeamInvitesContactModel>(list);
		List<TeamInvitesContactModel> uniqueList = new ArrayList<TeamInvitesContactModel>(mHashSet);
		return uniqueList;
	}

	/**
	 * Function to check if the present url or string has image formate like JPG , PNG, JPEG
	 * @param imageUrl string 
	 * @return true if the image format is present else return false.
	 */
	public static boolean isUrlHasImageFormat(String imageUrl) {
		return imageUrl!=null && (imageUrl.toLowerCase().contains("png") || imageUrl.toLowerCase().contains("jpg") || imageUrl.toLowerCase().contains("jpeg"));
	}

	/**
	 * Function to remove duplicate elements from the array of string
	 * @param stringArray array of string having duplicate elements.
	 * @return unique array of string 
	 */
	public static String[] removeDuplicatesFrom(String[] stringArray) {
		Set<String> temp = new HashSet<String>(Arrays.asList(stringArray));
		String[] uniqueStringArray = temp.toArray(new String[temp.size()]);
		return uniqueStringArray;
	}

	/**
	 * Function to check if the mandatory fields are present or not.
	 * @param playerMandatoryFields list of string .
	 * @return true is all are present else will return false
	 */
	public static boolean isMandatoryFieldsPresent(List<String> listOfMandatoryFields) {
		for (int i = 0; i < listOfMandatoryFields.size(); i++) {
			if (!isValidString(listOfMandatoryFields.get(i))) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Function to check if duplication elements are present in array or not.
	 * @param contacts array of string
	 * @return true if duplication is present else return false.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isDuplicatePresent(String[] contacts) {
		List inputList = Arrays.asList(contacts);
		Set inputSet = new HashSet(inputList);
		if(inputSet.size()< inputList.size())
			return true;
		return false;
	}

	/**
	 * Function to get the application current version.
	 * @param applicationContext 
	 * @return float - currentVersion of the application.
	 */
	public static float getAppCurrentVersion(Context mContext) {
		float currentVersion = -1;
		if (mContext != null) {
			String currentAppVersion = "";
			PackageManager manager = mContext.getPackageManager();
			PackageInfo info;
			try {
				info = manager.getPackageInfo(mContext.getPackageName(), 0);
				currentAppVersion = info.versionName;
				LogUtils.LOGI(TAG, "app version =" + currentAppVersion);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			currentVersion = Float.parseFloat(currentAppVersion);
		}
		return currentVersion;
	}
}
