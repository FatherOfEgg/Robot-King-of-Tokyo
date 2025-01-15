package com.example.robotkingoftokyo.presentation.game_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.robotkingoftokyo.domain.model.PowerCardType
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTEvent
import com.example.robotkingoftokyo.presentation.game_screen.RobotKOTViewModel

@Composable
fun PowerCardShop(
    modifier: Modifier = Modifier,
    viewModel: RobotKOTViewModel
) {
    val state = viewModel.state.value

    LazyColumn(
        modifier = modifier
    ) {
        items(state.pcShop.size) { i->
            PowerCardCard(
                modifier = modifier
                    .clickable(onClick = {
                        viewModel.onEvent(RobotKOTEvent.BuyPowerCard(i))
                    }),
                powerCard = state.pcShop[i]
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PowerCardShowPreview(modifier: Modifier = Modifier) {
    PowerCardShop(
        modifier = modifier,
        RobotKOTViewModel(LocalContext.current)
    )
}