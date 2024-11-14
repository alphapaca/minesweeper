/**
 * Описывает состояние одной клетки игрового поля
 */
sealed interface CellState {
    val mined: Boolean

    /**
     * Не открытая клетка
     */
    data class Hidden(override val mined: Boolean) : CellState

    /**
     * Клетка помеченная флагом
     */
    data class Flagged(override val mined: Boolean) : CellState

    /**
     * Показывается в момент поражения если клетка была помечена флагом не правильно (там не было мины)
     */
    data object FlaggedWrongly : CellState {
        override val mined: Boolean = true
    }

    /**
     * Мина на которую на которую не натыкались, но тем не менее она показана в момент поражения/победы
     */
    data object ShownMine : CellState {
        override val mined: Boolean = true
    }

    /**
     * Мина которую нажал пользователь что привело к поражению
     */
    data object ExplodedMine : CellState {
        override val mined: Boolean = true
    }

    /**
     * Открытая клетка
     * @param neighbourMinesCount - количество мин в соседних клетках
     */
    data class Swept(
        val neighbourMinesCount: Int, // 0..8
    ) : CellState {
        override val mined: Boolean = false
    }
}

enum class GamePhase {
    LOST,
    PLAYING,
    WON,
}

data class GameState(
    val playField: List<List<CellState>>,
    val flagsLeft: Int,
    val gamePhase: GamePhase,
)