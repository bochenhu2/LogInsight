package com.bochen.loginsight.ui.screens.LogDetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bochen.loginsight.viewmodel.MainViewModel

@Composable
fun LogDetailScreen(viewModel: MainViewModel, msg:String, onBack: () -> Unit){
    val displayLevel = when (msg) {
        "ERROR", "WARNING", "INFO", "DEBUG" -> msg
        else -> "UNKNOWN"
    }
    var level = displayLevel

    val time = "2026-07-26 14:30"
    val tag = "NetworkService"
    val message = "Network request timeout while\n" +
            "connecting to api.xxx.com"

    /*val logs = remember{
        listOf(
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime: Process: com.bochen.composedemo, PID: 13327\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime: java.lang.IllegalArgumentException: Only VectorDrawables and rasterized asset types are supported ex. PNG, JPG, WEBP\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.ui.res.PainterResources_androidKt.loadVectorResource(PainterResources.android.kt:95)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.ui.res.PainterResources_androidKt.painterResource(PainterResources.android.kt:67)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at com.bochen.composedemo.MainActivityKt.Greeting(MainActivity.kt:216)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at com.bochen.composedemo.ComposableSingletons\$MainActivityKt.lambda__1235624923\$lambda\$31(MainActivity.kt:141)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at com.bochen.composedemo.ComposableSingletons\$MainActivityKt\$\$ExternalSyntheticLambda11.invoke(D8\$\$SyntheticClass:0)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:131)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:52)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.material3.ScaffoldKt\$ScaffoldLayout\$bodyContent\$1\$1.invoke(Scaffold.kt:163)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.material3.ScaffoldKt\$ScaffoldLayout\$bodyContent\$1\$1.invoke(Scaffold.kt:163)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:122)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.kt:52)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.runtime.internal.Expect_jvmKt.invokeComposable(Expect.jvmAndAndroid.kt:26)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.runtime.GapComposer.doCompose-aFTiNEg(GapComposer.kt:2643)\n",
            "03-10 23:27:37.255 13327 13327 E AndroidRuntime:        at androidx.compose.runtime.GapComposer.composeContent--ZbOJvo\$runtime(GapComposer.kt:2545)"
        )
    } */
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            LogDetailsTopBar(onBack)
        },
        bottomBar = {
            LogDetailsBottomBar()
        }
    ) {

        innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Text(text = "Level: ")
                Text(text = level)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Text(text = "Time: ")
                Text(text = time)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Text(text = "Tag: ")
                Text(text = tag)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Text(text = "Message: ")
                Text(text = message)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.logList) { log ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${log.time} ${log.level} ${log.tag}\n${log.message}",
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailsTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text("Log Details")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
fun LogDetailsBottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites"
                )
            },
            label = {
                Text("Favorites")
            }
        )
    }
}