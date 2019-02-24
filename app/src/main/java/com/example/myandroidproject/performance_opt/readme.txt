

------------内存泄漏-------------
	当一个对象不再使用了，本该被回收时，
而有另外一个正在使用的对象持有他的引用，
从而导致该对象不能被回收，这块内存永远
无法重新利用。
	对象不再有任何引用的时候才会被回收。
内存分配的策略：
1.静态
	静态存储区：内存在程序编译的时
候就分配好了，这块内存在程序整个运行期间
一直存在。它主要存放静态数据、全局的static
数据和一些常量。

2.栈式
	在执行方法时，方法的一些局部变量可以放在
栈上面创建，方法执行结束后，这些存储单元就会
被自动释放掉。栈内存包括分配的运算是速度很快，
因为内置在处理器（寄存器）的里面，但是容量有限。

3.堆式
	动态内存分配，可以用new来申请分配一块内存，
在C/C++需要自己负责释放，java里面直接依赖GC。

区别：栈式一块连续的内存区域，大小是由操作系统
确定的。堆是不连续的内存区域，频繁的new/remove
会造成大量的内存碎片，这样会慢慢导致效率低下。

public class Main{
	int a = 1;
	Student s = new Student();//堆
	public void XXX(){
		int b = 1;//栈里面
		//s2:栈，new Student():堆
		Student s2 = new Student();
	}

}

	成员变量全部全部存储在堆中（包括基本数据类型，
引用和引用的对象实体），因为他们属于类，而类
是被new出来的。
	局部变量的基本数据类型和引用存储在栈当中，引用
的对象实体在堆中。

四大引用：
StrongReference强引用：
	回收时机：从不回收 使用：对象的一般保存
	生命周期：JVM停止的时候才会终止
SoftReference，软引用
	回收时机：当内存不足的时候；
	使用：SoftReference<String>结合ReferenceQueue构造有效期短；
	生命周期：内存不足时终止
WeakReference，弱引用
	回收时机：在垃圾回收的时候；
	使用：同软引用；
	生命周期：GC后终止
PhatomReference 虚引用
	回收时机：在垃圾回收的时候；
	使用：合ReferenceQueue来跟踪对象呗垃圾回收期回收的活动；


	为了防止内存溢出，处理一些占用内存较大，并且生命周期
长的对象的时候，可以尽量使用软引用和弱引用。


1.凭借工具结合自己的经验查找内存泄漏。
2.养成良好的编程习惯。
3.查看内存抖动情况
4.对照内存快照，查看被引用对象

排查工具（工具只是提供线索）：
Android Profile，
MAT(针对java层)

内存泄漏案例：
1、单例持有Activity引用，多次旋转屏幕，造成Activity内存泄漏
	结论：（Android Profiler）旋转3次：会在内存里面开辟三个
	MainActivity
实际上3次以上都只会有2个MainActivity。
MainActivity。当GC回收的时候会将除了第0个
和最后这一个留着其他的都会被回收。







-------------热修复--------------------
阿里系：Dexposed,AndFix;
	从底层C的二进制入手
	从新包和旧包包生成差量包，再由旧包和差量包生成新包
腾讯系：tinker
	Java类加载机制入手
	用新的dex文件覆盖旧的旧的dex文件

1.Android是如何加载classes.dex文件并启动程序的？

BaseDexClassLoader->findClass(name)
DexPahtList->findClass()
rivate Element dexElements[]
PathClassLoader:加载应用程序的dex
DexClassLoader:可以加载指定的某个dex文件，
限制条件：必须要在应用程序的目录下面

修复方案：
第一个版本：classes.dex
修复后的补丁包：classes2.dex(),包含了修复的
xxx.class。

这种方式也可以用于插件开发。

2.如何合并两个dex文件
将修复好的dex插入到dexElements的集合，
位置：出现bug的xxx.class所在的dex的前面。

List of dex/resource (class path) elements.
Element[] dexElements;存储的是dex的集合

最本质的实现原理：类加载器去加载某个类的时候，
是去dexElements里面从头往下查找的。
 fixed.dex,classes1.dex,classes2.dex,classes3.dex



 =================AS打包multidex(官方待验证)============================
1.
dependencies {
    compile 'com.android.support:multidex:1.0.1'
}

2.
defaultConfig {
        multiDexEnabled true
    }

