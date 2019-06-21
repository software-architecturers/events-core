package com.kpi.miss;

import com.kpi.miss.repository.MissKpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissKpiService {

    @Autowired
    private MissKpiRepository missKpiRepository;

    public List<MissKpiEntity> findAll(int size, int page) {
        return missKpiRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public MissKpiEntity unlikeParticipant(long id) {
        MissKpiEntity missKpiEntity = missKpiRepository.findById(id).orElseThrow(
                () -> new RuntimeException("That participant isn't present"));
        missKpiEntity.setLikes(missKpiEntity.getLikes() + 1);
        return missKpiRepository.save(missKpiEntity);
    }

    public MissKpiEntity likeParticipant(long id) {
        MissKpiEntity missKpiEntity = missKpiRepository.findById(id).orElseThrow(
                () -> new RuntimeException("That participant isn't present"));
        missKpiEntity.setLikes(missKpiEntity.getLikes() + 1);
        return missKpiRepository.save(missKpiEntity);
    }
}
