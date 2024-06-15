package org.d3if0166.dailytask.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import org.d3if0166.dailytask.network.UserDataStore
import org.d3if0166.dailytask.ui.theme.DailyTaskTheme
import org.d3if0166.dailytask.util.SettingsDataStore
import org.d3if0166.dailytask.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val userDataStore = UserDataStore(context)
    val dataStore = SettingsDataStore(LocalContext.current)
    val user by userDataStore.userFlow.collectAsState(initial = User())
    val showList by dataStore.layoutFlow.collectAsState(true)

    var expanded by remember { mutableStateOf(false) }

    val gradientColors = listOf(
        Color(0xFF0D562D),  // Hijau tua
        Color(0xFF34A853), // Hijau muda
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,  // Set to transparent as background will be covered by gradient
                    titleContentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = gradientColors
                        )
                    ),
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.riwayat),
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.padding(2.dp)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                showDialog = true
                            },
                            text = {
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
                            }
                        )
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                navController.navigate(Screen.History.route)
                            },
                            text = {
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
                            }
                        )
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                showDialog = true
                            },
                            text = {
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
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                },
                containerColor = Color(0xFF34A853)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.description),
                    tint = Color.White
                )
            }
        },


        ) { padding ->
        ScreenContent(showList, Modifier.padding(padding), navController)
    }

    if (showDialog) {
        ProfilDialog(
            user = user,
            onDismissRequest = { showDialog = false }) {
            CoroutineScope(Dispatchers.IO).launch { signOut(context, userDataStore, navController) }
            showDialog = false
        }
    }
}

@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val db = TaskDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()


    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(task = it, viewModel = viewModel) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                    Divider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp),
                modifier = modifier.fillMaxSize()
            ) {
                items(data) {
                    GridItem(task = it, viewModel = viewModel) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(task: Task, viewModel: MainViewModel, onClick: () -> Unit) {
    val checkedState = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    )
    {
        Checkbox(
            checked = !task.status,
            onCheckedChange = { isChecked ->
//                checkedState.value = isChecked
                // Perbarui status Task saat checkbox diklik
                viewModel.taskDone(task.id)
                Toast.makeText(context, R.string.selesai, Toast.LENGTH_LONG).show()
            },
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Text(
                text = task.judul,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )

            if (!task.detail.isEmpty())
                Text(
                    text = task.detail,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal
                )
        }
    }
}

@Composable
fun GridItem(task: Task, viewModel: MainViewModel, onClick: () -> Unit) {
    val checkedState = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = !task.status,
                onCheckedChange = { isChecked ->
//                    checkedState.value = isChecked
                    // Perbarui status Task saat checkbox diklik
                    viewModel.taskDone(task.id)
                    Toast.makeText(context, R.string.selesai, Toast.LENGTH_LONG).show()
                }
            )
            Text(
                text = task.judul,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = task.detail,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyTaskTheme {
        MainScreen(rememberNavController())
    }
}