package org.solen.business.habitcases;

import lombok.AllArgsConstructor;
import org.solen.business.repos.IHabitRepository;
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