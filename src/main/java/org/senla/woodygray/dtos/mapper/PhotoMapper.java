package org.senla.woodygray.dtos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.senla.woodygray.dtos.PhotoDto;
import org.senla.woodygray.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PhotoMapper {

    @Mapping(target = "fileName", source = "filePath", qualifiedByName = "extractFileName")
    @Mapping(target = "format", source = "filePath", qualifiedByName = "extractFormat")
    @Mapping(target = "size", source = "filePath", qualifiedByName = "getFileSize")
    @Mapping(target = "base64Image", source = "filePath", qualifiedByName = "encodeToBase64")
    public abstract PhotoDto toPhotoDto(Photo photo);

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
            //TODO:
            // Обработка ошибки чтения файла
            return null;
        }
    }
}