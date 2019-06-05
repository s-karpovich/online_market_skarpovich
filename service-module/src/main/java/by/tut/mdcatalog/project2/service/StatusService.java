package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.StatusDTO;

import java.util.List;

public interface StatusService {

    List<StatusDTO> getStatuses();

    StatusDTO getStatusById(Long id);
}
