#####################################################################################
# start 公共配置
# update author: qihao on 2020/7/4 17:53
# Email：sin2t@sina.com
#####################################################################################
#############################################
# 对于一些基本指令的添加
#############################################
    # 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
    -optimizationpasses 7

    # 混合时不使用大小写混合，混合后的类名为小写
    -dontusemixedcaseclassnames

    # 指定不去忽略非公共库的类
    -dontskipnonpubliclibraryclasses

    # 这句话能够使我们的项目混淆后产生映射文件
    # 包含有类名->混淆后类名的映射关系
    -verbose

    # 指定不去忽略非公共库的类成员
    -dontskipnonpubliclibraryclassmembers

    # 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
    -dontpreverify

    # 保留Annotation不混淆
    -keepattributes *Annotation*,InnerClasses

    # 避免混淆泛型
    -keepattributes Signature

    # 抛出异常时保留代码行号
    -keepattributes SourceFile,LineNumberTable

    # 优化  不优化输入的类文件
    -dontoptimize

    # 指定混淆是采用的算法，后面的参数是一个过滤器
    # 这个过滤器是谷歌推荐的算法，一般不做更改
    -optimizations !code/simplification/cast,!field/*,!class/merging/*

    # 混淆时所采用的算法
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
    #保护注解
    -keepattributes *Annotation*
     #如果引用了v4或者v7包
    -dontwarn android.support.**
    #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }
    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }
     # 保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    # 保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }
    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable
    #保持 Serializable 不被混淆
    -keep class * implements java.io.Serializable
    #保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }
    #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
    #-keepclassmembers enum * {
    #  public static **[] values();
    #  public static ** valueOf(java.lang.String);
    #}

    # apk 包内所有 class 的内部结构
    -dump release/dump.txt
    # 没有被混淆的类和成员
    -printseeds release/seeds.txt
    # 被移除的代码
    -printusage release/usage.txt
    # 混淆前后类、方法、类成员等的对照
    -printmapping release/mapping.txt

    -keepclassmembers class * {
        public void *ButtonClicked(android.view.View);
    }
    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }
    #避免混淆泛型 如果混淆报错建议关掉
    #–keepattributes Signature

    #关闭所有日志 log, java.io.Print, printStackTrace
    -assumenosideeffects class android.util.Log {
        public static *** e(...);
        public static *** w(...);
        public static *** i(...);
        public static *** d(...);
        public static *** v(...);
    }
    -assumenosideeffects class java.io.PrintStream {
        public *** print(...);
        public *** println(...);
    }
    -assumenosideeffects class java.lang.Throwable {
        public *** printStackTrace(...);
    }

    -obfuscationdictionary dictionary.txt
    -classobfuscationdictionary dictionary.txt
    -packageobfuscationdictionary dictionary.txt
    -ignorewarnings

    # 手动启用@Keep注解
    -dontskipnonpubliclibraryclassmembers
    -printconfiguration
    -keep,allowobfuscation @interface androidx.annotation.Keep
    -keep @androidx.annotation.Keep class **
    -keepclassmembers class ** {
        @androidx.annotation.Keep *;
    }
    # 对于混淆应用须要避免混淆IPackageDataObserver文件
    -keep class android.content.pm.** { *; }
#####################################################################################
# end 公共配置
# update author: qihao on 2020/7/4 17:53
# Email：sin2t@sina.com
#####################################################################################


#######################-- app start
    # bean
     -keep class com.clear.tools.data.** { *; }

    #HttpBean
#    -keep class com.google.httpmodule.net.beans.** { *; }
#    -keep class com.google.utilsmodule.restrictionbypass.** { *; }
#    ## 播放器混淆
#    -keep class cn.videoplayer.video.** { *; }
#    -dontwarn com.hyvideoplayer.video.**
#    -keep class cn.videoplayer.video.base.** { *; }
#    -dontwarn com.hyvideoplayer.video.base.**
#    -keep class cn.videoplayer.utils.** { *; }
#    -dontwarn com.hyvideoplayer.utils.**
#    -keep class tv.danmaku.ijk.** { *; }
#    -dontwarn tv.danmaku.ijk.**

#    -keep public class * extends android.view.View{
#        *** get*();
#        void set*(***);
#        public <init>(android.content.Context);
#        public <init>(android.content.Context, android.util.AttributeSet);
#        public <init>(android.content.Context, android.util.AttributeSet, int);
#    }

#######################-- app end

#######################-- start coil库混淆

    #adjust
    -keep class com.adjust.sdk.**{ *; }
    -keep class com.google.android.gms.common.ConnectionResult {
        int SUCCESS;
    }
    -keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
        com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
    }
    -keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
        java.lang.String getId();
        boolean isLimitAdTrackingEnabled();
    }
    -keep public class com.android.installreferrer.**{ *; }
    #oaid 不同的版本混淆代码不太一致，你注意你接入的oaid版本
     -dontwarn com.bun.**
     -keep class com.bun.** {*;}
     -keep class a.**{*;}
     -keep class XI.CA.XI.**{*;}
     -keep class XI.K0.XI.**{*;}
     -keep class XI.XI.K0.**{*;}
     -keep class XI.vs.K0.**{*;}
     -keep class XI.xo.XI.XI.**{*;}
     -keep class com.asus.msa.SupplementaryDID.**{*;}
     -keep class com.asus.msa.sdid.**{*;}
     -keep class com.huawei.hms.ads.identifier.**{*;}
     -keep class com.samsung.android.deviceidservice.**{*;}
     -keep class com.zui.opendeviceidlibrary.**{*;}
     -keep class org.json.**{*;}
     -keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}
    ## pangle 穿山甲原有的
    -keep class com.bytedance.sdk.openadsdk.** { *; }
    -keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
    -keep class com.pgl.sys.ces.** {*;}
    -keep class com.bytedance.embed_dr.** {*;}
    -keep class com.bytedance.embedapplog.** {*;}
    # Pangle
    -keep class com.bytedance.sdk.** { *; }
    -keep class com.pgl.sys.ces.* {*;}
    # smaato
	-keep public class com.smaato.sdk.** { *; }
	-keep public interface com.smaato.sdk.** { *; }
    # AdColony
    # For communication with AdColony's WebView
    -keepclassmembers class * {
        @android.webkit.JavascriptInterface <methods>;
    }
    # Verve
    -keepattributes Signature
    -keep class net.pubnative.** { *; }
    -keep class com.iab.omid.library.pubnativenet.** { *; }
    ## pangle 插件新增 穿山甲插件化版本新增
    -keep public class com.ss.android.**{*;}
    -keeppackagenames com.bytedance.sdk.openadsdk.api
    -keeppackagenames com.bytedance.embed_dr
    -keeppackagenames com.bytedance.embedapplog
    -keeppackagenames com.ss.android

    # ironsource
    -keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
        public *;
    }
    -keepclassmembers class * implements android.os.Parcelable {
        public static final android.os.Parcelable$Creator *;
    }
    -keep public class com.google.android.gms.ads.** {
       public *;
    }
    -keep class com.ironsource.adapters.** { *;
    }
    -dontwarn com.ironsource.mediationsdk.**
    -dontwarn com.ironsource.adapters.**
    -keepattributes JavascriptInterface
    -keepclassmembers class * {
        @android.webkit.JavascriptInterface <methods>;
    }

    # 安天杀毒SDK
    -keep class com.avl.engine.** { *; }
    # viewbinding
    -keep class * implements androidx.viewbinding.ViewBinding {
        *;
    }
    -keepclassmembers class * implements androidx.viewbinding.ViewBinding {
      public static ** inflate(...);
      public static ** bind(***);
    }
    # 自定义 ViewModelProvider.Factory
    -keepclassmembers public class * extends androidx.lifecycle.ViewModel {
        public <init>(...);
    }
    #聚合混淆
    -keep class bykvm*.**
    -keep class com.bytedance.msdk.adapter.**{ public *; }
    -keep class com.bytedance.msdk.api.** {
     public *;
    }
    -keep class com.bytedance.msdk.base.TTBaseAd{*;}
    -keep class com.bytedance.msdk.adapter.TTAbsAdLoaderAdapter{
        public *;
        protected <fields>;
    }

    # SVGA动画
    -keep class com.squareup.wire.** { *; }
    -keep class com.opensource.svgaplayer.proto.** { *; }
    # ServiceLoader support
    -keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
    -keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

    # Most of volatile fields are updated with AFU and should not be mangled
    -keepclassmembers class kotlinx.coroutines.** {
        volatile <fields>;
    }

    # Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
    -keepclassmembers class kotlin.coroutines.SafeContinuation {
        volatile <fields>;
    }

    # These classes are only required by kotlinx.coroutines.debug.AgentPremain, which is only loaded when
    # kotlinx-coroutines-core is used as a Java agent, so these are not needed in contexts where ProGuard is used.
    -dontwarn java.lang.instrument.ClassFileTransformer
    -dontwarn sun.misc.SignalHandler
    -dontwarn java.lang.instrument.Instrumentation
    -dontwarn sun.misc.Signal

    -dontwarn javax.annotation.**

    # A resource is loaded with a relative path so the package of this class must be preserved.
    -keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

    # Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
    -dontwarn org.codehaus.mojo.animal_sniffer.*

    # OkHttp platform used only on JVM and when Conscrypt dependency is available.
    -dontwarn okhttp3.internal.platform.ConscryptPlatform
    -dontwarn org.conscrypt.ConscryptHostnameVerifier

    # Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
    -dontwarn org.codehaus.mojo.animal_sniffer.*
#######################-- end coil库混淆



#####################################################################################
#  start 第三方库
#####################################################################################

# ImmersionBar
    -keep class com.gyf.immersionbar.* {*;}
    -dontwarn com.gyf.immersionbar.**
# EventBus 3.2
    -keepattributes *Annotation*
    -keepclassmembers class * {
        @org.greenrobot.eventbus.Subscribe <methods>;
    }
    -keep enum org.greenrobot.eventbus.ThreadMode { *; }

    # And if you use AsyncExecutor:
    -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
        <init>(java.lang.Throwable);
    }

#okhttp start
    -dontwarn okhttp3.**
    -keep class okhttp3.**{*;}
#okhttp end

#okio start
    -dontwarn okio.**
    -keep class okio.**{*;}
#okio end

#ijkplayer start
    -keep class tv.danmaku.ijk.media.player.** {*;}
    -keep class tv.danmaku.ijk.media.player.IjkMediaPlayer{*;}
    -keep class tv.danmaku.ijk.media.player.ffmpeg.FFmpegApi{*;}
#ijkplayer end

#微博 start
    -keep public class com.sina.weibo.sdk.**{*;}
     -keepattributes *Annotation*
     -keepclassmembers class * {
         @org.greenrobot.eventbus.Subscribe <methods>;
     }
     -keep enum org.greenrobot.eventbus.ThreadMode { *; }

     # Only required if you use AsyncExecutor
     -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
         <init>(java.lang.Throwable);}
#微博 end

# bugly start
    -dontwarn com.tencent.bugly.**
    -keep public class com.tencent.bugly.**{*;}
    -keep class android.support.**{*;}
# bugly end

#穿山甲 start
    -keep class com.bytedance.sdk.openadsdk.** { *; }
    -keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
    -keep class com.pgl.sys.ces.* {*;}
#穿山甲 end

#聚合混淆 start
    -keep class com.bytedance.msdk.adapter.**{ public *; }
    -keep class com.bytedance.msdk.api.** {
     public *;
    }
    -keep class com.bytedance.msdk.base.TTBaseAd{*;}
    -keep class com.bytedance.msdk.adapter.TTAbsAdLoaderAdapter{
        public *;
        protected <fields>;
    }
#聚合混淆 end

# databinding
    -keep class android.databinding.** { *; }
    -keep class androidx.databinding.DataBindingComponent {*;}

#友盟 start
    -keep class com.umeng.**{*;}

    #您如果使用了稳定性模块可以加入该混淆
    -keep class com.uc.**{*;}

    -keepclassmembers class * {
    public<init>(org.json.JSONObject);
    }
    -keepclassmembers enum *{
    publicstatic**[] values();
    publicstatic** valueOf(java.lang.String);
    }
#友盟 end

#友盟一键登录 start
    -keep class com.umeng.**{*;}

    -keepclassmembers class *{
    public<init>(org.json.JSONObject);
    }

    -keepclassmembers enum *{
    publicstatic**[] values();
    publicstatic** valueOf(java.lang.String);
    }

    -keep class com.umeng.umverify.** {*;}
    -keep class cn.com.chinatelecom.gateway.lib.** {*;}
    -keep class com.unicom.xiaowo.login.** {*;}
    -keep class com.cmic.sso.sdk.** {*;}
    -keep class com.mobile.auth.** {*;}
    -keep class android.support.v4.** { *;}
    -keep class org.json.**{*;}
    -keep class com.alibaba.fastjson.** {*;}
#友盟一键登录 end

#七陌SDK start
    -keep class com.moor.imkf.** { *; }
    -keepattributes *Annotation*
    -keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
    }
    -keep enum org.greenrobot.eventbus.ThreadMode { *; }
    #glide
    -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
    }
    -keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
    *** rewind();
    }
    #七牛
    -keep class com.qiniu.**{*;}
    -keep class com.qiniu.**{public <init>();}
    -ignorewarnings
#七陌SDK end

#mp4parser
    -keep public class org.mp4parser.** {*;}
#mp4parser end

#ARoute start
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider
#ARoute end

#####################################################################################
#  end 第三方库
#####################################################################################

#HeaderBehavior
-keep class com.google.android.material.appbar.HeaderBehavior{*;}

-keep public class com.alipay.**