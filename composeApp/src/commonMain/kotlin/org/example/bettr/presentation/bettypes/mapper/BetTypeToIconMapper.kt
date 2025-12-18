package org.example.bettr.presentation.bettypes.mapper

import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.bet_casino_img
import bettr.composeapp.generated.resources.bet_lottery_img
import bettr.composeapp.generated.resources.bet_poker_img
import bettr.composeapp.generated.resources.bet_sport_img
import bettr.composeapp.generated.resources.bet_tiger_img
import bettr.composeapp.generated.resources.die_img
import org.example.bettr.domain.model.BetType
import org.jetbrains.compose.resources.DrawableResource

internal fun BetType.toIcon(): DrawableResource = when (this) {
    BetType.SPORT -> Res.drawable.bet_sport_img
    BetType.TIGER -> Res.drawable.bet_tiger_img
    BetType.CASINO -> Res.drawable.bet_casino_img
    BetType.POKER -> Res.drawable.bet_poker_img
    BetType.LOTTERY -> Res.drawable.bet_lottery_img
    BetType.OTHER -> Res.drawable.die_img
}