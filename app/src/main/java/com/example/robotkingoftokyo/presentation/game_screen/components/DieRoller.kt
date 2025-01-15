package com.example.robotkingoftokyo.presentation.game_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTEvent
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTViewModel

const val DICE_SIZE = 100
const val NUM_ROWS = 2
const val NUM_COLS = 3

@Composable
fun DieRoller(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel
) {
    val state = viewModel.state.value
    val die = state.die

    Column(
        modifier = modifier
    ) {
        for (r in 0 until NUM_ROWS) {
            Row(
                modifier = modifier
            ) {
                for (c in 0 until NUM_COLS) {
                    val i = r * NUM_COLS + c
                    val dice = die.getDie()[i]

                    Image(
                        painter = painterResource(id = die.getDieImage(dice)),
                        contentDescription = "Dice with value ${dice.name}",
                        modifier = modifier
                            .size(DICE_SIZE.dp)
                            .clickable {
                                viewModel.onEvent(RobotKOTEvent.ToggleDiceLock(i))
                            }
                            .border(
                                width = 2.dp,
                                color = if (die.locked[i]) {
                                    Color.Green
                                } else {
                                    Color.Transparent
                                }
                            )
                    )
                }
            }
        }
    }

    Button(
        onClick = {
            viewModel.onEvent(RobotKOTEvent.RerollDie)
        }
    ) {
        Text("Reroll")
    }
}

@Preview(showSystemUi = true)
@Composable
fun DieRollerPreview(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DieRoller(
            modifier = modifier,
            RobotKOTViewModel(LocalContext.current)
        )
    }
}