3.
buildTypes {
release {
    multiDexKeepFile file('dex.keep')
    def myFile = file('dex.keep')
    println("isFileExists:"+myFile.exists())
    println "dex keep"
    minifyEnabled true
    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.txt'
}
}

4.
public class MyApplication extends Application{

	@Override
	protected void attachBaseContext(Context base) {
		// TODO Auto-generated method stub
		MultiDex.install(base);
	}

}



private void fixBug(){
	File fileDir = getDir
}


//TODO 待续
//----------FFMpeg音视频同步--------\\
音视频同步：

2个队列：
1、音频AVPacket Queue;
2、视频AVPacket Queue;

3个线程
1、生产者：
	read_stream线程负责不断的读取视频文件中AVPacket,
分别放到两个队列中
2、消费者：
	视频解码：从视频AVPacket Queue中获取元素，解码，绘制
	音频解码：从音频AVPacket Queue中获取元素，解码，播放
 生产效率往往大于消费效率


-----------------------UI优化--------------------------
渲染机制：
	60HZ=60fps是屏幕刷新的理想频率，16ms系统会发出
信号(VSYNC:垂直刷新)，渲染UI，就会正常绘制，如果
超过16ms没绘制完成就会造成掉帧卡顿。

CSYNC(垂直刷新/绘制):有两个概念：
	1：Refresh Rate(硬件):屏幕在一秒内刷新屏幕的次数，
	由硬件的参数决定，比如：60HZ.
	2:Frame Rate:GPU在一秒内绘制的整数，60fps
GPU:帮助我们将UI组件等计算成纹理Texture和三维图形，
	同时会使用OpenGL将纹理和Polygons缓存在GPU内存里面。
	硬件加速。

 卡顿是如何造成的
	 1.外部引起的：比如主线程中执行了耗时操作
	 2.View本身的卡顿 ：如自定义View

	 内存需要注意的问题：要防止内存抖动，因为
	 频繁GC会阻断主线程，造成卡顿。

 优化：
	 1.渲染性能的优化。
	 2.防止过度绘制。
	 3.适当情况下让GPU去绘制，减少CPU负担
	 4.要防止内存抖动（首先通过Android Moniter）
	 5.使用TraeView来确定详细的问题
	 6.CPU消耗严重
	 7.尽量避免过度绘制。
	 	将系统设置的背景去掉 getWindow().setBackGroudDrawable(null)，自定义view的剪裁;

TraceView:
	柱状图：第一个表示内存分配开始，第二个表示当前分配
	的内存使用结束，中间区域表示方法的执行时间，
	可以看到上面的方法名。颜色一样表示一个方法被反复调用

	左边线程面板，右边时间轴面板，下边是时间轴详细信息

几个重要参数：
	Incl Cpu Time:CPU占用时间或占比，包括调用其他方法
	Excl Cpu TIme:不包含调用其他方法，仅自身的消耗时间或占比
	Incl Real Time:真实CPU时间，不包括CPU以外的时间或占比，一般小于 Incl Cpu Time.
	Call+RecurCall:调用和递归重复调用的次数
	Cpu Time/Call:平均消耗CPU时间

path和svg

GPU：显卡处理器。
UI绘制：XML->CPU（计算成纹理和多维图形）->传递指令集到GPU->
OpenGl_ES绘制(缓存)
在UI绘制里面比较耗时的操作：
1、CPU计算时间
2、GPU进行栅格化

CPU优化：
CPU的优化，从减轻加工View对象成Polygons和Texture来下手
	View Hierarchy中包涵了太多的没有用的view，这些view根本就不会显示在屏幕上面，
	一旦触发测量和布局操作，就会拖累应用的性能表现。

	1.如何找出里面没用的view呢？或者减少不必要的view嵌套。
	工具：Hierarchy Viewer检测

	优化：
		1）当我们的布局是用的FrameLayout的时候，我们可以把它改成merge
			可以避免自己的帧布局和系统的ContentFrameLayout帧布局重叠造成重复计算(measure和layout)
	ViewStub：当加载的时候才会占用。不加载的时候就是隐藏的，仅仅占用位置。

	[hierarchyviewer]Unable to capture data for node
	android.widget.LinearLayout@e6fdb11 in window com.example.android.mobileperf.render/com.example.android.mobileperf.render.ChatumLatinumActivity on device 192.168.56.101:5555

	三个圆点分别代表：测量、布局、绘制三个阶段的性能表现。
	1）绿色：渲染的管道阶段，这个视图的渲染速度快于至少一半的其他的视图。
	2）黄色：渲染速度比较慢的50%。
	3）红色：渲染速度非常慢。

	优化思想:查看自己的布局，层次是否很深以及渲染比较耗时，然后想办法能否减少层级以及优化每一个View的渲染时间。


