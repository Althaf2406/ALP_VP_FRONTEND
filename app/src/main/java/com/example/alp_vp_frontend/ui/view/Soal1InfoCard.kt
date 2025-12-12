package com.example.labweek7.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labweek7.R


@Composable
fun Soal1InfoCard(
    image: Int,
    title: String,
    value: String,
    modifier: Modifier = Modifier.width(110.dp)
) {
    Box(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .height(90.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = title,
                modifier = Modifier.size(20.dp),
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF101010)
@Composable
fun Soal1InfoCardPreview() {
    Soal1InfoCard(
        image = R.drawable.icon_humidity,
        title = "Humidity",
        value = "100%",
        modifier = Modifier
            .width(120.dp)
    )
}
