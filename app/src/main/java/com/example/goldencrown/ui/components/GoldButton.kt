package com.example.goldencrown.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

enum class GoldButtonStyle {
    PRIMARY,    // Gold Primary
    SECONDARY,  // Navy Primary
    OUTLINED    // Gold / Navy Border
}

@Composable
fun GoldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: GoldButtonStyle = GoldButtonStyle.PRIMARY,
    enabled: Boolean = true,
    height: Dp = 52.dp
) {
    val shape = RoundedCornerShape(12.dp) // Standard 12dp Radius

    when (style) {
        GoldButtonStyle.PRIMARY -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(height),
                shape = shape,
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = NavyPrimary,
                    disabledContainerColor = GoldLight,
                    disabledContentColor = TextMuted
                )
            ) {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        GoldButtonStyle.SECONDARY -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(height),
                shape = shape,
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GcSurfaceHigh,
                    contentColor = GcCream,
                    disabledContainerColor = GcSurface,
                    disabledContentColor = GcMuted
                )
            ) {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        GoldButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(height),
                shape = shape,
                enabled = enabled,
                border = BorderStroke(1.dp, GoldPrimary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = GcCream
                )
            ) {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
