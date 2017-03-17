package com.wtc.xmut.taoschool.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * POST上传文件
 * 
 * @author aokunsang
 * @Date 2011-12-6
 */
public class PostFile {

	private static PostFile postFile = new PostFile();

	public PostFile() {
	}

	public static PostFile getInstance() {
		return postFile;
	}



	/**
	 * 直接通过HTTP协议提交数据到服务器,实现表单提交功能
	 * 
	 * @time 2012/8/3
	 * @YPF 添加了以流的方式带参数post提交相册图片
	 */
	public static String postPhoto(String actionUrl, String formName,
                                   HashMap<String, String> params, byte[] buffer) {
		try {
			String BOUNDARY = "--------------et567z"; // 数据分隔线
			String MULTIPART_FORM_DATA = "Multipart/form-data";
			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);// 不使用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
					+ ";boundary=" + BOUNDARY);
			StringBuilder sb = new StringBuilder();
			// 上传的表单参数部分，格式请参考文章
			for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			outStream.write(sb.toString().getBytes());// 发送表单字段数据

			// 上传的文件部分，格式请参考文章

			StringBuilder split = new StringBuilder();
			split.append("--");
			split.append(BOUNDARY);
			split.append("\r\n");
			split.append("Content-Disposition: form-data;name=\"" + formName
					+ "\";filename=\"temp.jpg\"\r\n");
			split.append("Content-Type: image/jpeg\r\n\r\n");
			outStream.write(split.toString().getBytes());
			outStream.write(buffer, 0, buffer.length);
			outStream.write("\r\n".getBytes());
			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 数据结束标志
			outStream.write(end_data);
			outStream.flush();

			/* 取得Response内容 */
			InputStream is = conn.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */

			outStream.close();
			conn.disconnect();

