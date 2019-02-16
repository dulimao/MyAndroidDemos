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
		MultiDex.install(base);
	}

}



BaseDexClassLoader类{
	DexPathList pathList；


}
DexPathList类{
	Element[] dexElements;
}
源码链接：
http://androidxref.com/4.4.2_r1/xref/libcore/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java#pathList
http://androidxref.com/4.4.2_r1/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java
提供参考源码解析文章阅读：
http://blog.csdn.net/ch15851302205/article/details/44671687

Element[] dexElements;原来的
Element[] dexElements2;合并以后的

1.找到MyTestClass.class
	dn_fix_ricky_as\app\build\intermediates\bin\MyTestClass.class
2.配置dx.bat的环境变量
	Android\sdk\build-tools\23.0.3\dx.bat
3.命令
dx --dex --output=D:\Users\ricky\Desktop\dex\classes2.dex D:\Users\ricky\Desktop\dex
命令解释：
	--output=D:\Users\ricky\Desktop\dex\classes2.dex   指定输出路径
	D:\Users\ricky\Desktop\dex    最后指定去打包哪个目录下面的class字节文件(注意要包括全路径的文件夹，也可以有多个class)




