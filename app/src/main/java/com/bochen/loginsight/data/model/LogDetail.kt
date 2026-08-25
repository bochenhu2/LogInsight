package com.bochen.loginsight.data.model

import com.bochen.loginsight.data.local.LogEntity

data class LogDetail (
    val id:Int,
    val time: String,
    val level: String,
    val tag: String,
    val pid: Int,
    val tid: Int,
    val message: String
)

fun LogDetail.toEntity(): LogEntity{
    return LogEntity(
        id = id,
        time = time,
        level = level,
        tag = tag,
        pid = pid,
        tid = tid,
        message = message
    )
}