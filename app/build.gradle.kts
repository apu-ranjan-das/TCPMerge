plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tcpmerge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tcpmerge"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures{
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation (libs.socket.io.client)
//    {
//        exclude() group: 'org.json', module: 'json'
//    }
    implementation (libs.java.websocket)


    //Room

    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.google.code.gson:gson:2.10.1")



    //JSON
//    implementation ("com.android.volley:volley:1.2.0")
//    //implementation ("com.google.code.gson:gson:2.8.6")
//    implementation ("com.github.bumptech.glide:glide:4.12.0")


}