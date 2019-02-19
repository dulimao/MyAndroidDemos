

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





------------------RXJava---------------------

rxjava响应式编程：
特点：
	1：线程切换（异步），
	2.事件变换（订阅者和发布者）
	3.链式调度（避免了回调+嵌套）

RxJava 有四个基本概念：
	Observable： 即被观察者，它决定什么时候触发事件以及触发怎样的事件
	Observer： 即观察者，它决定事件触发的时候将有怎样的行为。
	事件：
	Subscribe (订阅)：Observable 和 Observer 通过 subscribe() 方法实现订阅关系，
		创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了
	从而 Observable 可以在需要的时候发出事件来通知 Observer。





