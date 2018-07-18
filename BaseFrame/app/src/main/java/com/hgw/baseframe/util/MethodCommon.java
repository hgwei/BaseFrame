package com.hgw.baseframe.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：常用方法类
 * @author hgw
 * 按照序号，方法目录如下（添加新方法，序号自增）：
 * 1、描述：提供精确的小数位四舍五入处理
 * 2、描述：将字符串转换成Bitmap类型 
 * 3、描述： 将Bitmap转换成字符串 
 * 4、描述：判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的 
 * 5、描述：跳转到GPS启动页面
 * 6、描述：根据图片路径，生成bitmap
 * 7、描述：计算inSampleSize方法，配合getBitmap()方法
 * 8、描述：把Bitmap保存到指定路径 
 * 9、描述：判断某个服务是否正在运行的方法 
 * 10、描述：验证网络是否可用
 * 11、描述：验证wifi是否可用
 * 12.1、描述：判断手机号码是否有效（调养家规则）
 * 12.2、描述：判断手机号码是否有效（客户端判断规则）
 * 13、描述：判断是不是一个合法的电子邮件地址
 * 14、描述：通过URI获取图片的绝对路径
 * 15、描述：Unicode转UTF-8
 * 16、描述：返回一个随机数
 * 17、描述：判断身份证号码是否正确
 * 18、描述：获取号码的运营商类型
 * 19、描述：判断邮政编码是否合法
 * 20、描述：根据经纬度计算距离
 * 21、描述：是否含有指定字符
 * 22、描述：判断用户名是否满足要求（26个大小字符及数字0-9,长度在 2-10之间）
 * 23、描述：判断密码是否满足安全要求（以字母开头，长度 在6-18之间，只能包含字符、数字和下划线）
 * 24、描述：获取手机Imei号
 * 25、描述：根据手机的分辨率从 dp 的单位 转成为 px(像素)
 * 26、描述：根据手机的分辨率从 px(像素) 的单位 转成为 dp
 * 27、描述：获取jar包中assets目录下的图片文件
 * 28、描述：隐藏手机号码中间4位（eg：180****3298）
 * 29、判断是特殊符号 true包含特殊字符  false不包含特殊字符
 * 30、获取url请求参数 指定name的value;
 * 31、判断是否是合法的json字符串
 * 32、判断是否安装微信客户端
 * 33、半角转换为全角
 * 34、获取View宽度
 * 35、获取View高度
 * 36、获取状态栏高度
 * 37、产生两个数之间的一个随机数（例如：100-1000之间）
 * 38、获取屏幕宽度
 * 39、获取屏幕高度
 * 40、获取版本号
 * 41、获取版本号(内部识别号)
 * 42、获取手机UUID
 * 43、获取手机厂商
 * 44、获取设备ID
 * */
public class MethodCommon {
	
	/**
     * 1、描述：提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字        
     * @param scale 小数点后保留几位    
     * @return 四舍五入后的结果
     */

    public static double round(Double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**2、描述：将字符串转换成Bitmap类型 */
	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**3、描述： 将Bitmap转换成字符串 */
	public static String bitmaptoString(Bitmap bitmap) {
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.DEFAULT);
		return string;
	}
	
