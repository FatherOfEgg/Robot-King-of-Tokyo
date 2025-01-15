package com.example.robotkingoftokyo.presentation.game_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.robotkingoftokyo.R
import com.example.robotkingoftokyo.domain.model.Location
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTEvent
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTViewModel

@Composable
fun TokyoMap(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel
) {
    val state = viewModel.state.value

    Column(
        modifier = modifier
            .padding(4.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = modifier
                    .border(2.dp, Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Tokyo City",
                    fontSize = 30.sp
                )

                val tokyoCityRobot = state.robots.find { it.location == Location.TOKYO_CITY }

                if (tokyoCityRobot != null) {
                    Image(
                        painter = painterResource(id = tokyoCityRobot.image),
                        contentDescription = null,
                        modifier = modifier
                            .size(150.dp)
                    )
                } else {
                    Box(
                        modifier = modifier
                            .size(150.dp)
                            .background(Color(173, 17, 12))
                            .clickable(onClick = {
                                viewModel.onEvent(RobotKOTEvent.MoveTo(Location.TOKYO_CITY))
                            })
                    ) {
                        Text(
                            "Enter Tokyo City",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            color = Color.White,
                            modifier = modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            if (state.robots.size >= 5) {
                Spacer(modifier = modifier.size(15.dp))
                Column (
                    modifier = modifier
                        .border(2.dp, Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Tokyo Bay",
                        fontSize = 30.sp
                    )

                    val tokyoBayRobot = state.robots.find { it.location == Location.TOKYO_BAY }

                    if (tokyoBayRobot != null) {
                        Image(
                            painter = painterResource(id = tokyoBayRobot.image),
                            contentDescription = null,
                            modifier = modifier
                                .size(150.dp)
                        )
                    } else {
                        Box(
                            modifier = modifier
                                .size(150.dp)
                                .background(Color(28, 100, 217))
                                .clickable(onClick = {
                                    viewModel.onEvent(RobotKOTEvent.MoveTo(Location.TOKYO_BAY))
                                })
                        ) {
                            Text(
                                "Enter Tokyo Bay",
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                color = Color.White,
                                modifier = modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(2.dp, Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Outside",
                fontSize = 20.sp
            )
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color(111, 123, 143))
                    .padding(8.dp)
                    .clickable(onClick = {
                        viewModel.onEvent(RobotKOTEvent.MoveTo(Location.OUTSIDE))
                    })
            ) {
                Text(
                    "Enter Outside",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color.White,
                    modifier = modifier.align(Alignment.Center)
                )
            }
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val outsideRobots = state.robots.filter { it.location == Location.OUTSIDE }

                for (r in outsideRobots) {
                    Image(
                        painter = painterResource(id = r.image),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TokyoMapPreview(modifier: Modifier = Modifier) {
    TokyoMap(
        modifier = modifier,
        viewModel = RobotKOTViewModel(LocalContext.current)
    )
}