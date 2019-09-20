package com.kpi.events.services;

import com.kpi.events.model.Image;
import com.kpi.events.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageService implements IService<Image> {

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private ImageRepository repository;

    @Transactional
    public Image uploadImage(MultipartFile file) {
        String link = this.amazonClient.uploadFile(file);
        Image image = new Image();
        image.setLink(link);
        return save(image);
    }

    @Override
    @Transactional
    public List<Image> findAll(int size, int page) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    @Transactional
    public Image save(Image entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public Image find(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Image update(long id, Image image) {
        Image entity = repository.findById(id)
                .orElseThrow(RuntimeException::new);
        entity.setLink(image.getLink());
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }
}
