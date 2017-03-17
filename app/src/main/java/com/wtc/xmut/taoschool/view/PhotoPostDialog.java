package com.wtc.xmut.taoschool.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.PhotoUtils;


public class PhotoPostDialog {
	private Dialog photoDialog;
	private Activity mActivity;
	public PhotoPostDialog(Activity mActivity){
		this.mActivity=mActivity;
		photoDialog = new Dialog(mActivity, R.style.dialog_untran);
		View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_photo_post,null);
		photoDialog.setContentView(view);
		photoDialog.getWindow().setAttributes(getLayoutParams(mActivity));
		view.findViewById(R.id.tv_camera_post).setOnClickListener(new OnCameraClick());
		view.findViewById(R.id.tv_native_post).setOnClickListener(new OnNativeClick());
		photoDialog.show();
	}
	
	class OnCameraClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PhotoUtils.openCameraStartActivityForResult(mActivity);
			dissmiss();
		}
	}
	
	class OnNativeClick implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PhotoUtils.openGalleryStartActivityForResult(mActivity);
			dissmiss();
		}
	}
	
	public void dissmiss(){
		if (photoDialog.isShowing()) {
			photoDialog.dismiss();
		}
	}
	
	private LayoutParams getLayoutParams(Activity mActivity) {
		// TODO Auto-generated method stub
		int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
  		LayoutParams layoutParams = photoDialog.getWindow().getAttributes();
		layoutParams.width=(int) (width*0.7);
		return layoutParams;
	}
	
}
