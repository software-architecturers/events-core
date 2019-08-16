package com.kpi.events.services;

import com.kpi.events.model.Image;
import com.kpi.events.model.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageService implements IService<Image> {

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private ImageRepository repository;

    public Image uploadImage(MultipartFile file) {
        String link = this.amazonClient.uploadFile(file);
        Image image = new Image();
        image.setLink(link);
        return save(image);
    }

    @Override
    public List<Image> findAll(int size, int page) {
        return null;
    }

    @Override
    public Image save(Image entity) {
        return repository.save(entity);
    }

    @Override
    public Image find(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Image update(long id, Image entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
