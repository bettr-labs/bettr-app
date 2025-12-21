package org.example.bettr.domain.usecase

import org.example.bettr.data.repository.OnboardingRepository
import org.example.bettr.domain.model.DreamType
import org.example.bettr.domain.model.DreamTypeModel

class GetDreamTypesUseCase(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke(): Result<List<DreamTypeModel>> {
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

