package com.involvex.ytmp3dlp.database.models

data class SearchSuggestionItem(
    var text: String,
    val type: SearchSuggestionType,
)

enum class SearchSuggestionType{
    SUGGESTION, HISTORY, CLIPBOARD
}
