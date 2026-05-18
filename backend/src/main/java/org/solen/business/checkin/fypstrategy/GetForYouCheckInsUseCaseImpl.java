package org.example.habit_tracker.business.checkin.fypstrategy;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
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
