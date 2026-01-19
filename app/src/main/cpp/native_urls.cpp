//
// Created by Chethan on 13-11-2025.
//
#include <jni.h>
#include <string>

//DEMO Encrypt
const char *DOMAIN = "4be8a5c62289f7c9708e1a64b7efe2a5584bd25c78934d3222c69dc0216a67a2";
const char *BASE_URL = "51a5f1b02a83aa66f9232bf50695c9a9c42ff00c61202d900210fed15e4d22f1ae8565918fa73d807092618dc21209b5";
const char *PROMO_IMAGE_BASE = "51a5f1b02a83aa66f9232bf50695c9a901461c66a64175f4c1e86f63d6d97aea29e59bfd246d68cd7bbe866a102e036a";
const char *CATALOGUE_IMAGE_BASE = "51a5f1b02a83aa66f9232bf50695c9a901461c66a64175f4c1e86f63d6d97aea29e59bfd246d68cd7bbe866a102e036a";
const char *MERCHECNTNAME = "20997e482e32225e37396bc9b2da402eca74f100ff5c25da2f52de17d894de97";
const char *CHEK_LIVE_DEMO = "ff3b807122d59687225108b3cc7b1656";

//LIVE Encrypt
//const char *DOMAIN = "066f3142f757812fb0992f8e0ca8a0fbd2d15f0e3bb4c0fbe74792d4bf1b1c10";
//const char *BASE_URL = "33c74245360e2adf5fac9d0c34af548fa04863ea7fe331ccd4388cba4ce356b0";
//const char *PROMO_IMAGE_BASE = "066f3142f757812fb0992f8e0ca8a0fbd2d15f0e3bb4c0fbe74792d4bf1b1c10";
//const char *CATALOGUE_IMAGE_BASE = "066f3142f757812fb0992f8e0ca8a0fbd2d15f0e3bb4c0fbe74792d4bf1b1c10";
//const char *MERCHECNTNAME = "da3f5f4afc289220b63df0fc42ec086893164a1d49e3fc3df55fd6f7e291ee67";
//const char *CHEK_LIVE_DEMO = "4b0517dff272d87b3613a5c274af64ee";


//Contents Encrypt
const char *SHAKEY = "89cfff6fa91bdeccf15a748249789a7e50ab75ad20074948402192461010330c7ec9d2c6907fba959cf007632a2225c3";
const char *UPDATE_URL = "89cfff6fa91bdeccf15a748249789a7e0bd01fd705e7996af2121256a7f6782e9eff0efb06b8eb59a55b2e18c155d4c3d985ba7f23c788a8e68854f70c4866d504f754ea71e771232d48d2b18257de30";


extern "C" {
//Base url
JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getBaseUrl(JNIEnv *env, jobject /*thiz*/) {
    return env->NewStringUTF(BASE_URL);
}

JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getDomain(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(DOMAIN);
}

JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getPromoImageBase(JNIEnv *env,jobject /* this */) {
    return env->NewStringUTF(PROMO_IMAGE_BASE);
}

JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getCatalogueImageBase(JNIEnv *env,
                                                                                   jobject /* this */) {
    return env->NewStringUTF(CATALOGUE_IMAGE_BASE);
}

JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getShaKey(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(SHAKEY);
}

JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getUpdateUrl(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(UPDATE_URL);
}

JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getMerchentName(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(MERCHECNTNAME);
}

JNIEXPORT jstring JNICALL
Java_com_loyltworks_mandelapremium_utils_fetchData_ndk_NativeConfig_getCheckLiveDemo(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(CHEK_LIVE_DEMO);
}
}