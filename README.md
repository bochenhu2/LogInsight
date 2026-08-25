# Log Insight 

Log Insight is an Android application for retrieving, storing, and displaying
structured Android log data.

The project was built as a practical Android application to demonstrate a
modern Android architecture using Jetpack Compose, MVVM, Room, Retrofit,
Coroutines, Flow, and Hilt.

## Features

- Fetch log data from a REST API using Retrofit and OkHttp
- Persist log data locally using Room
- Observe database changes reactively using Kotlin Flow
- Manage UI state using ViewModel and StateFlow
- Display logs with Jetpack Compose
- Navigate between log list and log detail screens
- Filter and inspect structured Android log information
- Manage application dependencies using Hilt

## Architecture

The application follows an MVVM-based architecture:

REST API
    ↓
Retrofit + OkHttp
    ↓
DTO
    ↓
Repository
    ↓
Room Database
    ↓
DAO / Entity
    ↓
Flow
    ↓
ViewModel + StateFlow
    ↓
Jetpack Compose UI

Hilt is used for dependency injection across the application.

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Coroutines
- Flow / StateFlow
- Room
- Retrofit
- OkHttp
- Gson
- Hilt
- Navigation Compose
- Material 3

## Project Structure

```text
com.bochen.loginsight
├── data
│   ├── local
│   │   ├── LogDao
│   │   ├── LogDatabase
│   │   └── LogEntity
│   ├── remote
│   │   ├── LogApi
│   │   ├── LogDto
│   │   └── Retrofit
│   ├── model
│   └── repository
│       └── LogRepository
├── di
├── ui
│   ├── navigation
│   └── screens
├── viewmodel
├── LogInsightApplication
└── MainActivity
