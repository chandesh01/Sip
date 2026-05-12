package com.sip.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sip.data.WaterEntry
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HistoryScreen(
    paddingValues: PaddingValues,
    entries: List<WaterEntry>,
    onDeleteEntry: (WaterEntry) -> Unit
) {
    // Track the entry the user is trying to delete
    var entryToDelete by remember { mutableStateOf<WaterEntry?>(null) }

    // Material 3 Confirmation Dialog
    if (entryToDelete != null) {
        AlertDialog(
            onDismissRequest = { entryToDelete = null },
            title = { Text("Delete Entry?") },
            text = { Text("Are you sure you want to remove this entry?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteEntry(entryToDelete!!)
                        entryToDelete = null
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { entryToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "History",
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(
            items = entries,
            key = { it.id }
        ) { entry ->
            SwipeToDeleteItem(
                modifier = Modifier.animateItem(),
                entry = entry,
                onConfirmDelete = {
                    entryToDelete = entry
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteItem(
    modifier: Modifier = Modifier,
    entry: WaterEntry,
    onConfirmDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()

    // Trigger the dialog and reset the swipe immediately
    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
            onConfirmDelete()
            // IMPORTANT: Reset the state so the red background snaps back
            // and doesn't "hang" while the dialog is open or after deletion
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            // Use targetValue to decide color so it feels more responsive
            val isSwiping = dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isSwiping) Color.Red.copy(alpha = 0.8f) else Color.Transparent,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (isSwiping) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        HistoryEntryItem(entry = entry)
    }
}

@Composable
private fun HistoryEntryItem(
    entry: WaterEntry
) {

    val formatter = remember {
        SimpleDateFormat(
            "dd MMM • hh:mm a",
            java.util.Locale.getDefault()
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(32.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(
                    horizontal = 22.dp,
                    vertical = 20.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = "${entry.amount} ml",

                style = MaterialTheme
                    .typography
                    .titleLarge
            )

            Text(
                text = formatter.format(
                    Date(entry.timestamp)
                ),

                style = MaterialTheme
                    .typography
                    .bodyMedium,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}