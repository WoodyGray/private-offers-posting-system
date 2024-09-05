package org.senla.woodygray.dtos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.senla.woodygray.dtos.PhotoDto;
import org.senla.woodygray.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PhotoMapper {

    @Value("${photo.source}")
    private String photoSource;

    @Mapping(target = "fileName", source = "filePath", qualifiedByName = "extractFileName")
    @Mapping(target = "format", source = "filePath", qualifiedByName = "extractFormat")
    @Mapping(target = "size", source = "filePath", qualifiedByName = "getFileSize")
    @Mapping(target = "base64Image", source = "filePath", qualifiedByName = "encodeToBase64")
    public abstract PhotoDto toPhotoDto(Photo photo);

    @Mapping(target = "filePath", expression = """
            java(constructFilePath(
            photoDto.fileName(), 
            photoDto.format(),
            photoDto.size(),
            photoDto.base64Image()
            ))""")
    public abstract Photo toPhoto(PhotoDto photoDto);

    @Named("toPhotos")
    public abstract List<Photo> toPhotos(List<PhotoDto> photoDtos);

    // Метод для обновления списка Photo, добавляя новые
    public void updatePhotoList(List<PhotoDto> photoDtos, List<Photo> existingPhotos) {
        Set<String> existingPhotoPaths = existingPhotos.stream()
                .map(Photo::getFilePath)
                .collect(Collectors.toSet());

        List<Photo> newPhoto = toPhotos(photoDtos).stream()
                .filter(photo -> !existingPhotoPaths.contains(photo.getFilePath()))
                .toList();
        existingPhotos.addAll(newPhoto);
    }

    @Named("toPhotoDtos")
    public abstract List<PhotoDto> toPhotoDtos(List<Photo> photos);

    @Named("extractFileName")
    public String extractFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        return new File(filePath).getName();
    }

    @Named("extractFormat")
    public String extractFormat(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        String[] parts = filePath.split("\\.");
        return (parts.length > 1) ? parts[parts.length - 1] : null;
    }

    @Named("getFileSize")
    public Integer getFileSize(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        File file = new File(filePath);
        return (file.exists()) ? (int) file.length() : null;
    }

    @Named("encodeToBase64")
    public String encodeToBase64(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        try {
            File file = new File(filePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String constructFilePath(
            String fileName,
            String format,
            Integer size,
            String base64Image
            ){
        if (fileName == null || format == null || size == null || base64Image == null) {
            return null;
        }

        try {

            File file = new File(String.format("%s\\%s.%s", photoSource, fileName, format));
            byte[] fileContent = Base64.getDecoder().decode(base64Image);

            if (fileContent.length != size) {
                throw new IllegalArgumentException("The size of the decoded file does not match the expected size");
            }

            var fos = new FileOutputStream(file);
            fos.write(fileContent);
            fos.close();

            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}