package org.d3if0166.dailytask.ui.screen

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.d3if0166.dailytask.R
import org.d3if0166.dailytask.component.CustomDatePicker
import org.d3if0166.dailytask.database.TaskDb
import org.d3if0166.dailytask.model.Task
import org.d3if0166.dailytask.model.User
import org.d3if0166.dailytask.network.TaskApi
import org.d3if0166.dailytask.network.UserApi
import org.d3if0166.dailytask.network.UserDataStore
import org.d3if0166.dailytask.ui.theme.DailyTaskTheme
import org.d3if0166.dailytask.util.SettingsDataStore

//import org.d3if0166.dailytask.util.ViewModelFactory

const val KEY_ID_TASK = "idTask"

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, id: Long? = null) {
    val context = LocalContext.current

    var taskName by remember { mutableStateOf("") }
    var taskDetail by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val viewModel: MainViewModel = viewModel()

    val gradientColors = listOf(
        Color(0xFF0D562D),  // Hijau tua
        Color(0xFF34A853)   // Hijau muda
    )

    LaunchedEffect(id) {
        if (id == null) return@LaunchedEffect
        try {
            val response = TaskApi.service.getTaskById(id)
//                val task = response.()

        } catch (e: Exception) {
            Log.e("DetailScreen", "Exception while fetching task: ${e.message}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_tugas))
                    else
                        Text(text = stringResource(id = R.string.edit_tugas))
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
                    IconButton(onClick = {
                        if (taskName.isBlank()) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(taskName, taskDetail, taskDueDate, user_id = "dikirahman@gmail.com")
                        } else {
//                            viewModel.update(id, taskName, taskDetail, taskDueDate, user_id = "dikirahman@gmail.com")
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.tombol_simpan),
                            tint = Color.White
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
//                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        Form(
            title = taskName,
            onTitleChange = { taskName = it },
            desc = taskDetail,
            onDescChange = { taskDetail = it },
            due_date = taskDueDate,
            onDueDateChange = { taskDueDate = it },
            modifier = Modifier.padding(padding),
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {expanded = true}) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.lainnya),
            tint = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )

        }

    }
}


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(
    title: String, onTitleChange: (String) -> Unit,
    desc: String, onDescChange: (String) -> Unit,
    due_date: String, onDueDateChange: (String) -> Unit,
    modifier: Modifier
) {

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        OutlinedTextField(
            value = title,
            onValueChange =  onTitleChange,
            label = { Text(text = stringResource(id =R.string.field_nama_tugas )) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = desc,
            onValueChange = onDescChange,
            label = { Text(text = stringResource(id = R.string.description)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth().height(120.dp) // Atur ketinggian sesuai kebutuhan
        )
        CustomDatePicker(
            label = "Tenggat Waktu",
            context = LocalContext.current,
            value = due_date,
            onValueChange = onDueDateChange,
            modifier = Modifier.fillMaxWidth()
        )

    }

}


@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    DailyTaskTheme {
        DetailScreen(rememberNavController())
    }
}