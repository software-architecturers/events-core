package com.kpi.miss.service;

import com.kpi.miss.model.MissKpiEntity;
import com.kpi.miss.model.repository.MissKpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class MissKpiService implements IService<MissKpiEntity> {

    @Autowired
    private MissKpiRepository missKpiRepository;

    private Supplier<RuntimeException> runtimeExceptionSupplier =
            () -> new RuntimeException("That participant isn't present");


    public List<MissKpiEntity> findAll(int size, int page) {
        return missKpiRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public MissKpiEntity save(MissKpiEntity entity) {
        return missKpiRepository.save(entity);
    }

    @Override
    public MissKpiEntity find(long id) {

        return missKpiRepository.findById(id).orElseThrow(runtimeExceptionSupplier);
    }

    @Override
    public void delete(long id) {
        missKpiRepository.deleteById(id);
    }

    public MissKpiEntity unlikeParticipant(long id) {
        MissKpiEntity missKpiEntity = missKpiRepository.findById(id).orElseThrow(runtimeExceptionSupplier);
        missKpiEntity.setLikes(missKpiEntity.getLikes() + 1);
        return missKpiRepository.save(missKpiEntity);
    }

    public MissKpiEntity likeParticipant(long id) {
        MissKpiEntity missKpiEntity = missKpiRepository.findById(id).orElseThrow(runtimeExceptionSupplier);
        missKpiEntity.setLikes(missKpiEntity.getLikes() + 1);
        return missKpiRepository.save(missKpiEntity);
    }

    @Override
    public MissKpiEntity update(long id, MissKpiEntity entity) {
        return null;
    }
}