----------------------电量优化----------------------

耗电排名：
	1、屏幕 - 点亮的那一刻会出现峰值
	2、流量 - 唤醒流量，发送数据和接受数据

AndroidL版本后可以利用检测工具来优化应用的耗电
1、Battery Historian
	详细用法见文档



------------------图片优化----------------------------

图片存在的几种形式：File,流，Bitmap(内存)


图片压缩：
1、质量压缩：
	压缩前：
	AB
	CD
	压缩后：
	AA
	AA
	原理：通过算法抠掉了图片中的一些点附近相近的像素点，达到降低质量减少文件大小的目的。
	注意：其实只能实现对file文件的影响，bitmap本身并无影响，包括内存。
	因为bitmap在内存中的大小是按照像素计算的，也就是widht*height*每个像素的大小计算，对于质量压缩，并不会改变图片真实的像素
	使用场景：将图片压缩后保存到本地，或上传到服务器。

	BitmapFactory.Options options = new BitmapFactory.Options();
	Bitmap bitmap = BitmapFactory.decodeFile("pathName",options);
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	//质量：quality 0 - 100
	bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
	FileOutputStream fos = new FileOutputStream(bos);
	fos.write(bos.toByteArray());
	fos.flush();
	fos.close();


	BitmapFactory.decodeStream();-->bitmap.cpp

	BitmapFactory.decodeResource()方法里面根据当前屏幕的像素密度进行缩放适配
		BitmapFactory.Options:参数
			inDensity:图片的像素密度，默认是160（默认屏幕像素密度）
			inTargetDensity:图片最终的像素密度，获取屏幕的像素密度


	屏幕像素密度		分辨率	Density(密度)
	160dpi			320x480		1
	px = (dpi/160) * dp = density * dp




2、尺寸压缩：
	原理：通过减少单位尺寸的像素值，真正意义上的像素，
	改变大小和图片的色值通道和位深，1000x1000->250x250（缩放）
	使用场景：缓存缩略图，头像等
	int ratio = 4;//压缩尺寸倍数，值越大，图片尺寸越小
	Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth()/ratio,bmp.getHeight()/ratio,Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap);
	Rect rect = new Rect(0,0,bmp.getWidth()/ratio,bmp.getHeight()/ratio);
	canvas.drawBitmap(bitmp,null,rect,null);
	canvas.save();




3、采样率压缩：（加载大图）
	设置图片的采样率，降低图片像素（ndk进行处理）
	options.inJustDecodeBounds = true;//不会真正加载图片，而是得到图片的宽高信息
	options.inSampleSize;//采样率，必须是2的倍数

4、终极压缩：绕过系统的压缩方式，自己实现
	图片处理引擎
	95年JPEG处理引擎，05年skia引擎，基于ppeg的二次开发
	Android上用的就是Skia的阉割版（去掉了哈夫曼编码算法（解码算法保留），采用定长编码算法）
	导致图片处理后文件变大
	理由：由于CPU和内存在手机上都非常吃紧和性能问题，因为哈夫曼算法非常
	吃CPU，被迫采用了其他算法。
自己的优化：绕过Android Bitmap的API层，来自己实现编码，修复使用哈夫曼算法。

一个像素点包含四个信息，A,R,G,B。（定长编码）
哈夫曼算法：使用质量压缩压缩20倍，清晰度也相当好

a b c d e
a:001
b:010
c:011
d:100
e:101
用3位来表示一个字符的信息，数据定长编码的最优
定长编码：
001 010 011 100 101
加权信息编码：（哈夫曼）
a:80%
b:5%
c:5%
d:5%
e:5%
问题来了，如何得到每个字符出现的权重：
	需要扫描整个文件信息，要大量计算，所以很吃内存！！！
如何实现：不适用skia,直接用JPEG引擎，
下载：libjpeg库 www.ijg.org
基于该引擎左一定的开发，自己实现编码

