package com.example.wazup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wazup.ui.theme.WazUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WazUpTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Message()
                }
            }
        }
    }
}

@Composable
fun Message() {
    var nameState by rememberSaveable { mutableStateOf("") }
    var messageState by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        GetMessage(
            name = nameState,
            onNameChanged = { nameState = it },
            mess = messageState,
            onMessChanged = { messageState = it },
        )
        Spacer(modifier = Modifier.height(4.dp))
        MessageCart(
            name = nameState,
            mess = messageState,
        )
    }
}

@Composable
fun GetMessage(name: String,
               onNameChanged: (String) -> Unit,
               mess: String,
               onMessChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        TextField(value = name, onValueChange = onNameChanged)
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = mess, onValueChange = onMessChanged)
    }
}

@Composable
fun MessageCart(
    name: String,
    mess: String,
    ) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val surfaceColor: Color by animateColorAsState(
        targetValue = if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )


    Column(modifier = Modifier
        .clickable { isExpanded = !isExpanded}
        .padding(8.dp)
        .fillMaxWidth(1f)) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.subtitle2
        )
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 1.dp,
            color = surfaceColor,
            modifier = Modifier
                .animateContentSize()
                .padding(1.dp)
        ) {
            Text(
                text = mess,
                modifier = Modifier.padding(all = 4.dp),
                // If the message is expanded, we display all its content
                // otherwise we only display the first line
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
