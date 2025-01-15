package com.example.robotkingoftokyo.presentation.game_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robotkingoftokyo.R
import com.example.robotkingoftokyo.domain.model.Location
import com.example.robotkingoftokyo.domain.model.Robot
import com.example.robotkingoftokyo.domain.util.currentRobot
import com.example.robotkingoftokyo.domain.util.loadPowerCards
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RobotKOTViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
): ViewModel() {
    private val _state = mutableStateOf(RobotKOTState())
    val state: State<RobotKOTState> = _state

    init {
        viewModelScope.launch {
            _state.value.pcDrawPile = withContext(Dispatchers.IO) {
                loadPowerCards(context)
            }
        }
    }

    fun onEvent(event: RobotKOTEvent) {
        when (event) {
            is RobotKOTEvent.StartGame -> {
                val robots = mutableListOf(
                    R.drawable.robot_red,
                    R.drawable.robot_green,
                    R.drawable.robot_yellow,
                    R.drawable.robot_white,
                    R.drawable.robot_pink,
                    R.drawable.robot_purple
                )

                for (i in 0 until event.numRobots) {
                    val randomIndex = (robots.indices).random()
                    val selectedRobot = robots[randomIndex]
                    robots.removeAt(randomIndex)

                    _state.value.robots.add(Robot(
                        selectedRobot,
                        health = 10,
                        victoryPoints = 0,
                        energyCubes = 0,
                        location = Location.OUTSIDE,
                        hand = mutableListOf()
                    ))
                }

                populateShop()
            }

            RobotKOTEvent.EndTurn -> {
                _state.value = _state.value.copy(
                    curRobot = (_state.value.curRobot + 1) % _state.value.robots.size
                )
            }

            is RobotKOTEvent.RobotIncreaseStat-> {
                val r = currentRobot(state.value).copy().apply {
                    incrementStat(event.stat)
                }

                updateRobot(r)
            }
            is RobotKOTEvent.RobotDecreaseStat -> {
                val r = currentRobot(state.value).copy().apply {
                    decrementStat(event.stat)
                }

                updateRobot(r)
            }

            RobotKOTEvent.RerollDie -> {
                val newDie = _state.value.die.copy()
                newDie.rollDie()

                _state.value = _state.value.copy(
                    die = newDie
                )
            }
            is RobotKOTEvent.ToggleDiceLock -> {
                val newDie = _state.value.die.copy()
                newDie.toggleLock(event.index)

                _state.value = _state.value.copy(
                    die = newDie
                )
            }

            is RobotKOTEvent.BuyPowerCard -> {
                val pc = _state.value.pcShop[event.index]
                val currentRobot = currentRobot(state.value)

                if (currentRobot.energyCubes < pc.cost) {
                    Toast.makeText(context, "Not enough energy", Toast.LENGTH_SHORT).show()
                    return
                }

                val r = currentRobot(state.value).copy().apply {
                    this.hand.add(pc)
                    this.energyCubes -= pc.cost
                }

                updateRobot(r)

                if (_state.value.pcDrawPile.isEmpty()) {
                    _state.value.pcDrawPile.addAll(_state.value.pcDiscardPile)
                    _state.value.pcDiscardPile.clear()
                }

                _state.value = _state.value.copy(
                    pcShop = _state.value.pcShop.clone().apply {
                        val randomIndex = (_state.value.pcDrawPile.indices).random()
                        val newPc = _state.value.pcDrawPile[randomIndex]
                        _state.value.pcDrawPile.removeAt(randomIndex)
                        this[event.index] = newPc
                    }
                )
            }
            is RobotKOTEvent.DiscardPowerCard -> {
                val pc = currentRobot(state.value).hand[event.index]
                _state.value.pcDiscardPile.add(pc)

                val r = currentRobot(state.value).copy().apply {
                    this.hand.removeAt(event.index)
                }

                updateRobot(r)
            }
            is RobotKOTEvent.ResetShop -> {
                val currentRobot = currentRobot(state.value)

                if (currentRobot.energyCubes < 2) {
                    Toast.makeText(context, "Not enough energy to reroll shop", Toast.LENGTH_SHORT).show()
                    return
                }

                val r = currentRobot(state.value).copy().apply {
                    this.energyCubes -= 2
                }

                updateRobot(r)

                _state.value = _state.value.copy(
                    pcShop = _state.value.pcShop.clone().apply {
                        _state.value.pcDiscardPile.addAll(_state.value.pcShop)

                        for (i in 0 until 3) {
                            if (_state.value.pcDrawPile.isEmpty()) {
                                _state.value.pcDrawPile.addAll(_state.value.pcDiscardPile)
                                _state.value.pcDiscardPile.clear()
                            }

                            val randomIndex = (_state.value.pcDrawPile.indices).random()
                            val newPc = _state.value.pcDrawPile[randomIndex]
                            _state.value.pcDrawPile.removeAt(randomIndex)
                            this[i] = newPc
                        }
                    }
                )
            }

            is RobotKOTEvent.MoveTo -> {
                val r = currentRobot(state.value).copy().apply {
                    moveTo(event.location)
                }

                updateRobot(r)
            }
        }
    }

    private fun populateShop() {
        for (i in 0 until 3) {
            val randomIndex = (_state.value.pcDrawPile.indices).random()
            val selectedPc = _state.value.pcDrawPile[randomIndex]
            _state.value.pcDrawPile.removeAt(randomIndex)
            _state.value.pcShop[i] = selectedPc
        }
    }

    private fun updateRobot(robot: Robot) {
        _state.value = _state.value.copy(
            robots = _state.value.robots.toMutableList().also { robots ->
                val robotIndex = _state.value.curRobot

                robots[robotIndex] = robot
            }
        )
    }
}