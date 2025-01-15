package com.example.robotkingoftokyo.domain.model

enum class PowerCardType {
    KEEP,
    DISCARD
}

class PowerCard(
    var name: String,
    var cost: Int,
    var type: PowerCardType,
    var description: String,
) {
}