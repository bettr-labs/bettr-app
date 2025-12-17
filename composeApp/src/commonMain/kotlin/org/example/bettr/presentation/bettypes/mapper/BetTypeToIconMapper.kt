package org.example.bettr.presentation.bettypes.mapper

import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.bet_casino_img
import bettr.composeapp.generated.resources.bet_lottery_img
import bettr.composeapp.generated.resources.bet_poker_img
import bettr.composeapp.generated.resources.bet_sport_img
import bettr.composeapp.generated.resources.bet_tiger_img
import bettr.composeapp.generated.resources.bet_types_casino
import bettr.composeapp.generated.resources.bet_types_lottery
import bettr.composeapp.generated.resources.bet_types_other
import bettr.composeapp.generated.resources.bet_types_poker
import bettr.composeapp.generated.resources.bet_types_sports
import bettr.composeapp.generated.resources.bet_types_tiger
import bettr.composeapp.generated.resources.die_img
import org.example.bettr.domain.model.BetType
import org.example.bettr.presentation.bettypes.model.BetTypeItemUiModel
import org.jetbrains.compose.resources.DrawableResource

internal fun BetType.toIcon(): DrawableResource = when (this) {
    BetType.SPORT -> Res.drawable.bet_sport_img
    BetType.TIGER -> Res.drawable.bet_tiger_img
    BetType.CASINO -> Res.drawable.bet_casino_img
    BetType.POKER -> Res.drawable.bet_poker_img
    BetType.LOTTERY -> Res.drawable.bet_lottery_img
    BetType.OTHER -> Res.drawable.die_img
}

// TODO: Replace getMockBetTypes() with ViewModel state when backend is ready
internal fun getMockBetTypes(): List<BetTypeItemUiModel> = listOf(
    BetTypeItemUiModel(
        type = BetType.SPORT,
        label = Res.string.bet_types_sports,
        icon = BetType.SPORT.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.TIGER,
        label = Res.string.bet_types_tiger,
        icon = BetType.TIGER.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.CASINO,
        label = Res.string.bet_types_casino,
        icon = BetType.CASINO.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.POKER,
        label = Res.string.bet_types_poker,
        icon = BetType.POKER.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.LOTTERY,
        label = Res.string.bet_types_lottery,
        icon = BetType.LOTTERY.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.OTHER,
        label = Res.string.bet_types_other,
        icon = BetType.OTHER.toIcon()
    )
)