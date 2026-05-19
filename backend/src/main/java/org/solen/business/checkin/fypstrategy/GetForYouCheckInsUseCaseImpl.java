package org.solen.business.checkin.fypstrategy;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.UserNotFoundByIdException;
import org.solen.business.repos.IUserRepository;
import org.solen.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

import java.util.List;

// Thin use case: validates the user exists, then delegates to RecommendationService
// which picks the right strategy (personalised category match vs. cold-start default).
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
