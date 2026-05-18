package org.solen.persistence.jparepos;

import org.solen.persistence.entities.CheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CheckInJpaRepository extends JpaRepository<CheckInEntity, Long> {
    @Query("""
        select ci
        from CheckInEntity ci
        join ci.habit h
        where h.creator.id = :userId
    """)
    List<CheckInEntity> findByHabitCreatorId(@Param("userId") Long userId);

    @Query("""
        select ci
        from CheckInEntity ci
        join ci.habit h
        where h.creator.id = :userId
          and ci.date between :from and :to
    """)
    List<CheckInEntity> findCheckInsForUser(
            @Param("userId") Long userId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query("select ci from CheckInEntity ci where ci.isPublic = true")
    List<CheckInEntity> findPublicCheckIns();

    @Query("""
        select ci
        from CheckInEntity ci
        join fetch ci.habit h
        join fetch h.creator u
        where ci.id = :id
    """)
    CheckInEntity findByIdWithHabitAndCreator(@Param("id") Long id);

    @Query("""
        select case when count(ci) > 0 then true else false end
        from CheckInEntity ci
        join ci.habit h
        join h.creator u
        where ci.id = :checkInId and u.email = :email
    """)
    boolean findByCheckInIdAndEmail(@Param("checkInId") Long checkInId, @Param("email") String email);
}
