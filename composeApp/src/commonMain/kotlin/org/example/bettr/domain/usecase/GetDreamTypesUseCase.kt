package org.example.bettr.domain.usecase

import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result
import org.example.bettr.data.network.util.map
import org.example.bettr.data.repository.OnboardingRepository
import org.example.bettr.domain.model.DreamType
import org.example.bettr.domain.model.DreamTypeModel

class GetDreamTypesUseCase(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke(): Result<List<DreamTypeModel>, NetworkError> {
        return onboardingRepository.getDreamTypes().map { dtoList ->
            dtoList.map { dto ->
                DreamTypeModel(
                    type = DreamType.fromKey(dto.key),
                    label = dto.label
                )
            }
        }
    }
}

