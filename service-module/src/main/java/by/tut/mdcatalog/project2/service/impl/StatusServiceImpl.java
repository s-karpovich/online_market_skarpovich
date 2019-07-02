package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.StatusRepository;
import by.tut.mdcatalog.project2.repository.model.Status;
import by.tut.mdcatalog.project2.service.StatusService;
import by.tut.mdcatalog.project2.service.converter.StatusConverter;
import by.tut.mdcatalog.project2.service.model.StatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements StatusService {
    private final static Logger logger = LoggerFactory.getLogger(StatusServiceImpl.class);
    private final StatusRepository statusRepository;
    private final StatusConverter statusConverter;

    public StatusServiceImpl(StatusRepository statusRepository, StatusConverter statusConverter) {
        this.statusRepository = statusRepository;
        this.statusConverter = statusConverter;
    }

    @Override
    @Transactional
    public List<StatusDTO> getStatuses() {
        List<Status> statusNames = statusRepository.getAll();
        List<StatusDTO> statuses = statusNames.stream()
                .map(statusConverter::toDTO)
                .collect(Collectors.toList());
        logger.info("Status successfully requested from database:{}", statuses.size());
        return statuses;
    }

    @Override
    @Transactional
    public StatusDTO getStatusById(Long id) {
        return statusConverter.toDTO(statusRepository.getById(id));
    }

}

