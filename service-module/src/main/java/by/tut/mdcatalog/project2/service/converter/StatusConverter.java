package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Status;
import by.tut.mdcatalog.project2.service.model.StatusDTO;

public interface StatusConverter {

    StatusDTO toDTO(Status status);

    Status fromDTO(StatusDTO statusDTO);
}
