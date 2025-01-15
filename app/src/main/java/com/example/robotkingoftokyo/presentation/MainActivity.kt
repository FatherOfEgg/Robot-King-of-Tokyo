package com.example.robotkingoftokyo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.robotkingoftokyo.domain.util.currentRobot
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTEvent
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTGame
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTViewModel
import com.example.robotkingoftokyo.presentation.game_screen.components.PowerCardCard
import com.example.robotkingoftokyo.presentation.game_screen.components.PowerCardShop
import com.example.robotkingoftokyo.presentation.ui.theme.RobotKingOfTokyoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RobotKingOfTokyoTheme {
                val viewModel: RobotKOTViewModel by viewModels()

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SelectNumRobotsScreen
                ) {
                    composable<SelectNumRobotsScreen> {
                        SelectNumRobots(navController, viewModel)
                    }
                    composable<GameScreen> {
                        RobotKOTGame(viewModel = viewModel, navController = navController)
                    }
                    composable<PowerCardShopScreen> {
                        PowerCardShopScreen(viewModel = viewModel)
                    }
                    composable<ViewHandScreen> {
                        ViewHandScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Serializable
object SelectNumRobotsScreen

@Serializable
object GameScreen

@Serializable
object PowerCardShopScreen

@Serializable
object ViewHandScreen

@Composable
fun SelectNumRobots(
    navController: NavController,
    viewModel: RobotKOTViewModel
) {
    var numRobots by remember {
        mutableIntStateOf(2)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier,
                onClick = {
                    if (numRobots > 2) {
                        numRobots--
                    }
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrease number of robots"
                )
            }

            Text(
                text = "$numRobots",
                textAlign = TextAlign.Center,
            )

            IconButton(
                modifier = Modifier,
                onClick = {
                    if (numRobots < 6) {
                        numRobots++
                    }
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increases number of robots"
                )
            }
        }

        Button(
            onClick = {
                viewModel.onEvent(RobotKOTEvent.StartGame(numRobots))
                navController.navigate(GameScreen)
            }
        ) {
            Text("Start Game")
        }
    }
}

@Composable
fun PowerCardShopScreen(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel
) {
    val state = viewModel.state.value

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${currentRobot(state).energyCubes}",
            fontSize = 30.sp,
            modifier = modifier
                .padding(4.dp)
        )

        Button(onClick = {
            viewModel.onEvent(RobotKOTEvent.ResetShop(currentRobot(state).energyCubes))
        }) {
            Text("Reroll shop (costs 2 energy)")
        }

        PowerCardShop(modifier = Modifier, viewModel)
    }
}

@Composable
fun ViewHandScreen(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel
) {
    val state = viewModel.state.value

    val currentRobot = currentRobot(state)
    val hand = currentRobot.hand

    LazyColumn(
        modifier = modifier
    ) {
        items(hand.size) { i ->
            Column(
                modifier = modifier
                    .padding(4.dp)
                    .border(4.dp, Color.Black)
                    .fillParentMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PowerCardCard(
                    modifier = modifier,
                    powerCard = hand[i]
                )
                Button(
                    modifier = modifier,
                    onClick = { viewModel.onEvent(RobotKOTEvent.DiscardPowerCard(i)) }
                ) {
                    Text("Discard Power Card")
                }
            }
        }
    }
}