详细内容见：ndk.bitmap_compress包下的代码
java：

public static native String compressBitmap(Bitmap bitmap
	int width,int height,int quality,byte[] filePath,boolean optimize);

C++:
	1、将bitmap解码，并转换成RGB数据
	2、JPEG对象分配空间以初始化
	3、指定压缩数据源
	4、获取文件信息
	5、为压缩设置参数，不如图像大小，类型，颜色空间
	6、开始压缩
	7、压缩结束
	8，释放资源



int generateJPEG(BYTE* data,int width,int height,
	int quality,const char* outfilename,jboolean optimize)
{
	//jpeg的结构体，保存图片的宽，高，位深，格式等信息，相当于java的类
	struct jpeg_compress_struct jcs;
}

jstring Java_com_dlm_ndk_BitmapUtil_compressBitmap(
	JNIEnv* env,jclass clazz,jobject bitmap,int width,
	int height,int quality,jbyteArray fileNameStr,jboolean optimize)
{
	//1、将bitmap的所有像素数据读取出来,转换成RGB，去掉A,保存到二位byte数组里面
	//处理bitmap图像信息 锁定画布
	BYTE *pixelscolr;//二维数组
	AndroidBitmap_lockPixels(env,bitmap,(void**)&pixelscolr);//将bitmap信息保存到pixelscolor中

	//2、解析每一个像素点的rgb值，去掉alpha，保存到一维数组
	BYTE *data;
	BYTE a,r,g,b;
	data = (BYTE*)malloc(width * height * 3);
	BYTE *tempData;//临时保存data的首地址
	tempData = data;
	//遍历bitmap的像素点
	int i = 0,j = 0;
	int color;
	for (; i < h; ++i)
	{
		for (;j < w; ++j) {
			//解决掉alpha,拿到像素点
			color = *((int*)pixelscolr);//通过地址取值，拿到像素点的首地址
			a = (color & 0xFF000000) >> 24;
			r = (color & 0x00FF0000) >> 16;
			g = (color & 0x0000FF00) >> 8;
			b = (color & 0x000000FF);
			//改值 --- 保存到data数据里面
			*data = b;
			*(data + 1) = g;
			*(data + 2) =r;
			data = data + 3;
			//指针向后移动4个位置，到下一个像素点
			pixelscolr += 4;
		}
	}

	//解锁
	AndroidBitmap_unlockPixels(env,bitmap);

	char* outputFileName = jstringToString(env,fileNameStr);

	//调用libjepg方法实现压缩
	int resultCode = generateJPEG(tempData,width,height,quality,outputFileName,true);
	if (resultCode == 0) {
		//代表失败
		jstring result = env->NewStringUTF("-1");
		return result;
	}
	//代表失败
	return env->NewStringUTF("1");
}

------------------数据传输效率优化---------------------

传统序列化方式:Serializable/Parcelable
json/xml/protocal-buffers/FlatBuffers
Flatbuffers:
1、基于二进制的文件，速度超快（json 1000ms,flatbuffer:0-5ms）
json基于字符串的
2、高效的内存使用和速度，使用过程中，不需要额外的内存，几乎接近原始数据
在内存中的大小
3、灵活，数据能能够前后兼容
4、很少的代码侵入，使用少量的自动生成的代码即可实现（缺点）
5、强数据类型，易于使用

由描述文件和二进制文件组成



--------------------安装包优化-----------------------

aapt:打包工具

1、图片压缩：
--apk里面的资源图片，压缩图片，优化空间很小，主要取决于美工
--svg图片：里面保存图片的一些描述，虽然文件很小，但是得牺牲CPU的计算能力，
	使用时间换了空间，所以得权衡使用。
	使用原则：简单的图片可以用svg，如图标。
--webp:谷歌现在非常提倡使用的图片格式，保存图片比较小，
	无损压缩比PNG文件小45%左右  iSparta在线图片转换工具
	缺点：加载速度相比于PNG慢的很多，但是现在手机配置比较高，所以也无所谓了

2、资源动态加载
如：emoji表情，动态下载资源，而不是直接打包到apk里面
一些模块的插件化动态添加。

3、Lint工具（apk瘦身）
	无用的布局，图片，string.xml中没有用的字符串

4、极限压缩
	7zZip压缩工具

