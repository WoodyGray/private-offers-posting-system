package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.Photo;
import org.senla.woodygray.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Transactional
    public void createNewPhoto(Photo photo) {

        photoRepository.save(photo);

    }
}
