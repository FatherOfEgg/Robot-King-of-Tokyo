package com.example.robotkingoftokyo.presentation.game_screen

import androidx.navigation.NavController
import com.example.robotkingoftokyo.domain.model.Location
import com.example.robotkingoftokyo.domain.model.RobotStat

sealed class RobotKOTEvent {
    data class StartGame(val numRobots: Int) : RobotKOTEvent()

    data object EndTurn: RobotKOTEvent()

    data class RobotIncreaseStat(val stat: RobotStat): RobotKOTEvent()
    data class RobotDecreaseStat(val stat: RobotStat): RobotKOTEvent()

    data object RerollDie: RobotKOTEvent()
    data class ToggleDiceLock(val index: Int): RobotKOTEvent()

    data class MoveTo(val location: Location): RobotKOTEvent()

    data class BuyPowerCard(val index: Int): RobotKOTEvent()
    data class DiscardPowerCard(val index: Int): RobotKOTEvent()
    data class ResetShop(val energy: Int): RobotKOTEvent()
}