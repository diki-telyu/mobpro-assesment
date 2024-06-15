package org.d3if0166.dailytask.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.d3if0166.dailytask.R
import org.d3if0166.dailytask.database.TaskDb
import org.d3if0166.dailytask.model.Task
import org.d3if0166.dailytask.navigation.Screen
//import org.d3if0166.dailytask.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.riwayat))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding), navController)
    }
}

@Composable
fun ScreenContent(modifier: Modifier, navController: NavController) {
//    val context = LocalContext.current
//    val db = TaskDb.getInstance(context)
//    val factory = ViewModelFactory(db.dao)
//    val viewModel: HistoryViewModel = viewModel(factory = factory)
//    val data by viewModel.data.collectAsState()
//
//    if (data.isEmpty()) {
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(1.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = stringResource(id = R.string.list_kosong))
//        }
//    } else {
//        LazyColumn(
//            modifier = modifier.fillMaxSize(),
//            contentPadding = PaddingValues(bottom = 84.dp)
//        ) {
//            items(data) {
//                ListHistoryItem(task = it, viewModel = viewModel) {
//                    navController.navigate(Screen.FormUbah.withId(it.task_id))
//                }
//            }
//        }
//    }
}

@Composable
fun ListHistoryItem(task: Task, viewModel: HistoryViewModel, onClick: () -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
    )
    {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.selesai),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                    .clickable { onClick() }
                .padding(15.dp, 0.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Text(
                text = task.name,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textDecoration = TextDecoration.LineThrough
            )

        }
    }
}