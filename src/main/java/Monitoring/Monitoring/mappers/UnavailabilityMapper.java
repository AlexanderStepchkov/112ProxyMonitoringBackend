package Monitoring.Monitoring.mappers;

import Monitoring.Monitoring.db.models.Unavailabilities;
import Monitoring.Monitoring.dto.services.viewmodels.response.mainmodels.VmSmUnavailability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UnavailabilityMapper {

    @Mapping(target = "id", ignore = true)
    Unavailabilities mapToIncidentResponse(VmSmUnavailability source);

    @Mapping(target = "id", ignore = true)
    void updateUnavailability(Unavailabilities incident, @MappingTarget Unavailabilities updated);
}