package com.wtc.xmut.taoschool.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PublishActivity";

    @BindView(R.id.et_title)
    EditText et_title;

    @BindView(R.id.et_summary)
    EditText et_summary;

    @BindView(R.id.tv_money)
    TextView tv_money;

    @BindView(R.id.tv_oldmoney)
    TextView tv_oldmoney;

    @BindView(R.id.btn_publish)
    Button btn_publish;

    @BindView(R.id.rl_money)
    RelativeLayout rl_money;

    @BindView(R.id.iv_addpic)
    ImageView iv_addpic;

    private String newmoney;
    private String oldmoney;
    private String srcPath;
    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static final int CROP_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        intView();
    }

    private void intView() {
        iv_addpic = (ImageView) findViewById(R.id.iv_addpic);
        iv_addpic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addpic:
                getPic();
                break;
        }
    }

    @OnClick(R.id.rl_money)
    public void inputMoney() {
        DialogUIUtils.showAlert(this, null, "请输入出售的价格", "价格", "原先的价格", "确定", "取消", false, false, false, new DialogUIListener() {
            @Override
            public void onPositive() {
            }

            @Override
            public void onNegative() {
            }

            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {
                newmoney = (String) input1;
                oldmoney = (String) input2;
                if (!(TextUtils.isEmpty(newmoney) || TextUtils.isEmpty(oldmoney))) {
                    tv_money.setText("￥：" + newmoney + "元");
                    tv_oldmoney.setText("原价：￥" + oldmoney + "元");
                    tv_oldmoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    SnackbarUtils.ShowSnackbar(getCurrentFocus(), "未输入任何数据");
                }
            }
        }).show();
    }

    @OnClick(R.id.btn_publish)
    public void OkPublish() {
        DialogUIUtils.showLoading(getApplicationContext(), "提交中...", false, false, false, true).show();
    }


    @OnClick(R.id.iv_addpic)
    public void getPic() {
        ArrayList<TieBean> tieBeen = new ArrayList<>();
        tieBeen.add(new TieBean("拍照上传", 1));
        tieBeen.add(new TieBean("本地上传", 2));
        DialogUIUtils.showMdBottomSheet(getApplication(), true, null, tieBeen, 1, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                switch (position) {
                    case 0:
                        getPicForCamera();
                        break;
                    case 1:
                        getPicForSelect();
                        break;
                }
            }
        }).show();
    }

    private void getPicForCamera() {

        //构建隐式Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //调用系统相机
        startActivityForResult(intent, CAMERA_CODE);

    }

    private void getPicForSelect() {
        //构建一个内容选择的Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //设置选择类型为图片类型
        intent.setType("image/*");
        //打开图片选择
        startActivityForResult(intent, GALLERY_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_CODE:
                //用户点击了取消
                if(data == null){
                    return;
                }else{
                    Bundle extras = data.getExtras();
                    if (extras != null){
                        //获得拍的照片
                        Bitmap bm = extras.getParcelable("data");
                        //将Bitmap转化为uri
                        Uri uri = saveBitmap(bm, "temp");
                        //启动图像裁剪
                        startImageZoom(uri);
                    }
                }
                break;
            case GALLERY_CODE:
                if (data == null){
                    return;
                }else{
                    //用户从图库选择图片后会返回所选图片的Uri
                    Uri uri;
                    //获取到用户所选图片的Uri
                    uri = data.getData();
                    //返回的Uri为content类型的Uri,不能进行复制等操作,需要转换为文件Uri
                    uri = convertUri(uri);
                    iv_addpic.setImageURI(uri);
                    startImageZoom(uri);
                }
                break;
            case CROP_CODE:
                if (data == null){
                    return;
                }else{
                    Bundle extras = data.getExtras();
                    if (extras != null){
                        //获取到裁剪后的图像
                        Bitmap bm = extras.getParcelable("data");
                        iv_addpic.setImageBitmap(bm);
                    }
                }
                break;
            default:
                break;
        }


//      n =1;
    }

    /**
     * 将Bitmap写入SD卡中的一个文件中,并返回写入文件的Uri
     *
     * @param bm
     * @param dirPath
     * @return
     */
    private Uri saveBitmap(Bitmap bm, String dirPath) {
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        //新建文件存储裁剪后的图片
        File img = new File(tmpDir.getAbsolutePath() + "/avator.png");
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 通过Uri传递图像信息以供裁剪
     * @param uri
     */
    private void startImageZoom(Uri uri){
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_CODE);
    }
    /**
     * 将content类型的Uri转化为文件类型的Uri
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri){
        InputStream is;
        try {
            //Uri ----> InputStream
            is = getContentResolver().openInputStream(uri);
            //InputStream ----> Bitmap
            Bitmap bm = BitmapFactory.decodeStream(is);
            //关闭流
            is.close();
            return saveBitmap(bm, "temp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
