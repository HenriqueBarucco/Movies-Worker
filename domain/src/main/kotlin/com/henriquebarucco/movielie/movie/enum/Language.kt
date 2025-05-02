package com.henriquebarucco.movielie.movie.enum

enum class Language(
    val code: String,
) {
    ENGLISH("en"),
    HINDI("hi"),
    MANDARIN("zh"),
    SPANISH("es"),
    FRENCH("fr"),
    ARABIC("ar"),
    RUSSIAN("ru"),
    PORTUGUESE("pt"),
    JAPANESE("ja"),
    KOREAN("ko"),
    TAMIL("ta"),
    TELUGU("te"),
    BENGALI("bn"),
    GERMAN("de"),
    TURKISH("tr"),
    ITALIAN("it"),
    URDU("ur"),
    MALAYALAM("ml"),
    THAI("th"),
    INDONESIAN("id"),
    CZECH("cs"),
    SWEDISH("sv"),
    DANISH("da"),
    HUNGARIAN("hu"),
    POLISH("pl"),
    TAGALOG("tl"),
    GREEK("el"),
    MARATHI("mr"),
    DUTCH("nl"),
    ;

    companion object {
        fun from(code: String): Language =
            Language.entries.firstOrNull { it.code == code } ?: throw IllegalArgumentException("Unknown language code: $code")
    }
}
