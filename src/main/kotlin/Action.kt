sealed interface Action {
    // Нажатие на аватар
    data object AvatarClick : Action
    sealed interface FieldClick : Action {
        val x: Int
        val y: Int

        // Нажатие левой кнопкой мыши
        data class Left(
            override val x: Int,
            override val y: Int,
        ) : FieldClick

        // Нажатие правой кнопкой мыши
        data class Right(
            override val x: Int,
            override val y: Int,
        ) : FieldClick
    }
}