	/** 
     * 4、描述：判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的 
     * @param context 
     * @return true 表示开启 
     * 需添加权限
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />  
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />  
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />  
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />  
     * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />  
     * <uses-permission android:name="android.permission.INTERNET" />
     */  
    public static final boolean isOPen(final Context context) {  
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）  
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）  
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);  
        if (gps || network) {  
            return true;  
        }  
        return false;  
    }  
    
    /** 
     * 5、描述：跳转到GPS启动页面
     * @param context 
     */  
    public static void openGPS(Activity context) {  
    	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    	context.startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
    }
	
	/**
     * 6、描述：根据图片路径，生成bitmap
     * @param
     * @return
     */
    public Bitmap getBitmap(Context context,String path) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, displayMetrics.widthPixels, displayMetrics.heightPixels);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(path, options);
        }
        return bitmap;
    }
    
    /**
     * 7、描述：计算inSampleSize方法，配合getBitmap()方法
     * 
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
	
	/** 8、描述：把Bitmap保存到指定路径 */
	public static void saveBitmap(String fileAddress, Bitmap bm) {
		File f = new File(fileAddress);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 
     * 9、描述：判断某个服务是否正在运行的方法 
     * @param mContext 
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService） 
     * @return true 代表正在运行，false代表服务没有正在运行 
     */  
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;  
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> myList = myAM.getRunningServices(300);
        if (myList.size() <= 0) {  
            return false;  
        }  
        for (int i = 0; i < myList.size(); i++) {  
            String mName = myList.get(i).service.getClassName().toString();  
            if (mName.equals(serviceName)) {  
                isWork = true;  
                break;  
            }  
        }  
        return isWork;  
    }  
	
	/**
     * 10、描述：验证网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if ((null == info) || (!info.isConnected())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 11、描述：验证wifi是否可用
     * @param context
     * @return
     */  
    public static boolean isWifiEnable(Context context) {  
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        return wifiManager.isWifiEnabled();  
    }
    
    /**
	 * 12.1、描述：判断手机号码是否有效（调养家规则）
	 * 条件1，11位数字
	 * 条件2，第一位数是1
	 * @return true 合法；false 不合法
	 */
	public static boolean isMobileNO(String number) {
		if(TextUtils.isEmpty(number) || number.length()!=11){
			return false;
		}
		if(TextUtils.equals(number.substring(0,1),"1")){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 12.2、描述：判断手机号码是否有效（客户端判断规则）
	 * @param phone
	 * @return rue 合法；false 不合法
	 */
	public static boolean isPhoneNumber(String phone) {
		Pattern p = Pattern.compile("^[1][3456789][0-9]{9}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	/**
     * 13、描述：判断是不是一个合法的电子邮件地址
     * @param email 
     * @return 
     */  
    public static boolean isValidEmail(String email) {  
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");  
        Matcher m = p.matcher(email);  
        return m.matches();  
    }  
	
	/**
	* 14、描述：通过URI获取图片的绝对路径
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath( final Context context, final Uri uri ) {
	    if ( null == uri ) return null;
	    final String scheme = uri.getScheme();
	    String data = null;
	    if ( scheme == null )
	        data = uri.getPath();
	    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
	        data = uri.getPath();
	    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
	        Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
	        if ( null != cursor ) {
	            if ( cursor.moveToFirst() ) {
	                int index = cursor.getColumnIndex( ImageColumns.DATA );
	                if ( index > -1 ) {
	                    data = cursor.getString( index );
	                }
	            }
	            cursor.close();
	        }
	    }
	    return data;
	}
	
	/**15、描述：Unicode转UTF-8*/
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
	
	/**
     * 16、描述：返回一个随机数
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }
    
    /**
	 * 17、描述：判断身份证号码是否正确
	 * @param identifyCard
	 * @return boolean
	 */
	public static boolean isIdentifyCard(String identifyCard){
//		Pattern p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		Pattern p = Pattern.compile("(\\d{14}|\\d{17})(\\d|[xX])$");
		Matcher m = p.matcher(identifyCard);
		if(m.matches()){
			int length=identifyCard.length();
			if(length==15 && identifyCard.substring(length-1,length).equalsIgnoreCase("x"))
				return false;
		}
		return m.matches();		
	}
	
	/**
	 * 18、描述：获取号码的运营商类型
	 * @param number
	 * @return
	 */
	public static String getMobileType(String number) {
		String type = "未知用户";
		Pattern p = Pattern.compile("^(([4,8]00))\\d{7}$");
		if (p.matcher(number).matches())
			return "企业电话";

		if (number.startsWith("+86")) {
			number = number.substring(3);
		}

		if (number.startsWith("+") || number.startsWith("0")) {
			number = number.substring(1);
		}
		
		number = number.replace(" ", "").replace("-", "");
		
		p = Pattern.compile("^((13[4-9])|(147)|(15[0-2,7-9])|(18[2,3,7,8]))\\d{8}$");
		if (p.matcher(number).matches())
			return "移动用户";

		p = Pattern.compile("^((13[0-2])|(145)|(15[5,6])|(18[5,6]))\\d{8}$");
		if (p.matcher(number).matches())
			return "联通用户";

		p = Pattern.compile("^((1[3,5]3)|(18[0,1,9]))\\d{8}$");
		if (p.matcher(number).matches())
			return "电信用户";

		p = Pattern.compile("^((17[0-9]))\\d{8}$");
		if (p.matcher(number).matches())
			return "虚拟运营端";

		if (number.length() >= 7 && number.length() <= 12)
			return "固话用户";

		return type;
	}
	
	/** 
     * 19、描述：判断邮政编码是否合法
     * @param zipcode 
     * @return 
     */  
    public static boolean isZipcode(String zipcode) {  
        Pattern p = Pattern.compile("[0-9]\\d{5}");  
        Matcher m = p.matcher(zipcode);  
        return m.matches();  
    } 
    
    /**
	 * 20、描述：根据经纬度计算距离
	 * @param longitude1
	 * @param latitude1
	 * @return 返回单位是米 
	 */
	 public static double getDistance(double longitude1, double latitude1,double longitude2, double latitude2) {  
		  double EARTH_RADIUS =6378137.0; //地球半径
		  double Lat1 = rad(latitude1);  
		  double Lat2 = rad(latitude2);  
		  double a = Lat1 - Lat2;  
		  double b = rad(longitude1) - rad(longitude2);  
		  double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
		    + Math.cos(Lat1) * Math.cos(Lat2)  
		    * Math.pow(Math.sin(b / 2), 2)));  
		  s = s * EARTH_RADIUS;  
		  s = Math.round(s * 10000) / 10000;  
		  return s;  
	 }
	 private static double rad(double d) {  
		 return d * Math.PI / 180.0;  
	 }
	 
	 /** 
	  * 21、描述：是否含有指定字符
	  * @param expression 源字符串
	  * @param text 指定字符串
	  * @return boolean (包含：true；不包含：false)
	  */  
	 public static boolean matchingText(String expression, String text) {  
	    Pattern p = Pattern.compile(expression);  
	    Matcher m = p.matcher(text);  
	    boolean b = m.matches();  
	    return b;  
	 } 
	 
	 /** 
	  * 22、描述：判断用户名是否满足要求（26个大小字符及数字0-9,长度在 2-10之间）
	  * @param name 
	  * @return 
	  */  
	  public static boolean isCorrectUserName(String name) {  
	    Pattern p = Pattern.compile("([A-Za-z0-9]){2,10}");  
	    Matcher m = p.matcher(name);  
	    return m.matches();  
	  }  
	 
	 /** 
	  * 23、描述：判断密码是否满足安全要求（以字母开头，长度 在6-18之间，只能包含字符、数字和下划线）
	  * @param pwd
	  * @return 
	  */  
	  public static boolean isCorrectUserPwd(String pwd) {  
	    Pattern p = Pattern.compile("\\w{6,18}");  
	    Matcher m = p.matcher(pwd);   
	    return m.matches();  
	  }
	  
	 /** 
	  * 24、描述：获取手机Imei号 
	  * @param context 
	  * @return 
	  */  
	  public static String getImeiCode(Context context) {  
	        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
	        return tm.getDeviceId();  
	  }
	  
	 /** 
	  * 25、描述：根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	  */  
	  public static int dip2px(Context context, float dpValue) {  
		  	final float scale = context.getResources().getDisplayMetrics().density;  
	    	return (int) (dpValue * scale + 0.5f);  
	  }
	    
	 /** 
	  * 26、描述：根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	  */  
	  public static int px2dip(Context context, float pxValue) {  
	    	final float scale = context.getResources().getDisplayMetrics().density;  
	    	return (int) (pxValue / scale + 0.5f);  
	  }
	    
	 /**
	  * 27、描述：获取jar包中assets目录下的图片文件
	  * @param context
	  * @param fileName 带后缀的文件名（eg：cdc_nw_back.png）
	  * @return Drawable
	  * */
	  public static Drawable getAssertDrawable(Context context,String fileName){
	    	Drawable mDrawable = null;
	    	AssetManager assetManager= context.getAssets();
			try {
				mDrawable = Drawable.createFromStream(assetManager.open(fileName), System.currentTimeMillis()+"");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mDrawable;
	  }
	    
	  /**
	   * 28、描述：隐藏手机号码中间4位（eg：180****3298）
	   * @param phone 11位的手机号码（eg：18011713298）
	   * @return （eg：180****3298）
	   * */
	   public static String hidePhoneMiddle(String phone){
	    	String hidePhone="";
	    	if(phone.length()==11){
	    		hidePhone=phone.substring(0,3) + "****" + phone.substring(7, phone.length());
	    	}else{
	    		hidePhone=phone;
	    	}
			return hidePhone;
	   }

	/**
	 * 29、判断是含有非（中文、符号、英文、数字）外的其它特殊字符
	 * @param str
	 * @return true包含  false不包含
	 */
	public static boolean isSpecialString(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
//		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		String regEx = "^[\u4e00-\u9fa5]|[`_~!@#$%^&*()+-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+-|{}【】‘；：”“’。，、？]|[A-Za-z0-9]$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return !m.find();
	}

	/**
	 * 29.1、判断是含有非（符号、英文、数字）外的其它特殊字符
	 * @param str
	 * @return true包含  false不包含
	 */
	public static boolean isSpecialString2(String str) {
		String regEx = "[`_~!@#$%^&*()+-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+-|{}【】‘；：”“’。，、？]|[A-Za-z0-9]$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return !m.find();
	}

	/**
	 * 29.2、判断是含有非（中文、符号、英文、数字）外的其它特殊字符 区别：允许空格
	 * @param str
	 * @return true包含  false不包含
	 */
	public static boolean isSpecialString3(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
//		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		String regEx = "^[\u4e00-\u9fa5]|[ `_~!@#$%^&*()+-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+-|{}【】‘；：”“’。，、？]|[A-Za-z0-9]$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return !m.find();
	}
	/**
	 * 29.2、判断是含有非（中文、符号、英文、数字）外的其它特殊字符 区别：允许空格和换行
	 * @param str
	 * @return true包含  false不包含
	 */
	public static boolean isSpecialString4(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
//		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		String regEx = "^[\u4e00-\u9fa5]|[ `_~!@#$%^&*()+-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+-|{}【】‘；：”“’。，、？\\n]|[A-Za-z0-9]$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return !m.find();
	}
	/***
	 * 30、获取url请求参数 指定name的value;
	 * @param url
	 * @param name
	 * @return
	 */
	public static String getValueByName(String url, String name) {
		String result = "";
		int index = url.indexOf("?");
		String temp = url.substring(index + 1);
		String[] keyValue = temp.split("&");
		for (String str : keyValue) {
			if (str.contains(name)) {
				result = str.replace(name + "=", "");
				break;
			}
		}
		return result;
	}

	/***
	 * 31、判断是否是合法的json字符串
	 * @param jsonString
	 * 规则：初步判断，“{}”开头结尾，“[]”开头结尾
	 * @return
	 */
	public static boolean isJsonString(String jsonString) {
		if(TextUtils.isEmpty(jsonString)){
			return false;
		}
		String startString=jsonString.substring(0,1);
		String endString=jsonString.substring(jsonString.length()-1,jsonString.length());
		if(startString.equals("{") && endString.equals("}") || startString.equals("[") && endString.equals("]")){
			return true;
		}else{
			return false;
		}
	}

	/***
	 * 32、判断是否安装微信客户端
	 * @param context
	 * @return true：安装，false：未安装
	 */
	/**判断是否安装微信客户端*/
	public static boolean isWeixinAvilible(Context context) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				if (pn.equals("com.tencent.mm")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 33、半角转换为全角
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 34、获取View宽度
	 * @param view 控件
	 * @return
	 * */
	public static int getViewWidth(View view) {
		view.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
				, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int countWidth = view.getMeasuredWidth();
		return countWidth;
	}

	/**
	 * 35、获取View高度
	 * @param view 控件
	 * @return
	 * */
	public static int getViewHeight(View view) {
		view.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
				, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int countHeight = view.getMeasuredHeight();
		return countHeight;
	}

	/**
	 * 36、获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}

	/**
	 * 37、产生两个数之间的一个随机数（例如：100-1000之间）
	 * @param min 最小数
	 * @param max 最大数
	 * @return
	 * */
	public static int randomNumber(int min, int max){
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}

	/**
	 * 38、获取屏幕宽度
	 * @param mContext
	 * @return
	 */
	public static int getPMWidth(Context mContext) {
		if(mContext==null)
			return 1080;
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 39、获取屏幕高度
	 * @param mContext
	 * @return
	 */
	public static int getPMHeight(Context mContext) {
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 40、获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersionName(Context mContext) {
		try {
			PackageManager manager = mContext.getPackageManager();
			PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 41、获取版本号(内部识别号)
	 * @return 当前应用的版本号，返回0，代表获取失败
	 */
	public static int getVersionCode(Context mContext) {
		try {
			PackageManager manager = mContext.getPackageManager();
			PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 42、获取手机UUID
	 * @return UUID
	 */
	public static String getUUID(Context mContext) {
		TelephonyManager tm=(TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		String tmDevice,tmSerial,androidId;
		tmDevice=""+tm.getDeviceId();
		tmSerial=""+tm.getSimSerialNumber();
		androidId=""+ Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
		UUID deviceUuid=new UUID(androidId.hashCode(),((long)tmDevice.hashCode()<<32)|tmSerial.hashCode());
		return deviceUuid.toString();
	}

	/**
	 * 43、获取手机厂商
	 * @return MODEL （列如Redmi Note 3）
	 */
	public static String getDevice() {
		return Build.MODEL;
	}

	/**
	 * 44、获取设备ID
	 * @return DeviceId
	 */
	public static String getDeviceId(Context mContext) {
		TelephonyManager telephonyManager=(TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}





}
