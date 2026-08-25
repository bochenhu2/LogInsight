package com.bochen.loginsight.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/*
INSERT INTO logs (time, level, tag, pid, tid, message) VALUES
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'Process: com.bochen.composedemo, PID: 13327'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'java.lang.IllegalArgumentException: Only VectorDrawables and rasterized asset types are supported ex. PNG, JPG, WEBP'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.ui.res.PainterResources_androidKt.loadVectorResource(PainterResources.android.kt:95)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.ui.res.PainterResources_androidKt.painterResource(PainterResources.android.kt:67)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at com.bochen.composedemo.MainActivityKt.Greeting(MainActivity.kt:216)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at com.bochen.composedemo.ComposableSingletons$MainActivityKt.lambda__1235624923$lambda$31(MainActivity.kt:141)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at com.bochen.composedemo.ComposableSingletons$$ExternalSyntheticLambda11.invoke(D8$$SyntheticClass:0)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:131)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:52)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.material3.ScaffoldKt$ScaffoldLayout$bodyContent$1$1.invoke(Scaffold.kt:163)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.material3.ScaffoldKt$ScaffoldLayout$bodyContent$1$1.invoke(Scaffold.kt:163)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:122)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:52)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.runtime.internal.Expect_jvmKt.invokeComposable(Expect.jvmAndAndroid.kt:26)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.runtime.GapComposer.doCompose-aFTiNEg(GapComposer.kt:2643)'),
('03-10 23:27:37.255', 'E', 'AndroidRuntime', 13327, 13327, 'at androidx.compose.runtime.GapComposer.composeContent--ZbOJvo$runtime(GapComposer.kt:2545)');
*/

@Dao
interface LogDao {
    @Query("SELECT* FROM logs ORDER BY id DESC")
    fun observeLogs(): Flow<List<LogEntity>>

    @Query("SELECT* FROM logs WHERE id = :id LIMIT 1")
    suspend fun getLogById(id: Int): LogEntity?

    @Insert
    suspend fun insertLog(log: LogEntity)

    @Insert
    suspend fun insertLogs(logs: List<LogEntity>)

    @Update
    suspend fun updateLog(log: LogEntity)

    @Delete
    suspend fun deleteLog(log: LogEntity)

    @Query("DELETE FROM logs")
    suspend fun deleteAllLogs()


}