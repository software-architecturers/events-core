package com.kpi.events.utils;

import com.kpi.events.model.User;
import com.kpi.events.model.dto.EventDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

class Client {

    @Autowired
    private static ModelMapper modelMapper;
    public static void main(String[] args) {
//        User user = new User();
//        user.setFirstName("Slavick");
//        user.setId(60);
//        user.setLogin("Ololo");
//        user.setSecondName("Ololo1");
//
//        if (!ValidationManager.isValidate(user)) {
//            System.out.println(ValidationManager.getFirsErrorMessage(user));
//        } else System.out.println("okay");

        EventDto eventDto = modelMapper.map(event, EventDto.class);
        eventDto.setImagesLinks(service.findImageLinks(event.getId()));
        return eventDto;






    }
}
