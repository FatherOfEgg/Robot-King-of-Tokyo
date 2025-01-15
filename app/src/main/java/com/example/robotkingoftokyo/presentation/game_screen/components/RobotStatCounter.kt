package com.example.robotkingoftokyo.presentation.game_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkingoftokyo.R
import com.example.robotkingoftokyo.domain.model.RobotStat
import com.example.robotkingoftokyo.domain.util.currentRobot
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTEvent
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTViewModel

@Composable
fun RobotStatCounter(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel,
    stat: RobotStat,
    swapArrows: Boolean
) {
    val state = viewModel.state.value

    if (swapArrows) {
        IconButton(
            modifier = modifier,
            onClick = { viewModel.onEvent(RobotKOTEvent.RobotIncreaseStat(stat)) }
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = "Increases robot $stat stat"
            )
        }
    } else {
        IconButton(
            modifier = modifier,
            onClick = { viewModel.onEvent(RobotKOTEvent.RobotDecreaseStat(stat)) }
        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrease robot $stat stat"
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${currentRobot(state).getStat(stat)}",
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(
                id = when (stat) {
                    RobotStat.HP -> R.drawable.counter_health
                    RobotStat.VICTORY_POINTS -> R.drawable.counter_victory_points
                    RobotStat.ENERGY_CUBES -> R.drawable.counter_energy
                }
            ),
            contentDescription = null,
            modifier = modifier.size(20.dp)
        )
    }

    if (swapArrows) {
        IconButton(
            modifier = modifier,
            onClick = { viewModel.onEvent(RobotKOTEvent.RobotDecreaseStat(stat)) }
        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrease robot $stat stat"
            )
        }
    } else {
        IconButton(
            modifier = modifier,
            onClick = { viewModel.onEvent(RobotKOTEvent.RobotIncreaseStat(stat)) }
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = "Increases robot $stat stat"
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RobotStatCounterPreview(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RobotStatCounter(
            modifier = modifier,
            viewModel = RobotKOTViewModel(LocalContext.current),
            stat = RobotStat.ENERGY_CUBES,
            swapArrows = false
        )
    }
}