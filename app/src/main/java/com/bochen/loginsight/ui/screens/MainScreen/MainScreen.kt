package com.bochen.loginsight.ui.screens.MainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import android.util.Log
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bochen.loginsight.viewmodel.MainViewModel

private const val TAG = "MainScreen"

@Composable
fun MainScreen(viewModel: MainViewModel,onNavigateToDetails: (String) -> Unit){


    /*val logs = listOf(
        "03-11 21:23:39.581  1074  6459 D PowerManagerService: acquireWakeLockInternal: lock=71931571, flags=0x1, tag=\"AppFreeze:WakeLock\", ws=null, uid=1000, pid=2860, packageName=\"com.zui.pp\", displayId=-1\n",
        "03-11 21:23:39.581  1074  6459 D PowerManagerService: updateWakeLockSummaryLocked: mWakefulness=Dozing, mWakeLockSummary=0x41\n",
        "03-11 21:23:39.581  1074  6459 D PowerManagerService: updateUserActivitySummaryLocked: groupId=0, mWakefulness=Dozing, mUserActivitySummary=0x4, nextTimeout=-1 (114406564 ms ago)\n",
        "03-11 21:23:39.582  1074  6459 D PowerManagerService: updateDisplayPowerStateLocked: displayReady=true, groupId=0, policy=DOZE, mWakefulness=Dozing, mWakeLockSummary=0x41, mUserActivitySummary=0x4, mBootCompleted=true, screenBrightnessOverride=NaN, mScreenBrightnessBoostInProgress=false, sQuiescent=false\n",
        "03-11 21:23:39.582  1074  6459 D PowerManagerService: Acquiring suspend blocker \"PowerManagerService.WakeLocks\".\n",
        "03-11 21:23:39.584  2860  5394 W PP/AppFreezeManager: in refreshFreezeAppState Acquire wake lock!\n",
        "03-11 21:23:39.602  2860  5394 W PP/AppFreezeManager: BatteryCurrentStatus Current:291mA  BatteryTemperature:30.9°C BatteryLevel:100 BatteryStatus:CHGING Screen: false"
    )*/
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            LogInsightTopBar()
        },
        bottomBar = {
            LogInsightBottomBar()
        }
    ) { innerPadding ->

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.keyword,
            onValueChange = {
                viewModel.updateKeyword(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Search logs")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterButton(
                text = "ERROR",
                modifier = Modifier.weight(1f),
                onClick = {
                    onNavigateToDetails("ERROR")
                    Log.d(TAG, "ERROR button clicked.")}
            )

            FilterButton(
                text = "WARNING",
                modifier = Modifier.weight(1f),
                onClick = {
                    onNavigateToDetails("WARNING")
                    Log.d(TAG, "WARNING button clicked.")
                }
            )

            FilterButton(
                text = "INFO",
                modifier = Modifier.weight(1f),
                onClick = {
                    onNavigateToDetails("INFO")
                    Log.d(TAG, "INFO button clicked.")
                }
            )

            FilterButton(
                text = "DEBUG",
                modifier = Modifier.weight(1f),
                onClick = {
                    onNavigateToDetails("DEBUG")
                    Log.d(TAG, "DEBUG button clicked.")
                }
            )

            FilterButton(
                text = "REFRESH",
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.refreshLogs()
                    Log.d(TAG, "Refresh logs...")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = uiState.logList,
                key = {log -> log.id}) { log ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        }
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

@Composable
fun FilterButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        Text(text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInsightTopBar() {
    TopAppBar(
        title = {
            Text("Log Insight")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@Composable
fun LogInsightBottomBar() {
    NavigationBar {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Adb,
                    contentDescription = "Logs"
                )
            },
            label = {
                Text("Logs")
            }
        )

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

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            },
            label = {
                Text("Settings")
            }
        )
    }
}
