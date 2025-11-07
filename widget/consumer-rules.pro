# Keep all public classes and their public methods in the SDK - prevent removal and obfuscation
-keep,allowoptimization public class com.seel.widget.** {
    public *;
}

# Keep SeelWidgetSDK and SeelClient as they are the main SDK entry points - prevent removal
-keep,allowoptimization class com.seel.widget.SeelWidgetSDK {
    *;
}
-keep,allowoptimization class com.seel.widget.core.SeelClient {
    *;
}

# Keep network classes - prevent removal
-keep,allowoptimization class com.seel.widget.network.** {
    *;
}

# Keep all model classes (for JSON serialization/deserialization)
-keep class com.seel.widget.models.** {
    *;
}

# Keep all UI classes as they need to be instantiated
-keep class com.seel.widget.ui.** {
    public *;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Gson specific classes
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keep,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keep class retrofit2.** { *; }

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

