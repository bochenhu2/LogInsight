package com.bochen.loginsight.data.remote

import com.bochen.loginsight.data.local.LogEntity
data class LogDto(
    val id: Int,
    val time: String,
    val level: String,
    val tag: String,
    val pid: Int,
    val tid: Int,
    val message: String
)

fun LogDto.toEntity(): LogEntity {
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