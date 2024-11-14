private const val MINES_COUNT = 10
private const val FIELD_WIDTH = 9
private const val FIELD_HEIGHT = 9

fun initState(): GameState {
    val field = (0..<FIELD_WIDTH * FIELD_HEIGHT)
        .map { index ->
            if (index < MINES_COUNT) {
                CellState.Hidden(true)
            } else {
                CellState.Hidden(false)
            }
        }
        .shuffled()
        .chunked(FIELD_WIDTH)
    return GameState(
        playField = field,
        flagsLeft = 10,
        gamePhase = GamePhase.PLAYING,
    )
}

fun update(action: Action, oldState: GameState): GameState {
    println(action)
    return when (action) {
        Action.AvatarClick -> initState()
        is Action.FieldClick -> oldState
    }
}