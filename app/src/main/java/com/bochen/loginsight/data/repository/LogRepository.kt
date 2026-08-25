package com.bochen.loginsight.data.repository

import com.bochen.loginsight.data.local.LogDao
import com.bochen.loginsight.data.local.toLogDetail
import com.bochen.loginsight.data.model.LogDetail
import com.bochen.loginsight.data.model.toEntity
import com.bochen.loginsight.data.remote.LogApi
import com.bochen.loginsight.data.remote.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogRepository @Inject constructor(
    private val logDao: LogDao,
    private val logApi: LogApi
) {
    /*private var logs = listOf(
        LogDetail(1, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"Process: com.bochen.composedemo, PID: 13327\n") ,
        LogDetail(2, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"java.lang.IllegalArgumentException: Only VectorDrawables and rasterized asset types are supported ex. PNG, JPG, WEBP\n\n"),
        LogDetail(3, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.ui.res.PainterResources_androidKt.loadVectorResource(PainterResources.android.kt:95)\n"),
        LogDetail(4, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.ui.res.PainterResources_androidKt.painterResource(PainterResources.android.kt:67)\n"),
        LogDetail(5, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at com.bochen.composedemo.MainActivityKt.Greeting(MainActivity.kt:216)\n") ,
        LogDetail(6, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at com.bochen.composedemo.ComposableSingletons\$MainActivityKt.lambda__1235624923\$lambda\$31(MainActivity.kt:141)\n"),
        LogDetail(7, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at com.bochen.composedemo.ComposableSingletons\$MainActivityKt\$\$ExternalSyntheticLambda11.invoke(D8\$\$SyntheticClass:0)\n"),
        LogDetail(8, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:131)\n"),
        LogDetail(9, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:52)\n") ,
        LogDetail(10, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.material3.ScaffoldKt\$ScaffoldLayout\$bodyContent\$1\$1.invoke(Scaffold.kt:163)\n"),
        LogDetail(11, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.material3.ScaffoldKt\$ScaffoldLayout\$bodyContent\$1\$1.invoke(Scaffold.kt:163)\n"),
        LogDetail(12, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:122)\\n"),
        LogDetail(13, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:52)\n") ,
        LogDetail(14, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.runtime.internal.Expect_jvmKt.invokeComposable(Expect.jvmAndAndroid.kt:26)\n"),
        LogDetail(15, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.runtime.GapComposer.doCompose-aFTiNEg(GapComposer.kt:2643)\n"),
        LogDetail(16, "03-10 23:27:37.255", "E", "AndroidRuntime", 13327, 13327,"       at androidx.compose.runtime.GapComposer.composeContent--ZbOJvo\$runtime(GapComposer.kt:2545)")
    )*/

    fun observeLogs(): Flow<List<LogDetail>> =
        logDao.observeLogs()
            .map { entities ->
                entities.map {
                        entity -> entity.toLogDetail()
                }
            }

    /*suspend fun getAllLogs(): List<LogDetail> =
        withContext(Dispatchers.IO){
            logs
        }*/
    suspend fun refreshLogs(): Result<Unit>{
        return try {
            val response = logApi.getLogs()

            if (response.isSuccessful){
                val remoteLogs = response.body() ?: emptyList()

                val entities = remoteLogs.map {
                    dto -> dto.toEntity()
                }

                logDao.deleteAllLogs()
                logDao.insertLogs(entities)

                Result.success(Unit)
            } else {
                Result.failure(Exception("HTTP ERROR: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getLogById(id: Int): LogDetail? =
        logDao.getLogById(id)?.toLogDetail()


    suspend fun addLog(newLog: LogDetail) =
        logDao.insertLog(newLog.toEntity())
}