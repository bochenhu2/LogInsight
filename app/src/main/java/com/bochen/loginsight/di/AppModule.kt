package com.bochen.loginsight.di

import android.content.Context
import androidx.room.Room
import com.bochen.loginsight.data.local.LogDao
import com.bochen.loginsight.data.local.LogDatabase
import com.bochen.loginsight.data.remote.LogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    private const val BASE_URL = "https://mpa43f069f622ae2c7c6.free.beeceptor.com/"
    //set up server at https://beeceptor.com/mock-api/ to test


    @Provides
    @Singleton
    fun provideLogDatabase(
        @ApplicationContext context: Context
    ): LogDatabase{
        return Room.databaseBuilder(
            context,
            LogDatabase::class.java,
            "log_insight.db"
        ).build()
    }

    @Provides
    fun provideLogDao(
        database: LogDatabase
    ): LogDao{
        return database.logDao()
    }

    // ------------------
    // OkHttp
    // ------------------

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // ------------------
    // Retrofit
    // ------------------
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLogApi(retrofit: Retrofit): LogApi{
        return retrofit.create(LogApi::class.java)
    }
}
/*
Context
  ↓
LogDatabase
  ↓
LogDao
       \
        LogRepository
       /
LogApi
  ↑
Retrofit
  ↑
OkHttpClient
  ↑
LoggingInterceptor
 */

/*
 [
{
 "id": 1,
 "time": "03-29 22:29:01.456",
 "level": "W",
 "tag": "InputReader",
 "pid": 1080,
 "tid": 1577,
 "message": "Received unexpected event (0x35, 0x37a) for slot 0 with tracking id 34"
},
{
 "id": 2,
 "time": "03-29 22:29:01.456",
 "level": "W",
 "tag": "InputReader",
 "pid": 1080,
 "tid": 1577,
 "message": "Received unexpected event (0x36, 0x1b2) for slot 0 with tracking id 34"
},
{
 "id": 3,
 "time": "03-29 22:29:01.460",
 "level": "V",
 "tag": "ViewRootImpl",
 "pid": 3523,
 "tid": 3523,
 "message": "title = com.tblenovo.launcher/com.tblenovo.launcher.TabUILauncher enqueue motion: MotionEvent { action=ACTION_OUTSIDE, actionButton=0, id[0]=0, x[0]=0.0, y[0]=1200.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, classification=NONE, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=1981457, downTime=1981457, deviceId=6, source=0xd002, displayId=0, eventId=1780423851 }"
},
{
 "id": 4,
 "time": "03-29 22:29:01.460",
 "level": "V",
 "tag": "ViewRootImpl",
 "pid": 2428,
 "tid": 2428,
 "message": "title = Taskbar enqueue motion: MotionEvent { action=ACTION_OUTSIDE, actionButton=0, id[0]=0, x[0]=434.0, y[0]=-641.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, classification=NONE, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=1981457, downTime=1981457, deviceId=6, source=0xd002, displayId=0, eventId=2089434127 }"
},
{
 "id": 5,
 "time": "03-29 22:29:01.460",
 "level": "D",
 "tag": "PowerManagerService",
 "pid": 1080,
 "tid": 1576,
 "message": "userActivityNoUpdateLocked: groupId=0, eventTime=1981457, event=touch, flags=0x0, uid=1000"
},
{
 "id": 6,
 "time": "03-29 22:29:01.460",
 "level": "V",
 "tag": "Scheduler",
 "pid": 873,
 "tid": 1669,
 "message": "notifyInteractionEvent action: Down"
},
{
 "id": 7,
 "time": "03-29 22:29:01.460",
 "level": "D",
 "tag": "NoBackGesture",
 "pid": 1885,
 "tid": 1885,
 "message": "Start gesture: MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=434.0, y[0]=309.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, classification=NONE, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=1981457, downTime=1981457, deviceId=6, source=0xd002, displayId=0, eventId=326353603 }"
},
{
 "id": 8,
 "time": "03-29 22:29:01.461",
 "level": "I",
 "tag": "NoBackGesture",
 "pid": 1885,
 "tid": 1885,
 "message": "onMotionEvent,mDisabledForQuickstep=false, mIsBackGestureAllowed: true, isWithinInsets: false, mGestureBlockingActivityRunning:false, isBlockedByWritingMode:false, isBackGestureDisabled:true, isWithinTouchRegion:true"
},
{
 "id": 9,
 "time": "03-29 22:29:01.461",
 "level": "V",
 "tag": "Scheduler",
 "pid": 873,
 "tid": 1669,
 "message": "notifyInteractionEvent action: Down"
},
{
 "id": 10,
 "time": "03-29 22:29:01.462",
 "level": "V",
 "tag": "ViewRootImpl",
 "pid": 2428,
 "tid": 2428,
 "message": "title = com.tblenovo.launcher/com.tblenovo.launcher.TabUILauncher enqueue motion: MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=434.0, y[0]=309.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, classification=NONE, metaState=0, flags=0x2, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=1981457, downTime=1981457, deviceId=6, source=0xd002, displayId=0, eventId=326353603 }"
},
{
 "id": 11,
 "time": "03-29 22:29:01.539",
 "level": "V",
 "tag": "ViewRootImpl",
 "pid": 2428,
 "tid": 2428,
 "message": "title = com.tblenovo.launcher/com.tblenovo.launcher.TabUILauncher enqueue motion: MotionEvent { action=ACTION_UP, actionButton=0, id[0]=0, x[0]=434.0, y[0]=309.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, classification=NONE, metaState=0, flags=0x2, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=1981539, downTime=1981457, deviceId=6, source=0xd002, displayId=0, eventId=695763206 }"
},
{
 "id": 12,
 "time": "03-29 22:29:01.539",
 "level": "V",
 "tag": "Scheduler",
 "pid": 873,
 "tid": 1668,
 "message": "notifyInteractionEvent action: Up"
},
{
 "id": 13,
 "time": "03-29 22:29:01.539",
 "level": "V",
 "tag": "Scheduler",
 "pid": 873,
 "tid": 912,
 "message": "notifyInteractionEvent action: Up"
}
]
 */