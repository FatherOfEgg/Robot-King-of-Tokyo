package com.example.robotkingoftokyo.presentation.game_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robotkingoftokyo.presentation.PowerCardShopScreen
import com.example.robotkingoftokyo.presentation.ViewHandScreen
import com.example.robotkingoftokyo.presentation.game_screen.components.DieRoller
import com.example.robotkingoftokyo.presentation.game_screen.components.PowerCardShop
import com.example.robotkingoftokyo.presentation.game_screen.components.RobotCard
import com.example.robotkingoftokyo.presentation.game_screen.components.TokyoMap

@Composable
fun RobotKOTGame(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel,
    navController: NavController
) {
    BackHandler {  }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TokyoMap(modifier, viewModel)

        // Die Roller
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DieRoller(modifier, viewModel)
        }

        Row(
            modifier = modifier
        ) {
            Button(
                modifier = modifier,
                onClick = { navController.navigate(PowerCardShopScreen) }
            ) {
                Text("Buy Power Cards")
            }
            Button(
                modifier = modifier,
                onClick = { navController.navigate(ViewHandScreen) }
            ) {
                Text("View Hand")
            }
            Button(
                modifier = modifier,
                onClick = { viewModel.onEvent(RobotKOTEvent.EndTurn) }
            ) {
                Text("End turn")
            }
        }

        RobotCard(modifier, viewModel)
    }
}

@Preview(showSystemUi = true)
@Composable
fun RobotKOTGamePreview(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    RobotKOTGame(
        modifier = modifier,
        viewModel = RobotKOTViewModel(LocalContext.current),
        navController
    )
}