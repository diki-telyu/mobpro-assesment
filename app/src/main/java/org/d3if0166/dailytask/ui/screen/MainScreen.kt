package org.d3if0166.dailytask.ui.screen

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.d3if0166.dailytask.R
import org.d3if0166.dailytask.model.Task
import org.d3if0166.dailytask.ui.theme.DailyTaskTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0166.dailytask.database.TaskDb
import org.d3if0166.dailytask.navigation.Screen
import org.d3if0166.dailytask.ui.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    var namaTugas by remember { mutableStateOf("") }
    var deskripsiTugas by remember { mutableStateOf("") }
    var namaTugasError by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState()

    Scaffold (
    topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            actions = {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.About.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(id = R.string.tentang_aplikasi),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.buat_tugas),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },

        
) { padding ->
    ScreenContent(Modifier.padding(padding))
}

    if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(375.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),

                title = {
                    Text(text = stringResource(id = R.string.buat_tugas))
                },
                text = {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = namaTugas,
                            isError = namaTugasError,
                            supportingText = { ErrorHint(isError = namaTugasError) },
                            onValueChange = { namaTugas = it },
                            label = { Text(stringResource(id = R.string.field_nama_tugas)) }
                        )
                        OutlinedTextField(
                            value = deskripsiTugas,
                            onValueChange = { deskripsiTugas = it },
                            label = { Text(stringResource(id = R.string.field_deskripsi_tugas)) },
                            maxLines = 5
                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        DatePicker(
//                            title = {
//                                Text(
//                                    text = stringResource(id = R.string.field_tenggat_tugas)
//                                )
//                            },
//                            state = dateState,
//                            modifier = Modifier.fillMaxWidth(),
//                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.tombol_batal))
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            namaTugasError = (namaTugas == "")
                            if (namaTugasError) return@Button
                            else
                                openDialog.value = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.tombol_simpan))
                    }
                }
            )
        }

}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(id = R.string.input_invalid))
    }
}


@Composable
fun ScreenContent(modifier: Modifier) {
    val context = LocalContext.current
    val db = TaskDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()


    if (data.isEmpty()) {
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) {
                ListItem(task = it) {
                    val pesan = context.getString(R.string.x_diklik, it.judul)
                    Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show()
                }
                Divider()
            }
        }
    }
}

@Composable
fun ListItem(task: Task, onClick: () -> Unit) {
    val checkedState = remember { mutableStateOf(false) }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    )
    {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
        )
        Column (
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyTaskTheme {
        MainScreen(rememberNavController())
    }
}