package com.example.todolist.service;

import com.example.todolist.model.Image;
import com.example.todolist.model.User;
import com.example.todolist.repository.ImageRepository;
import com.example.todolist.util.ImageUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void uploadImage(MultipartFile file, User user) throws IOException {
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .user(user)
                .imageData(ImageUtility.compressImage(file.getBytes())).build());

    }

    public Image getImageInfo(Long id) {
        final Optional<Image> dbImage = imageRepository.findById(id);

        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtility.decompressImage(dbImage.get().getImageData())).build();
    }


    public byte[] getImage(Long id) {
        final Optional<Image> dbImage = imageRepository.findById(id);
        return ImageUtility.decompressImage(dbImage.get().getImageData());
    }

}