5、Progurad混淆压缩
	可以删除注释和不用的代码
	可以将java文件名改成短名a.java,b.java
	方法名 a.b();
	去除无用的import

6、资源文件再压缩（重点：在常规的优化之外继续优化压缩）

	在做“混淆”：要实现资源名称的修改，如 ic_launcher.png->a.png
	甚至可以可以更夸张，res/drawable/ -> r/d



------------------Service调优---------------------

Service:是一个后台服务，专门用来处理常驻后台的工作组件

即时通讯：service来做常驻后台的心跳传输
良民：核心服务尽可能的轻（任务尽可能少）

进程的优先级：
1、前台进程 Foreground progress
	用户正在交互的Activity
	当某一个Service绑定正在交互的Activity
	被主动调用为前台Service(startForground())
	组件正在执行生命周期的回调（onCreate()/onStart()）
	BroadcastReceiver正在执行onReceive()

2、可见进程 Visible process
	Activity处在onPause()（没有进入onStop()）
	绑定到前台Activity的Service
3、服务进程 Servicee process
	startService()
4、后台进程 Background process
	对用户没有直接影响的进程，比如Activity处于onStop()的时候
	设置单独进程：android:process":xxx"
5、空进程 Empty process
	什么都不做的进程（Android设计的为了第二次启动更快，采取的一个权衡）

如何提高进程的优先级，尽量做到不轻易被系统杀死

1、QQ采取在锁屏的时候启动一个1像素的Activity（故事：小米撕逼）
Window window = getWindow();
window.setGravity(Gravity.LEFT|Gravity.TOP);
LayoutParams params = window.getAttributes();
params.height = 1;
params = 1;
params.xxx =0;
params = 0;


//保持KeepOnePixcelActivity所在的进程不被干掉
MainActivity.java -> startService() -> MyService.java -> registeReceiver()-> ScreenBraodcastReceive.java

-> ACTION_SCREEN_ON/ACTION_SCREEN_OFF -> startActivity() -> KeppOnePixcleActivity.java

//监听屏幕开启

class KeepLievReceiver extends BraodcastReceiver{
	String action = "";
	public void onReceive(Intent intent){
		action = intent.getAction();
		if (action == Intent.ACTION_SCREEN_ON) {
			//do something
		}
	}
}

2、app运营商和手机厂商可能有合作关系-白名单

3、双进程守护：一个进程被杀死，另一进程又被他启动，项目监听启动（可以被手动强制退出）

4.JobScheduler:代码见：performance_opt\keepliveprocess.zip（永远杀不死的）

5、监听系统应用，然后启动自己

6、利用账号同步机制唤醒进程 AccountManager

7、NDK解决，Native进程来实现双进程守护

8、等等

总结：根据自己的需要来使用。

A<--->B
注意系统杀进程是一个一个杀的，本质是和杀进程时间赛跑
使用AIDL进行进程间通信,接口定义语言
LocalService:
class LocalService extends Service{
	MyBinder binder;
	MyServiceConnection conn;
	public IBinder onBind(Intent intent){
		return binder;
	}

	public void onCreate(){
		if (binder == null) {
			binder = new MyBinder();
		}
		if (conn == null) {
			conn = new MyServiceConnection();
		}
	}

	public int onStartCommod(Intent intent){
		bindService(RemoteService);
		//把service设置为前台运行，避免手机系统自动杀掉服务
		//注意：前提是要让用知道，所以需要弹出通知栏
		Notification notificatioin = new Notification(R.drawable.ic_launcher,"hello I am 360",System.currenttime());
		notificatioin.contentIntent = PendingIntent.getService(this,0,intent,0);
		startForeground(startId,notificatioin);
		return START_STICKY;
	}

	class MyBinder extends RemoteConnect.Stub{
		public String getProcessName(){
			return "LocalService";
		}
	}

	class MyServiceConnection implements ServiceConnection{
		public void onServiceConnected(ComponentName,IBinder service){
			Log.i("RemoteService","建立连接成功");
		}

		public void onServiceDisconnected(ComponentName name){
			Log.i("RemoteService","远程服务可能被干掉了，断开连接");
			//启动被干掉的服务,并绑定
			startService(RemoteService);
			bindService(RemoteService);
		}
	}
}

