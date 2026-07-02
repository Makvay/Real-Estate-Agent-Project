package com.pm.contentloaderadapter.service;

import com.pm.contentloaderadapter.domain.dto.BuildingRawData;
import com.pm.contentloaderadapter.domain.entity.UploadJob;
import com.pm.contentloaderadapter.repository.UploadJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessorService {

    private final UploadJobRepository uploadJobRepository;
    private final XlsxParserService xlsxParserService;
    private final KafkaProducerService kafkaProducerService;

    public UploadJob processFile(MultipartFile file, String source) {
        UploadJob job = UploadJob.builder()
                .filename(file.getOriginalFilename())
                .status(UploadJob.UploadStatus.PENDING)
                .build();
        job = uploadJobRepository.save(job);

        try {
            job.setStatus(UploadJob.UploadStatus.PROCESSING);
            uploadJobRepository.save(job);

            List<BuildingRawData> records = xlsxParserService.parse(file, source);
            job.setTotalRows(records.size());

            int processed = kafkaProducerService.sendBatch(records);
            job.setProcessedRows(processed);

            job.setStatus(UploadJob.UploadStatus.COMPLETED);
        } catch (Exception e) {
            job.setStatus(UploadJob.UploadStatus.FAILED);
        }

        return uploadJobRepository.save(job);
    }

    public UploadJob getJob(UUID id) {
        return uploadJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));
    }
}