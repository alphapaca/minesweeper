package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import initState
import update

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
        title = "Minesweeper",
        resizable = false,
    ) {
        GameWidget()
    }
}

@Composable
@Preview
fun App() {
    GameWidget()
}

@Composable
fun GameWidget(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(initState()) }
    Column(modifier.width(IntrinsicSize.Max)) {
        HorizontalBorder("top")
        VerticalBorder {
            Header(state) { action -> state = update(action, state) }
        }
        HorizontalBorder("t")
        VerticalBorder {
            PlayField(state) { action -> state = update(action, state) }
        }
        HorizontalBorder("bottom")
    }
}

@Composable
private fun HorizontalBorder(cornerType: String) {
    Row(Modifier.fillMaxWidth()) {
        Image(painterResource(resPath("border_${cornerType}_left.png")), contentDescription = null)
        Image(
            painterResource(resPath("border_horizontal.png")),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.weight(1f),
        )
        Image(painterResource(resPath("border_${cornerType}_right.png")), contentDescription = null)
    }
}

@Composable
private fun VerticalBorder(content: @Composable RowScope.() -> Unit) {
    Row(Modifier.height(IntrinsicSize.Max)) {
        Image(
            painterResource(resPath("border_vertical.png")),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxHeight(),
        )
        content()
        Image(
            painterResource(resPath("border_vertical.png")),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxHeight(),
        )
    }
}