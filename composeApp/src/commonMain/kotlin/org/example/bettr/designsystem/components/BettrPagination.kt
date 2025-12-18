package org.example.bettr.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.example.bettr.designsystem.theme.BettrGray
import org.example.bettr.designsystem.theme.BettrGreen
import org.example.bettr.designsystem.theme.BettrTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BettrPagination(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (index + 1 == currentPage) BettrGreen else BettrGray.copy(alpha = 0.3f)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BettrPaginationPreview() {
    BettrTheme {
        BettrPagination(
            totalPages = 5,
            currentPage = 0
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrPaginationMiddlePreview() {
    BettrTheme {
        BettrPagination(
            totalPages = 5,
            currentPage = 2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrPaginationLastPreview() {
    BettrTheme {
        BettrPagination(
            totalPages = 5,
            currentPage = 4
        )
    }
}

