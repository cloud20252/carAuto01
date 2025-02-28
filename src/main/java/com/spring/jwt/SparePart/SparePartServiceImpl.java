package com.spring.jwt.SparePart;

import com.spring.jwt.utils.BaseResponseDTO;
import com.spring.jwt.utils.ImageCompressionUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SparePartServiceImpl implements SparePartService {

    public final SparePartRepo sparePartRepo;

    public final SparePartMapper sparePartMapper;

    public static final Logger logger = LoggerFactory.getLogger(SparePartServiceImpl.class);

    @Override
    public BaseResponseDTO addPart(String partName, String description, String manufacturer, Long price, Long partNumber, List<MultipartFile> photos) {
        try {
            List<byte[]> compressedPhotos = photos.stream()
                    .map(file -> {
                        try {
                            return ImageCompressionUtil.compressImage(file.getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to compress image", e);
                        }
                    })
                    .toList();

            SparePart sparePart = SparePart.builder()
                    .partName(partName)
                    .description(description)
                    .manufacturer(manufacturer)
                    .price(price)
                    .partNumber(partNumber)
                    .photo(compressedPhotos)
                    .updateAt(LocalDate.now())
                    .build();

            sparePartRepo.save(sparePart);
            return new BaseResponseDTO("Success", "Part Added Successfully");

        } catch (RuntimeException e) {
            logger.error("Error processing images: ", e);
            return new BaseResponseDTO("Error", "Failed to process images");
        }
    }

    @Override
    public SparePartDto getSparePartById(Integer id) {
        Optional<SparePart> sparePartOptional = sparePartRepo.findById(id);

        return sparePartOptional.map(sparePart -> SparePartDto.builder()
                .sparePartId(sparePart.getSparePartId())
                .partName(sparePart.getPartName())
                .description(sparePart.getDescription())
                .manufacturer(sparePart.getManufacturer())
                .price(sparePart.getPrice())
                .partNumber(sparePart.getPartNumber())
                .updateAt(sparePart.getUpdateAt())
                .photo(sparePart.getPhoto().stream()
                        .map(photo -> Base64.getEncoder().encodeToString(photo))
                        .toList())
                .build()
        ).orElse(null);
    }

    @Override
    public List<SparePartDto> getAllSpareParts() {
        List<SparePart> spareParts = sparePartRepo.findAll();

        return spareParts.stream().map(sparePart -> SparePartDto.builder()
                .sparePartId(sparePart.getSparePartId())
                .partName(sparePart.getPartName())
                .description(sparePart.getDescription())
                .manufacturer(sparePart.getManufacturer())
                .price(sparePart.getPrice())
                .partNumber(sparePart.getPartNumber())
                .updateAt(sparePart.getUpdateAt())
                .photo(sparePart.getPhoto().stream()
                        .map(photo -> Base64.getEncoder().encodeToString(photo))
                        .toList())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public SparePartDto updatePart(Integer id, String partName, String description, String manufacturer, Long price, Long partNumber, List<MultipartFile> photos) {
        SparePart sparePart = sparePartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Spare part not found"));

        Optional.ofNullable(partName).ifPresent(sparePart::setPartName);
        Optional.ofNullable(description).ifPresent(sparePart::setDescription);
        Optional.ofNullable(manufacturer).ifPresent(sparePart::setManufacturer);
        Optional.ofNullable(price).ifPresent(sparePart::setPrice);
        Optional.ofNullable(partNumber).ifPresent(sparePart::setPartNumber);

        if (photos != null && !photos.isEmpty()) {
            try {
                List<byte[]> compressedPhotos = photos.stream()
                        .map(file -> {
                            try {
                                byte[] compressed = ImageCompressionUtil.compressImage(file.getBytes());
                                if (compressed == null || compressed.length == 0) {
                                    logger.error("Compressed image is empty for file: {}", file.getOriginalFilename());
                                    throw new RuntimeException("Compressed image is empty");
                                }
                                return compressed;
                            } catch (IOException e) {
                                logger.error("Failed to compress image: {}", file.getOriginalFilename(), e);
                                throw new RuntimeException("Failed to compress image", e);
                            }
                        })
                        .collect(Collectors.toList());

                logger.info("Number of compressed photos: {}", compressedPhotos.size());

                sparePart.setPhoto(new ArrayList<>(compressedPhotos));
            } catch (Exception e) {
                logger.error("Failed to upload images", e);
                throw new RuntimeException("Failed to upload images", e);
            }
        } else {
            logger.warn("No photos provided for update. Skipping photo update.");
        }

        SparePart updatedPart = sparePartRepo.save(sparePart);

        logger.info("Updated spare part: {}", updatedPart);

        return SparePartDto.builder()
                .sparePartId(updatedPart.getSparePartId())
                .partName(updatedPart.getPartName())
                .description(updatedPart.getDescription())
                .manufacturer(updatedPart.getManufacturer())
                .price(updatedPart.getPrice())
                .partNumber(updatedPart.getPartNumber())
                .updateAt(updatedPart.getUpdateAt())
                .photo(updatedPart.getPhoto().stream()
                        .map(photo -> Base64.getEncoder().encodeToString(photo))
                        .toList())
                .build();
    }


}

