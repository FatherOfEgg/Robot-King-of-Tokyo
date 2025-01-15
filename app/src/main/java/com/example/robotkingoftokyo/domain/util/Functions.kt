package com.example.robotkingoftokyo.domain.util

import android.content.Context
import android.util.Log
import com.example.robotkingoftokyo.domain.model.PowerCard
import com.example.robotkingoftokyo.domain.model.PowerCardType
import com.example.robotkingoftokyo.domain.model.Robot
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTState
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

const val TAG = "POWER_CARD"

fun currentRobot(state: RobotKOTState): Robot {
    return state.robots[state.curRobot]
}

fun loadPowerCards(context: Context): MutableList<PowerCard> {
    Log.d(TAG, "loadPowerCards")

    val powerCards: MutableList<PowerCard> = mutableListOf()

    try {
        val reader = BufferedReader(
            InputStreamReader(context.assets.open("power_cards.csv"))
        )

        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val columns = line.split(",", limit = 4)
                powerCards.add(PowerCard(
                    name = columns[0],
                    cost = columns[1].toInt(),
                    type = if (columns[2] == "Keep") {
                        PowerCardType.KEEP
                    } else {
                        PowerCardType.DISCARD
                    },
                    description = columns[3].trim('"')
                ))
            }
        }

        reader.close()
    } catch (e: IOException) {
        Log.d(TAG, "loadPowerCards: IOException")
        e.printStackTrace()
    }

//    Log.d(TAG, "${powerCards.size}")

    return powerCards
}