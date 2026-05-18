package org.solen.business.checkin.fypstrategy;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.UserNotFoundByIdException;
import org.solen.business.repos.IUserRepository;
import org.solen.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetForYouCheckInsUseCaseImpl implements IGetForYouCheckInsUseCase {

    private IUserRepository userRepository;
    private RecommendationService recommendationService;

    @Override
    public List<CheckIn> getForYouCheckIns(Long userId) {
        if (userRepository.findById(userId) == null) {
            throw new UserNotFoundByIdException(userId);
        }
        return recommendationService.findPublicCheckIns(userId);
    }
}
