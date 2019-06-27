# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/bjornahlfeld/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
#Retrolambda
-dontwarn java.lang.invoke**
-dontwarn com.viewpagerindicator.LinePageIndicator
-dontwarn com.squareup.**
-dontwarn okio.**
-dontwarn org.codehaus
-dontwarn java.nio
-keep public class org.codehaus.**
-keep public class java.nio.**
-dontwarn retrofit2.Platform$Java8
-keep class android.arch.** { *; }


-keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
}

###################
# Get rid of #can't find referenced method in library class java.lang.Object# warnings for clone() and finalize()
# for details see http://stackoverflow.com/questions/23883028/how-to-fix-proguard-warning-cant-find-referenced-method-for-existing-methods
-dontwarn net.fortuna.ical4j.model.**

-dontwarn com.androidmapsextension.*
-dontwarn com.google.android.gms.maps.*
-dontwarn com.google.android.gms.maps.model.*
# standard, except v4.app.Fragment, its required when app uses Fragments
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v7.app.ActionBarActivity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
# rxjava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }
-dontwarn groovy.**
-keep class groovy.** { *; }

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase