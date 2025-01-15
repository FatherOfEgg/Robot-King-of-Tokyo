package com.example.robotkingoftokyo.presentation.game_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.robotkingoftokyo.domain.model.PowerCard
import com.example.robotkingoftokyo.domain.model.PowerCardType

@Composable
fun PowerCardCard(
    modifier: Modifier = Modifier,
    powerCard: PowerCard
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .size(400.dp, 250.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = powerCard.cost.toString(),
                    fontSize = 35.sp
                )
                Text(
                    text = powerCard.name,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            Box(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = modifier
                        .align(Alignment.Center),
                    text = powerCard.type.name,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = if (powerCard.type == PowerCardType.KEEP) {
                        Color.Cyan
                    } else {
                        Color.Red
                    }
                )
            }
            Text(
                text = powerCard.description,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}