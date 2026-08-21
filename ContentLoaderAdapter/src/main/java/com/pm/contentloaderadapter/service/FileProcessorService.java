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
        if (source != null && source.contains(",")) {
            source = source.split(",")[0].trim();
        }

        UploadJob job = UploadJob.builder()
                .filename(file.getOriginalFilename())
                .status(UploadJob.UploadStatus.PENDING)
                .build();
        job = uploadJobRepository.save(job);

        try {
            job.setStatus(UploadJob.UploadStatus.PROCESSING);
            uploadJobRepository.save(job);

            String topic = resolveTopic(file.getOriginalFilename());
            List<BuildingRawData> records = xlsxParserService.parse(file, source);
            job.setTotalRows(records.size());

            int processed = kafkaProducerService.sendBatch(records, topic);
            job.setProcessedRows(processed);

            job.setStatus(UploadJob.UploadStatus.COMPLETED);
        } catch (Exception e) {
            job.setStatus(UploadJob.UploadStatus.FAILED);
        }

        return uploadJobRepository.save(job);
    }

    private String resolveTopic(String filename) {
        if (filename == null) return "building.raw.data";
        String lower = filename.toLowerCase();
        if (lower.contains("domclick")) return "building.raw.domclick";
        if (lower.contains("rosreestr")) return "building.raw.rosreestr";
        return "building.raw.data";
    }

    public UploadJob getJob(UUID id) {
        return uploadJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));
    }
}