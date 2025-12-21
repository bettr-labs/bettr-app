package org.example.bettr.domain.model

enum class DreamType {
    HOME, TRAVEL, MONEY, CAR, STUDY, WEDDING, VACATION, HEALTH, OTHER;

    companion object {
        fun fromKey(key: String): DreamType {
            return when (key.uppercase()) {
                "HOME" -> HOME
                "TRAVEL" -> TRAVEL
                "MONEY" -> MONEY
                "CAR" -> CAR
                "STUDY" -> STUDY
                "WEDDING" -> WEDDING
                "VACATION" -> VACATION
                "HEALTH" -> HEALTH
                else -> OTHER
            }
        }
    }
}