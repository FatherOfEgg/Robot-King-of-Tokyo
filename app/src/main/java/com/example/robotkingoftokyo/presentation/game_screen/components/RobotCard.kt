package com.example.robotkingoftokyo.presentation.game_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTViewModel
import com.example.robotkingoftokyo.presentation.game_screen.util.imageToName

@Composable
fun RobotCard(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel
) {
    val state = viewModel.state.value
    val robot = currentRobot(state)

    Card(
        modifier = modifier
            .size(370.dp, 260.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .align(Alignment.TopStart)
            ) {
                RobotStatCounter(
                    modifier = modifier,
                    viewModel = viewModel,
                    stat = RobotStat.VICTORY_POINTS,
                    swapArrows = false
                )
            }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                RobotStatCounter(
                    modifier = modifier,
                    viewModel = viewModel,
                    stat = RobotStat.ENERGY_CUBES,
                    swapArrows = true
                )
                RobotStatCounter(
                    modifier = modifier,
                    viewModel = viewModel,
                    stat = RobotStat.HP,
                    swapArrows = true
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .align(Alignment.Center)
            ) {
                Text(
                    text = imageToName[robot.image]!!,
                    textAlign = TextAlign.Center,
                )

                Image(
                    painter = painterResource(id = robot.image),
                    contentDescription = "Image Description",
                    modifier = Modifier
                        .size(128.dp)
                )
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RobotCardPreview(modifier: Modifier = Modifier) {
    RobotCard(
        modifier = modifier,
        viewModel = RobotKOTViewModel(LocalContext.current)
    )
}