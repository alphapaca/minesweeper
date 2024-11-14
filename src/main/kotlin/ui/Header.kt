package ui

import Action
import GamePhase
import GameState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.Header(state: GameState, dispatch: (Action) -> Unit) {
    Row(
        Modifier.weight(1f).background(Color(0xFF454C54)).padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DigitDisplay(state.flagsLeft)
        Face(state.gamePhase, dispatch)
        DigitDisplay(0)
    }
}

@Preview
@Composable
fun FacePreview() {
    Face(GamePhase.PLAYING) { }
}

@Composable
private fun Face(phase: GamePhase, dispatch: (Action) -> Unit) {
    val faceSuffix = when (phase) {
        GamePhase.LOST -> "_lose"
        GamePhase.PLAYING -> ""
        GamePhase.WON -> "_win"
    }
    Image(
        painterResource(resPath("face${faceSuffix}.svg")),
        contentDescription = null,
        modifier = Modifier.clickable { dispatch(Action.AvatarClick) }
    )
}

@Composable
private fun DigitDisplay(number: Int) {
    Box {
        Image(painterResource(resPath("nums_background.svg")), contentDescription = null)
        val cappedNumber = number.coerceAtMost(999)
        Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
            Image(painterResource((cappedNumber / 100 % 10).toRes()), contentDescription = null)
            Image(painterResource((cappedNumber / 10 % 10).toRes()), contentDescription = null)
            Image(painterResource((cappedNumber % 10).toRes()), contentDescription = null)
        }
    }
}

@Preview
@Composable
fun DigitDisplayPreview() {
    DigitDisplay(123)
}

private fun Int.toRes(): String {
    require(this in 0..9)
    return resPath("digit_${this}.svg")
}