RemoteService:
class RemoteService extends Service{
	。。。
}
AndroidMenifest:
<service name = "RemoteService"
	android:process =":remoteService"/>
AIDL:
package com.dlm.demo;
interface RemoteConnect{
	String getProcessName();
}

MainActivity:

startService(LocalService.class);
startService(RemoteService.class)


-------------------应用启动速度优化和splash页面设计----------------
1、启动方式
	冷启动：直接从桌面上直接启动，同时后台没有该进程的缓存，
		系统需要重新创建新的进程和分配资源。
	热启动：该app后台有该进程的缓存，不需要重新分配资源。
2、如何测量一个应用的启动时间
	使用命令行启动app，同时开始时间测量，单位：毫秒
	adb shell am start -W [packagename]/[package.MainActivity]

	ThisTime 165:当前指定的activity启动时间
	TotalTime 165：整个应用的启动时间，Application+Activity.
	WaitTime 175:包括系统的影响时间，比上面的大。

3、应用启动的流程
	Application从构造方法开始 -> attachBaseContext() -> onCreate() ->
	Activity构造方法 -> onCreate() -> setContentView 设置显示的界面布局
	设置主题，背景等等属性 -> onStart() -> onResume() ->
	显示里面的View(测量，布局，绘制，显示到界面上)

问题：时间到底花在哪里了？
	Application初始化+MainActivity的界面绘制时间

4、如何减少应用的启动时间
	(1)不要在Application的构造方法，attachBaseContext,onCreate()里面进行初始化的耗时炒作。
	(2)由于用户只关心Activity最后显示的一帧,要求布局的层次要减少,
	自定义控件要减少测量，布局，绘制的时间。
	(3)SharedPreference的初始化的时候，需要将数据全部读取出来放到内存中
		优化：尽可能减少sp文件数量（IO需要时间），像这样的初始化最好放到线程里面和懒加载

	(4)因为MainActivity的页面绘制时间较长，所以使用SplashActivity作为欢迎页，
	但是还是需要跳转到MainActivity，不能解决根本问题。

耗时的问题：Application + Activity的启动时间及资源加载时间,预加载的数据花的时间。
更好的优化：让这两个耗时操作重叠在一个时间段内并发地做这两个事情就节省了时间。
解决办法：将SplashActivity和MainActivity合并为一个。
		 一进来还是显示MainActivity，SplashActivity可以变成一个SplashFragment,
		 然后放一个FrameLayout作为根布局，SplashFragment里面非常简单，就是显示一个图片，启动非常快。
		 当SplashFragment显示完毕后再将它remove掉，同时在splash的2秒的欢迎时间内进行网络缓存，
		 这个时候就直接显示MainActivity内容。
		 存在的问题：SpalshView和ContentView加载放到了一起来做了，这可能会影响应用的启动时间。
		 解决：可以用ViewStub延时加载MainActivity当中的View，这样可以尽可能减轻这种影响。
ViewStub的设计就是为了防止MainActivity的启动加载资源太耗时了，延时进行加载，不影响启动，用户友好，
但是他也需要时间加载：viewstub.inflate();

5、如何设计延时加载DelayLoad:
	不同的机型启动速度不一样，这个时间如何控制？
	需要达到的效果：应用已经启动并完成加载，界面已经显示出来了，然后再去做其他的事情。
	那问题来了，什么时候会加载完成？


代码见：package:performance_opt.splash_fragment
class SplashFragment extends Fragment{
	public View onCreateView(inflater,container,bundle){
		return new ImageView(this);
	}
}



------------------RXJava---------------------

rxjava响应式编程：
特点：
	1：线程切换（异步），
	2.事件变换（String->Bitmap）
	3.链式调度（避免了回调+嵌套）

RxJava 有四个基本概念：
	Observable： 即被观察者，它决定什么时候触发事件以及触发怎样的事件
	Observer： 即观察者，它决定事件触发的时候将有怎样的行为。
	事件：
	Subscribe (订阅)：Observable 和 Observer 通过 subscribe() 方法实现订阅关系，
		创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了
	从而 Observable 可以在需要的时候发出事件来通知 Observer。

线程切换：

--Scheduler:线程调度器
	--IOScheduler:子线程调度器
	--HandlerScheduler:主线程调度器。
--Work

子线程->new Thread();
主线程：Looper.getMainLoop();
handler.postRunnable();





