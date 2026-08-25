package com.bochen.loginsight.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bochen.loginsight.data.model.LogDetail

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: String,
    val level: String,
    val tag: String,
    val pid: Int,
    val tid: Int,
    val message: String
)

fun LogEntity.toLogDetail(): LogDetail{
    return LogDetail(id = id,
        time = time,
        level = level,
        tag = tag,
        pid = pid,
        tid = tid,
        message = message)
}