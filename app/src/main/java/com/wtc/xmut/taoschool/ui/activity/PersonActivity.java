package com.wtc.xmut.taoschool.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.listener.DialogUIListener;
import com.google.gson.Gson;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.User;
import com.wtc.xmut.taoschool.utils.FileUtil;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.SnackbarUtils;
import com.wtc.xmut.taoschool.utils.ToastUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.CircleImageView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static org.xutils.common.util.DensityUtil.getScreenHeight;
import static org.xutils.common.util.DensityUtil.getScreenWidth;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.rl_account_password)
    RelativeLayout mrl_account_password;

    @BindView(R.id.rl_chooseusericon)
    RelativeLayout rl_chooseusericon;

    @BindView(R.id.iv_user_icon)
    CircleImageView iv_user_icon;
    private String fileName;

    private static final int CROP_FROM_CAMERA = 5; //拍照裁剪
    private static final int CROP_FROM_PHOTO = 6;
    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static final int CROP_CODE = 3;
    private XutilsUtils xutilsUtils;
    private String realFilePath;
    private static final int SCALE = 5;//照片缩小比例
    private static final String TAG = "PersonActivity";
    private XutilsUtils utils;
    private String username;
    private List<String> list = new ArrayList<String>();
    private User user;
    private RelativeLayout rl_setting_address;
    private ArrayAdapter<String> adapter;
    private String collage = "";
    private TextView etdormitory;
    private TextView etfloor;

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        rl_chooseusericon.setOnClickListener(this);
        utils = XutilsUtils.getInstance();
        username = PrefUtils.getString(getApplicationContext(), PrefUtils.USER_NUMBER, "");
        rl_setting_address = (RelativeLayout) findViewById(R.id.rl_setting_address);

        initData();
    }

    /**
     * 初始化网络数据
     * utils
     */
    private void initData() {
        utils.get(ServerApi.FINDUSERBYNAME + username, null, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                user = gson.fromJson(result, User.class);
                Glide.with(getApplicationContext()).load(ServerApi.SHOWPIC + user.getIconpath()).into(iv_user_icon);
            }

            @Override
            public void onResponseFail() {

            }
        });
    }

    @OnClick(R.id.rl_setting_address)
    void choose() {
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        final View view = View.inflate(getApplicationContext(), R.layout.item_setting, null);
        final Spinner sp_college = (Spinner) view.findViewById(R.id.sp_college);
        list.add("软件学院");
        list.add("机械工程学院");
        list.add("建筑工程学院");
        list.add("电子电气学院");
        list.add("计科学院");
        list.add("环境工程学院");
        list.add("数理学院");
        list.add("商学院");
        list.add("管理科学学院");
        list.add("文化传播学院");
        list.add("设计艺术学院");
        list.add("人文科学学院");
        list.add("空信科学学院");
        list.add("继续教育学院");
        list.add("其他学院");

        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        sp_college.setAdapter(adapter);
        sp_college.setSelection(list.lastIndexOf(user.getCollege()));
        sp_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                setCollage(list.get(pos));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        final Dialog show = DialogUIUtils.showCustomAlert(this, view).show();
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etfloor = (TextView) view.findViewById(R.id.et_floor);
                etfloor.setText(user.getFloor());
                String floor = etfloor.getText().toString();
                etdormitory = (TextView) view.findViewById(R.id.et_dormitory);
                etdormitory.setText(user.getDormitory());
                String dormitory = etdormitory.getText().toString();
                Toasty.success(getApplicationContext(), getCollage(), Toast.LENGTH_LONG).show();

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", null);
                map.put("username", username);
                map.put("college", getCollage());
                map.put("floor", floor);
                map.put("dormitory", dormitory);

                if (!(TextUtils.isEmpty(getCollage()) || TextUtils.isEmpty(floor) || TextUtils.isEmpty(dormitory))) {
                    utils.post(ServerApi.UPDATEUSER, map, new XutilsUtils.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            if (result.contains("成功")) {
                                Toasty.success(getApplicationContext(), "更新成功", Toast.LENGTH_LONG).show();
                                onRestart();
                            }
                        }

                        @Override
                        public void onResponseFail() {
                            Toasty.error(getApplicationContext(), "更新失败", Toast.LENGTH_LONG).show();
                            onRestart();
                        }
                    });
                }
                show.dismiss();
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });

    }


    @OnClick(R.id.rl_account_password)
    void setPassWord() {

        DialogUIUtils.showAlert(this, null, "修改密码", "旧密码", "新密码", "确定", "取消", false, false, false, new DialogUIListener() {
            @Override
            public void onPositive() {

            }

            @Override
            public void onNegative() {
            }

            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {


            }
        }).show();
    }

    @Override
    public void onClick(View v) {

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_CODE:
                if (hasSdcard()) {
                    File file = new File(fileName);
                    if (file.exists()) {
                        //ucrop裁剪
                        crop(Uri.fromFile(file), CROP_FROM_CAMERA);

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
                    //iv_user_icon.setImageURI(uri);
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
                        iv_user_icon.setImageBitmap(bm);
                    }
                }
                break;
            case CROP_FROM_CAMERA:   //拍照裁剪
            case CROP_FROM_PHOTO:    //相册裁剪
                //裁剪之后显示照片 //如果用户点了取消，那么 啥都不干，防止报空
                if (data == null) {
                    return;
                } else {
                    Uri uri = UCrop.getOutput(data);
                    if (uri != null) {
//                    Uri uri = Uri.fromFile(new File(fileName));
                        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
                        iv_user_icon.setImageBitmap(bitmap);
                        if (requestCode == CROP_FROM_CAMERA) {  //如果是拍照,则删除原图，只保存裁剪后的图片
                            File oldFile = new File(fileName);
                            if (oldFile.exists()) {
                                oldFile.delete();   //删除原图
                            }
                        }
                        realFilePath = FileUtil.getRealFilePath(getApplication(), uri);
                        //这里进行网络访问 进行头像的上传
                        updateIcon(realFilePath);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 头像上传
     *
     * @param realFilePath 头像文件路径
     */
    private void updateIcon(String realFilePath) {

        ToastUtils.showToast(getApplicationContext(), user.getId() + "");

        HashMap<String, String> user = new HashMap<>();
        user.put("username", username);

        HashMap<String, File> picmap = new HashMap<>();
        if (!(realFilePath == null)) {
            File file = new File(realFilePath);
            if (!file.getName().equals("")) {
                picmap.put("file", file);
                final Dialog show = DialogUIUtils.showLoading(getApplicationContext(), "发布中...", false, false, false, true).show();
                utils.upLoadFile(ServerApi.USERHEAD, user, picmap, new XutilsUtils.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        Log.i(TAG, "onResponse: ");
                        show.dismiss();

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
        options.withAspectRatio(1, 1);   //裁剪框初始化比例
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

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

}
