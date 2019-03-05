#include <jni.h>
#include <string>
#include<opencv2/core.hpp>

extern "C" {
jstring
Java_com_example_pqi13_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

//DRS 20160822c - Added native method
jstring
Java_com_example_pqi13_myapplication_MainActivity_validate(JNIEnv *env, jobject, jlong addrGray, jlong addrRgba) {
    cv::Rect();
    cv::Mat();
    std::string hello2 = "Hello from validate";
    return env->NewStringUTF(hello2.c_str());
}

void
Java_com_example_pqi13_myapplication_MainActivity_createDetector(){}


}