			return b.toString().trim();
		} catch (Exception e) {
			Log.e("--------上传图片错误--------", "" + e.getMessage().toString());
			return null;
		}
	}

	/**
	 * 直接通过HTTP协议提交数据到服务器,实现表单提交功能
	 * 
	 * @param actionUrl
	 *            上传路径
	 * @param params
	 *            请求参数 key为参数名,value为参数值
	 * @param filename
	 *            上传文件
	 */
	public static String post(String actionUrl, HashMap<String, String> params,
                              String filename) {
		try {
			String BOUNDARY = "--------------et567z"; // 数据分隔线
			String MULTIPART_FORM_DATA = "Multipart/form-data";
			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);// 不使用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
					+ ";boundary=" + BOUNDARY);
			StringBuilder sb = new StringBuilder();
			// 上传的表单参数部分，格式请参考文章
			for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			outStream.write(sb.toString().getBytes());// 发送表单字段数据

			// 上传的文件部分，格式请参考文章

			StringBuilder split = new StringBuilder();
			split.append("--");
			split.append(BOUNDARY);
			split.append("\r\n");
			split.append("Content-Disposition: form-data;name=\"file\";filename=\"temp\"\r\n");
			split.append("Content-Type: image/jpeg\r\n\r\n");
			outStream.write(split.toString().getBytes());
			if (filename != null) {
				byte[] content = readFileImage(filename);
				outStream.write(content, 0, content.length);
			}
			outStream.write("\r\n".getBytes());
			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 数据结束标志
			outStream.write(end_data);
			outStream.flush();

			/* 取得Response内容 */
			InputStream is = conn.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */

			outStream.close();
			conn.disconnect();

			return b.toString().trim();
		} catch (Exception e) {
			Log.e("--------上传图片错误--------", "" + e.getMessage().toString());
			return null;
		}
	}

	public static String post(String actionUrl, HashMap<String, String> params,
                              String formName, String filename) {
		try {
			String BOUNDARY = "--------------et567z"; // 数据分隔线
			String MULTIPART_FORM_DATA = "Multipart/form-data";
			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);// 不使用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
					+ ";boundary=" + BOUNDARY);
			StringBuilder sb = new StringBuilder();
			// 上传的表单参数部分，格式请参考文章
			for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			outStream.write(sb.toString().getBytes());// 发送表单字段数据

			// 上传的文件部分，格式请参考文章

			StringBuilder split = new StringBuilder();
			split.append("--");
			split.append(BOUNDARY);
			split.append("\r\n");
			// split.append("Content-Disposition: form-data;name=\"file\";filename=\"temp\"\r\n");
			split.append("Content-Disposition: form-data;name=\"" + formName
					+ "\";filename=\"temp.jpg\"\r\n");
			split.append("Content-Type: image/jpeg\r\n\r\n");
			outStream.write(split.toString().getBytes());
			if (filename != null) {
				byte[] content = readFileImage(filename);
				outStream.write(content, 0, content.length);
			}
			outStream.write("\r\n".getBytes());
			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 数据结束标志
			outStream.write(end_data);
			outStream.flush();

			/* 取得Response内容 */
			InputStream is = conn.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */

			outStream.close();
			conn.disconnect();

			return b.toString().trim();
		} catch (Exception e) {
			Log.e("--------上传图片错误--------", "" + e.getMessage().toString());
			return null;
		}
	}

	public static String postJsutForWritePrivateMsgActivity(String actionUrl,
                                                            HashMap<String, String> params, String filename) throws IOException {
		String BOUNDARY = "--------------et567z"; // 数据分隔线
		String MULTIPART_FORM_DATA = "Multipart/form-data";
		URL url = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);// 不使用Cache
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
				+ ";boundary=" + BOUNDARY);
		StringBuilder sb = new StringBuilder();
		// 上传的表单参数部分，格式请参考文章
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			sb.append(entry.getValue());
			sb.append("\r\n");
		}
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());// 发送表单字段数据

		// 上传的文件部分，格式请参考文章

		StringBuilder split = new StringBuilder();
		split.append("--");
		split.append(BOUNDARY);
		split.append("\r\n");
		split.append("Content-Disposition: form-data;name=\"file\";filename=\"temp\"\r\n");
		split.append("Content-Type: image/jpeg\r\n\r\n");
		outStream.write(split.toString().getBytes());
		if (filename != null) {
			byte[] content = readFileImage(filename);
			outStream.write(content, 0, content.length);
		}
		outStream.write("\r\n".getBytes());
		byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 数据结束标志
		outStream.write(end_data);
		outStream.flush();

		/* 取得Response内容 */
		InputStream is = conn.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		/* 将Response显示于Dialog */

		outStream.close();
		conn.disconnect();

		return b.toString().trim();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 上传服务器代码
	@SuppressWarnings("resource")
	public static byte[] readFileImage(String filename) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(filename));
		int len = bufferedInputStream.available();
		byte[] bytes = new byte[len];
		int r = bufferedInputStream.read(bytes);
		if (len != r) {
			bytes = null;
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}

	/**
	 * 上传手机文件
	 * 
	 * @param uploadUrl 上传地址
	 * @return
	 */
	public static String postmyFile(String uploadUrl, String formName,
                                    HashMap<String, String> params, File file) {
		String end = "\r\n";
		String Hyphens = "--";
		String boundary = "--------------et567z";
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			// // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
			// // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
			// httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
			// 允许输入输出流
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			// 使用POST方法
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			StringBuilder sb = new StringBuilder();
			// 上传的表单参数部分，格式请参考文章
			for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
				sb.append("--");
				sb.append(boundary);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}

			DataOutputStream dos = new DataOutputStream(
					httpURLConnection.getOutputStream());
			dos.write(sb.toString().getBytes());// 发送表单字段数据

			// 上传的文件部分，格式请参考文章
			dos.writeBytes(Hyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data;name=\"" + formName
					+ "\";" + "filename=\"" + file.getName() + "\"\r\n");

			dos.writeBytes(end);

			InputStream input = new FileInputStream(file);
			int size = 1024;
			byte[] buffer = new byte[size];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = input.read(buffer)) != -1) {
				dos.write(buffer, 0, length);
			}
			input.close();

			dos.writeBytes(end);
			dos.writeBytes(Hyphens + boundary + Hyphens + end);
			dos.flush();

			/* 取得Response内容 */
			InputStream is = httpURLConnection.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			// dos.close();
			is.close();
			return b.toString().trim();

		} catch (Exception e) {
			Log.e("--------上传文件错误--------", e.getMessage().toString());
			return null;
		}
	}
}