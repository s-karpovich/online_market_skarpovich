package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Status;
import by.tut.mdcatalog.project2.service.converter.StatusConverter;
import by.tut.mdcatalog.project2.service.model.StatusDTO;
import org.springframework.stereotype.Component;

@Component
public class StatusConverterImpl implements StatusConverter {

    @Override
    public StatusDTO toDTO(Status status) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());
        return statusDTO;
    }

    @Override
    public Status fromDTO(StatusDTO statusDTO) {
        Status status = new Status();
        status.setId(statusDTO.getId());
        status.setName(statusDTO.getName());
        return status;
    }
}
