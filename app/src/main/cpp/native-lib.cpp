#include <string>

#include <android/log.h>
#include "NdkTest.h"

#define LOG_TAG "sen"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

long getFileSize(const char *filePath) {
    FILE *file = fopen(filePath, "rb");
    fseek(file, 0, SEEK_END);
    long size = ftell(file);
    fclose(file);
    return size;
}

JNIEXPORT void JNICALL Java_sen_com_dnstudyffmpeg01_NdkTest_diffFile
        (JNIEnv *env, jobject jobject, jstring inPath, jstring outPath, jstring subfileName,
         jint count) {
    const char *cInPath = env->GetStringUTFChars(inPath, NULL);
    const char *cOutPath = env->GetStringUTFChars(outPath, NULL);
    const char *cSubfileName = env->GetStringUTFChars(subfileName, NULL);

    //给子文件名字列表开辟一个空间
    char **pactchFile = (char **) malloc(sizeof(char *) * count);
    int i = 0;
    for (; i < count; i++) {
        //为每个开辟一个空间
        pactchFile[i] = (char *) malloc(sizeof(char *) * 100);
        sprintf(pactchFile[i], cSubfileName, i);
        LOGE("文件名%s", pactchFile[i]);
    }

    long fileSize = getFileSize(cInPath);
    FILE *inFile = fopen(cInPath, "rb");
    if (fileSize % count == 0) {
        //能整除，每个文件是平均大小

        long subFileSize = fileSize / count;
        int i = 0;
        //对每个边读边写
        for (; i < count; i++) {
            FILE *subOutFile = fopen(pactchFile[i], "wb");
            for (int j = 0; j < subFileSize; ++j) {
                fputc(fgetc(inFile), subOutFile);
            }
            fclose(subOutFile);
        }

    } else {
        //不能整除，那么前n-1 是平均大小，剩下就是n
        long subFileSize = fileSize /(count-1);
        int i = 0;
        //对每个边读边写
        for (; i < count - 1; i++) {
            FILE *subOutFile = fopen(pactchFile[i], "wb");
            for (int j = 0; j < subFileSize; ++j) {
                fputc(fgetc(inFile), subOutFile);
            }
            fclose(subOutFile);
        }

        //最后一个
        FILE *last = fopen(pactchFile[count - 1], "wb");
        long lastFileSize = fileSize %(count-1) ;
        LOGE("最后一个：%ld",lastFileSize);
        for (int j = 0; j < lastFileSize; ++j) {
            fputc(fgetc(inFile), last);
        }
        fclose(last);


    }

    //稀放内存
    i = 0;
    for (; i < count; i++) {
        free(pactchFile[i]);
    }
    fclose(inFile);
    free(pactchFile);
    env->ReleaseStringUTFChars(inPath, cInPath);
    env->ReleaseStringUTFChars(outPath, cOutPath);
    env->ReleaseStringUTFChars(subfileName, cSubfileName);
};

/*
 * Class:     sen_com_dnstudyffmpeg01_NdkTest
 * Method:    patchFile
 * Signature: (Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT void JNICALL Java_sen_com_dnstudyffmpeg01_NdkTest_patchFile
        (JNIEnv *env, jobject jobject, jstring inPath, jstring outPath, jstring subfileName,
         jint count) {
    const char *cInPath = env->GetStringUTFChars(inPath, NULL);
    const char *cOutPath = env->GetStringUTFChars(outPath, NULL);
    const char *cSubfileName = env->GetStringUTFChars(subfileName, NULL);

    //给子文件名字列表开辟一个空间
    char **pactchFile = (char **) malloc(sizeof(char *) * count);
    int i = 0;
    for (; i < count; i++) {
        //为每个开辟一个空间
        pactchFile[i] = (char *) malloc(sizeof(char *) * 100);
        sprintf(pactchFile[i], cSubfileName, i);
        LOGE("文件名%s", pactchFile[i]);
    }
    i =0;
    FILE *outFile = fopen(cOutPath,"wb");
    for(;i<count;i++){
       int subFileSize = getFileSize(pactchFile[i]);
        FILE *subInFile = fopen(pactchFile[i],"rb");
        for(int j = 0;j<subFileSize;++j){
            fputc(fgetc(subInFile), outFile);
        }
        fclose(subInFile);
    }
    fclose(outFile);
    //稀放内存
    i = 0;
    for (; i < count; i++) {
        free(pactchFile[i]);
    }
    free(pactchFile);
    env->ReleaseStringUTFChars(inPath, cInPath);
    env->ReleaseStringUTFChars(outPath, cOutPath);
    env->ReleaseStringUTFChars(subfileName, cSubfileName);

};


