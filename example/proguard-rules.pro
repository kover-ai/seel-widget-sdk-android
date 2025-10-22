# ProGuard rules for Seel Widget SDK Example

# Keep all Seel Widget SDK classes
-keep class com.seel.widget.** { *; }

# Keep all model classes
-keep class com.seel.widget.models.** { *; }

# Keep all UI classes
-keep class com.seel.widget.ui.** { *; }

# Keep all network classes
-keep class com.seel.widget.network.** { *; }

# Keep all core classes
-keep class com.seel.widget.core.** { *; }

# Keep all utility classes
-keep class com.seel.widget.utils.** { *; }

# Keep the main SDK class
-keep class com.seel.widget.SeelWidgetSDK { *; }

# Keep serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep Gson annotations
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep Retrofit classes
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Keep OkHttp classes
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Keep Glide classes
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# Keep Android classes
-keep class android.** { *; }
-keep class androidx.** { *; }

# Preserve line numbers for debugging
-keepattributes SourceFile,LineNumberTable