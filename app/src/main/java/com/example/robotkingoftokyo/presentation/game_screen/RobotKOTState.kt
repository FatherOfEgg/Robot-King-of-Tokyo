package com.example.robotkingoftokyo.presentation.game_screen

import com.example.robotkingoftokyo.domain.model.Dice
import com.example.robotkingoftokyo.domain.model.PowerCard
import com.example.robotkingoftokyo.domain.model.PowerCardType
import com.example.robotkingoftokyo.domain.model.Robot

data class RobotKOTState(
    var pcDrawPile: MutableList<PowerCard> = mutableListOf(),
    val pcShop: Array<PowerCard> = Array(3) {
        PowerCard("", 0, PowerCardType.KEEP, "")
        PowerCard("", 0, PowerCardType.KEEP, "")
        PowerCard("", 0, PowerCardType.KEEP, "")
    },
    val pcDiscardPile: MutableList<PowerCard> = mutableListOf(),
    val robots: MutableList<Robot> = mutableListOf(),
    var curRobot: Int = 0,
    var numRobots: Int = 0,
    var die: Dice = Dice()
)