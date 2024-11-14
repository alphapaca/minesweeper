package ui

import Action
import CellState
import GameState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.res.painterResource

@Composable
fun PlayField(state: GameState, dispatch: (Action) -> Unit) {
    Row {
        state.playField.forEachIndexed { x, row ->
            Column {
                row.forEachIndexed { y, cell ->
                    Cell(
                        cell,
                        onLeftClick = { dispatch(Action.FieldClick.Left(x, y)) },
                        onRightClick = { dispatch(Action.FieldClick.Right(x, y)) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Cell(cell: CellState, onLeftClick: () -> Unit, onRightClick: () -> Unit) {
    val leftClickModifier = when (cell) {
        is CellState.Hidden, is CellState.Swept -> Modifier.clickable(onClick = onLeftClick)

        is CellState.Flagged, CellState.FlaggedWrongly, CellState.ExplodedMine, CellState.ShownMine -> {
            Modifier.onClick(
                matcher = PointerMatcher.mouse(PointerButton.Primary),
                onClick = onLeftClick,
            )
        }
    }
    Image(
        painterResource(cell.toRes()),
        contentDescription = null,
        modifier = leftClickModifier
            .onClick(
                matcher = PointerMatcher.mouse(PointerButton.Secondary),
                onClick = onRightClick,
            )
    )
}

private fun CellState.toRes(): String {
    val name = when (this) {
        CellState.ExplodedMine -> "mine_red"
        is CellState.Flagged -> "flag"
        CellState.FlaggedWrongly -> "flag_wrong"
        is CellState.Hidden -> "hidden"
        CellState.ShownMine -> "mine"
        is CellState.Swept -> "swept_${neighbourMinesCount}"
    }
    return resPath("$name.svg")
}