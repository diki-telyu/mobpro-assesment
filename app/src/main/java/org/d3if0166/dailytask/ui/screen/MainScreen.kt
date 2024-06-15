package org.d3if0166.dailytask.ui.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0166.dailytask.R
import org.d3if0166.dailytask.database.TaskDb
import org.d3if0166.dailytask.model.Task
import org.d3if0166.dailytask.model.User
import org.d3if0166.dailytask.navigation.Screen
import org.d3if0166.dailytask.network.ApiStatus
import org.d3if0166.dailytask.network.UserDataStore
import org.d3if0166.dailytask.ui.theme.DailyTaskTheme
import org.d3if0166.dailytask.util.SettingsDataStore

//import org.d3if0166.dailytask.util.ViewModelFactory

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val userDataStore = UserDataStore(context)
    val dataStore = SettingsDataStore(LocalContext.current)
    val user by userDataStore.userFlow.collectAsState(initial = User())
//    Log.d("UserDataStore", "User loaded: $user")
    val showList by dataStore.layoutFlow.collectAsState(true)
    val viewModel: MainViewModel = viewModel()

    var expanded by remember { mutableStateOf(false) }

    val gradientColors = listOf(
        Color(0xFF0D562D),  // Hijau tua
        Color(0xFF34A853), // Hijau muda
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name), color = Color.White
                )
            }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,  // Set to transparent as background will be covered by gradient
                titleContentColor = Color.White
            ), modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ), actions = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(R.string.riwayat),
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.padding(2.dp)
                ) {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        showDialog = true
                    }, text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(
                                    R.drawable.baseline_account_circle_24
                                ),
                                contentDescription = stringResource(R.string.profil),
                                tint = Color.Black,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = stringResource(R.string.profil),

                                )
                        }
                    })
                    DropdownMenuItem(onClick = {
                        expanded = false
                        navController.navigate(Screen.History.route)
                    }, text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_history_24),
                                contentDescription = stringResource(R.string.riwayat),
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = stringResource(R.string.riwayat),

                                )
                        }
                    })
                    DropdownMenuItem(onClick = {
                        expanded = false
                        showDialog = true
                    }, text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_logout_24),
                                contentDescription = stringResource(R.string.logout),
                                tint = Color.Black,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = stringResource(R.string.logout),

                                )
                        }
                    })
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }, containerColor = Color(0xFF34A853)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.description),
                    tint = Color.White
                )
            }
        },


        ) { padding ->
        ScreenContent(viewModel, Modifier.padding(padding), navController)
    }

    if (showDialog) {
        ProfilDialog(user = user, onDismissRequest = { showDialog = false }) {
            CoroutineScope(Dispatchers.IO).launch { signOut(context, userDataStore, navController) }
            showDialog = false
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.N)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ScreenContent(viewModel: MainViewModel, modifier: Modifier, navController: NavController) {
    val data by viewModel.data
    val status by viewModel.status.collectAsState()
    val context = LocalContext.current
    val userDataStore = UserDataStore(context)
    val user by userDataStore.userFlow.collectAsState(initial = User())

    viewModel.retrieveData(user.email)


    when (status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        ApiStatus.SUCCESS -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(task = it, viewModel = viewModel) {
                        navController.navigate(Screen.FormUbah.withId(it.task_id))
                    }
//                    Divider()
                }
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "failed")
                Button(
                    onClick = {
                        viewModel.retrieveData(user.email)
                    },
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = "Try again")
                }
            }
        }
    }
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    borderRadius: Int = 4,
) {
    val borderColor = if (checked) MaterialTheme.colorScheme.primary else Color.Gray
    val checkMarkColor = if (checked) MaterialTheme.colorScheme.onPrimary else Color.Transparent

    Box(modifier = modifier
        .size(24.dp)
        .background(color = Color.Transparent, shape = RoundedCornerShape(borderRadius.dp))
        .clickable { onCheckedChange(!checked) }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = borderColor,
                size = size,
                style = Stroke(width = 2.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(borderRadius.dp.toPx())
            )
            if (checked) {
                val path = Path().apply {
                    moveTo(size.width * 0.2f, size.height * 0.5f)
                    lineTo(size.width * 0.4f, size.height * 0.7f)
                    lineTo(size.width * 0.8f, size.height * 0.3f)
                }
                drawPath(path, color = checkMarkColor, style = Stroke(width = 2.dp.toPx()))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ListItem(task: Task, viewModel: MainViewModel, onClick: () -> Unit) {
    remember { mutableStateOf(false) }
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    // Calculate checkbox size based on screen width
    val checkboxSize = (configuration.screenWidthDp * 0.05).dp
    val finalCheckboxSize = min(checkboxSize, 24.dp)  // Ensuring it doesn't exceed 24.dp

    val colors = listOf(
        Color.Blue, Color.Red, Color.Green
    )

    val randomColor = remember { mutableStateOf(colors.random()) }

    // Saat data ditambahkan, pilih warna secara acak
    LaunchedEffect(task) {
        randomColor.value = colors.random()
    }


//    val currentDateTime = LocalDateTime.now()
//    val dueDateTime = LocalDateTime.parse(task.dueDate, DateTimeFormatter.ISO_DATE_TIME)
//
//    val isDatePassed = currentDateTime.isAfter(dueDateTime)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .background(color = randomColor.value),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = RoundedCornerShape(8.dp),
//            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = task.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )

                        if (!task.detail.isNullOrBlank()) {
                            Text(
                                text = task.detail,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(bottom = 4.dp)  // Add bottom padding to separate from the due date text
                            )
                        }
                        if (!task.due_date.isNullOrBlank()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Schedule,
                                    contentDescription = "Due",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xFF4285F4)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = task.due_date,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF4285F4)

                                )
                            }

                        } else {

                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))  // Add space between text and checkbox
                    CustomCheckbox(
                        checked = task.is_completed,
                        onCheckedChange = { isChecked ->
//                            viewModel.taskDone(task.id)
                            Toast.makeText(context, R.string.selesai, Toast.LENGTH_LONG).show()
                        },
                        modifier = Modifier.size(finalCheckboxSize),
                        borderRadius = 4
                    )
                }
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyTaskTheme {
        MainScreen(rememberNavController())
    }
}