package com.codework.shoppingcart.service.image;

import com.codework.shoppingcart.dto.ImageDto;
import com.codework.shoppingcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface iImageService
{
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
