package org.example.habit_tracker.business.habitcases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DeleteHabitUseCaseImpl implements IDeleteHabitUseCase {

    private IHabitRepository repository;

    @Override
    public void deleteHabit(Long id) {
        repository.deleteById(id);
    }
}