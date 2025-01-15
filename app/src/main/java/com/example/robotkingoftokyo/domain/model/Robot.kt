package com.example.robotkingoftokyo.domain.model

import kotlin.math.max
import kotlin.math.min

enum class RobotStat {
    HP,
    VICTORY_POINTS,
    ENERGY_CUBES
}

class Robot(
    val image: Int,
    var health: Int = 10,
    var victoryPoints: Int,
    var energyCubes: Int,
    var location: Location = Location.OUTSIDE,
    var hand: MutableList<PowerCard> = mutableListOf(),
) {
    fun getStat(stat: RobotStat): Int {
        return when (stat) {
            RobotStat.HP -> health
            RobotStat.VICTORY_POINTS -> victoryPoints
            RobotStat.ENERGY_CUBES -> energyCubes
        }
    }

    fun incrementStat(stat: RobotStat) {
        when (stat) {
            RobotStat.HP -> health = min(12, health + 1)
            RobotStat.VICTORY_POINTS -> victoryPoints = min(20, victoryPoints + 1)
            RobotStat.ENERGY_CUBES -> energyCubes += 1
        }
    }

    fun decrementStat(stat: RobotStat) {
        when (stat) {
            RobotStat.HP -> health = max(0, health - 1)
            RobotStat.VICTORY_POINTS -> victoryPoints = max(0, victoryPoints - 1)
            RobotStat.ENERGY_CUBES -> energyCubes = max(0, energyCubes - 1)
        }
    }

    fun moveTo(newLocation: Location) {
        location = newLocation
    }

    fun copy(
        image: Int = this.image,
        health: Int = this.health,
        victoryPoints: Int = this.victoryPoints,
        energyCubes: Int = this.energyCubes,
        location: Location = this.location,
        hand: MutableList<PowerCard> = this.hand.toMutableList()
    ): Robot {
        return Robot(image, health, victoryPoints, energyCubes, location, hand)
    }
}