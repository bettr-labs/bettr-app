package org.example.bettr.presentation.dreamselection.mapper

import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.car_icon
import bettr.composeapp.generated.resources.fitness_icon
import bettr.composeapp.generated.resources.heart_icon
import bettr.composeapp.generated.resources.house_icon
import bettr.composeapp.generated.resources.pig_icon
import bettr.composeapp.generated.resources.study_icon
import bettr.composeapp.generated.resources.travel_icon
import bettr.composeapp.generated.resources.vacation_icon
import org.example.bettr.domain.model.DreamType
import org.jetbrains.compose.resources.DrawableResource

internal fun DreamType.toIcon(): DrawableResource = when (this) {
    DreamType.HOME -> Res.drawable.house_icon
    DreamType.TRAVEL -> Res.drawable.travel_icon
    DreamType.MONEY -> Res.drawable.pig_icon
    DreamType.CAR -> Res.drawable.car_icon
    DreamType.STUDY -> Res.drawable.study_icon
    DreamType.WEDDING -> Res.drawable.heart_icon
    DreamType.VACATION -> Res.drawable.vacation_icon
    DreamType.HEALTH -> Res.drawable.fitness_icon
    DreamType.OTHER -> Res.drawable.pig_icon
}