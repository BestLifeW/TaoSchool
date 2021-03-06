package com.wtc.xmut.taoschool.ui.activity;

import android.app.Dialog;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.utils.FileUtil;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.xutils.common.util.DensityUtil.getScreenHeight;
import static org.xutils.common.util.DensityUtil.getScreenWidth;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PublishActivity";
    private static final int CROP_FROM_CAMERA = 5; //拍照裁剪
    private static final int CROP_FROM_PHOTO = 6;
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

    @BindView(R.id.tv_classify)
    TextView tv_classify;
    String[] words2 = new String[]{"服饰", "彩妆", "珠宝", "数码", "运动", "汽车", "生活", "家具", "其他"};
    String[] pinyin = new String[]{"fushi", "caizhuang", "zhubao", "shuma", "yundong", "qiche", "shenghuo", "jiaju", "qita"};
    private String fileName;
    private String newmoney;
    private String oldmoney;
    private String srcPath;
    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static final int CROP_CODE = 3;
    private XutilsUtils xutilsUtils;
    private String realFilePath;
    private String username;
    private static final int SCALE = 5;//照片缩小比例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        username = PrefUtils.getString(getApplication(), PrefUtils.USER_NUMBER, "");
        xutilsUtils = XutilsUtils.getInstance();
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

    @OnClick(R.id.rl_select)
    public void chooseFenLei() {

        DialogUIUtils.showSingleChoose(PublishActivity.this, "请选择分类", 0, words2, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                if (text != null) {
                    tv_classify.setText(text);
                }
            }
        }).show();
    }


    @OnClick(R.id.btn_publish)
    public void OkPublish() {
        //获取系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        HashMap<String, String> Shop = new HashMap<>();
        String title = et_title.getText().toString().trim();
        String description = et_summary.getText().toString().trim();
        String classify = tv_classify.getText().toString().trim();

        if (!(title.trim().equals("") || description.trim().equals("") || username.trim().equals(""))) {
            if (newmoney == null || oldmoney == null) {
                newmoney = "0";
                oldmoney = "0";
            }
            Shop.put("shopname", title);
            Shop.put("description", description);
            Shop.put("username", username);

            Shop.put("price", newmoney);
            Shop.put("oldprice", oldmoney);
            //插入分类
            for (int i = 0; i < words2.length; i++) {
                if (classify == words2[i]) {
                    Shop.put("category", pinyin[i]);
                }
            }
            Shop.put("shoptime", str);
        } else {
            SnackbarUtils.ShowSnackbar(getCurrentFocus(), "请输入完整信息");
            return;
        }
        HashMap<String, File> picmap = new HashMap<>();
        if (!(realFilePath == null)) {
            File file = new File(realFilePath);
            if (!file.getName().equals("")) {
                picmap.put("file", file);
                final Dialog show = DialogUIUtils.showLoading(getApplicationContext(), "发布中...", false, false, false, true).show();
                xutilsUtils.upLoadFile(ServerApi.SHOPADD, Shop, picmap, new XutilsUtils.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        Log.i(TAG, "onResponse: ");
                        show.dismiss();
                        PublishActivity.this.finish();
                    }

                    @Override
                    public void onResponseFail() {
                        SnackbarUtils.ShowSnackbar(getCurrentFocus(), "发布失败，请检查网络");
                        show.dismiss();
                    }
                });
            }
        } else {
            SnackbarUtils.ShowSnackbar(getCurrentFocus(), "请选择一张图片");
        }
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

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断存储卡是否可以用
        if (hasSdcard()) {
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + System.currentTimeMillis() + ".jpg";
            File file = new File(fileName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(intent, CAMERA_CODE);

    }

    private boolean hasSdcard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    private void getPicForSelect() {
        //构建一个内容选择的Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //设置选择类型为图片类型
        intent.setType("image/*");
        //打开图片选择
        startActivityForResult(intent, GALLERY_CODE);

     /*   Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image*//*");
        startActivityForResult(intent, GALLERY_CODE);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_CODE:
                //保存裁剪后的图片,这里用到了一个裁剪的第三方库ucrop
                if (hasSdcard()) {
                    File file = new File(fileName);
                    if (file.exists()) {
                        //ucrop裁剪
                        crop(Uri.fromFile(file), CROP_FROM_CAMERA);
                        //系统裁剪
//                            crop2(Uri.fromFile(file), 400, 400);
                    }
                } else {

                    Toast.makeText(this, "未找到存储卡,无法存储照片", Toast.LENGTH_SHORT).show();
                }
                break;

            case GALLERY_CODE:
                if (data == null) {
                    return;
                } else {
                    //用户从图库选择图片后会返回所选图片的Uri
                    Uri uri;
                    //获取到用户所选图片的Uri
                    uri = data.getData();
                    //返回的Uri为content类型的Uri,不能进行复制等操作,需要转换为文件Uri
                    uri = convertUri(uri);
                    crop(uri, CROP_FROM_PHOTO);
                    iv_addpic.setImageURI(uri);
                    realFilePath = FileUtil.getRealFilePath(getApplication(), uri);
                    Log.i(TAG, "本地: " + realFilePath);
                }
                break;
            case CROP_CODE:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //获取到裁剪后的图像
                        Bitmap bm = extras.getParcelable("data");
                        iv_addpic.setImageBitmap(bm);
                    }
                }
                break;
            case CROP_FROM_CAMERA:   //拍照裁剪
            case CROP_FROM_PHOTO:    //相册裁剪
                //裁剪之后显示照片
                if (data == null) {
                    return;
                } else {
                    Uri uri = UCrop.getOutput(data);
//                    Uri uri = Uri.fromFile(new File(fileName));
                    Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
                    iv_addpic.setImageBitmap(bitmap);
                    if (requestCode == CROP_FROM_CAMERA) {  //如果是拍照,则删除原图，只保存裁剪后的图片
                        File oldFile = new File(fileName);
                        if (oldFile.exists()) {
                            oldFile.delete();   //删除原图
                        }
                    }
                    realFilePath = FileUtil.getRealFilePath(getApplication(), uri);
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
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
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
     * 将content类型的Uri转化为文件类型的Uri
     *
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri) {
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

    private void crop(Uri uri, int requestCode) {

        //计算压缩后图片的宽和高

        int scale = getinSamlpeSize(uri);

        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);  //设置裁剪框可控制
        options.setHideBottomControls(true);  //隐藏裁剪界面底部View
//        options.setShowCropFrame(true);
//        options.setCropGridRowCount(0);      //设置网格数量
        options.setToolbarTitle("图片裁剪");
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));   //状态栏颜色
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));  //标题栏背景色
//        options.setToolbarWidgetColor(Color.BLACK);
        options.withAspectRatio(16, 9);   //裁剪框初始化比例
        options.withMaxResultSize(getScreenWidth() / scale, getScreenHeight() / scale); //裁剪后图片的最大分辨率
//        options.setCircleDimmedLayer(false);
        UCrop.of(uri, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg")))//裁剪后图片存储的路径
                .withOptions(options)
                .start(this, requestCode);
    }

    //获取压缩比例
    private int getinSamlpeSize(Uri uri) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(), newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int inSampleSize = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            inSampleSize = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            inSampleSize = (int) (newOpts.outHeight / hh);
        }

        if (inSampleSize <= 0)
            inSampleSize = 1;
        return inSampleSize;
    }


}
