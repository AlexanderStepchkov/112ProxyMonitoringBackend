package ru.vtb.monitoring.vtb112.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.vtb.monitoring.vtb112.db.models.Changes;
import ru.vtb.monitoring.vtb112.db.models.dto.GroupedChanges;
import ru.vtb.monitoring.vtb112.dto.api.viewmodels.response.*;
import ru.vtb.monitoring.vtb112.dto.api.viewmodels.submodels.VmWorker;
import ru.vtb.monitoring.vtb112.dto.services.viewmodels.response.mainmodels.VmSmChange;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ChangesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "changeId",            source = "source.header.id")
    @Mapping(target = "currentPhase",        source = "source.header.currentPhase")
    @Mapping(target = "status",              source = "source.header.status")
    @Mapping(target = "category",            source = "source.header.category")
    @Mapping(target = "subcategory",         source = "source.header.subcategory")
    @Mapping(target = "briefDescription",    source = "source.header.briefDescription")
    @Mapping(target = "requestedBy",         source = "source.header.requestedBy")
    @Mapping(target = "requestedFor",        source = "source.header.requestedFor")
    @Mapping(target = "assignDept",          source = "source.header.assignDept")
    @Mapping(target = "plannedStartAt",      source = "source.header.plannedStartAt")
    @Mapping(target = "plannedEndAt",        source = "source.header.plannedEndAt")
    @Mapping(target = "foreignId",           source = "source.header.foreignId")
    @Mapping(target = "affectedServices",    source = "source.middle.affectedServices")
    @Mapping(target = "assets",              source = "source.middle.assets")
    @Mapping(target = "schedOutageStartAt",  source = "source.middle.schedOutageStartAt")
    @Mapping(target = "schedOutageEndAt",    source = "source.middle.schedOutageEndAt")
    @Mapping(target = "downStartAt",         source = "source.middle.downStartAt")
    @Mapping(target = "downEndAt",           source = "source.middle.downEndAt")
    @Mapping(target = "justification",       source = "source.description.justification")
    @Mapping(target = "description",         source = "source.description.description")
    @Mapping(target = "acceptanceComments",  source = "source.description.acceptanceComments")
    @Mapping(target = "plan",                source = "source.description.plan")
    @Mapping(target = "vtbRiskDescription",  source = "source.description.vtbRiskDescription")
    @Mapping(target = "closingComments",     source = "source.close.closingComments")
    Changes mapToChangesResponse(VmSmChange source);

    @Mapping(target = "name",              source = "changeId")
    @Mapping(target = "impactDescription", source = "vtbRiskDescription")
    @Mapping(target = "degradationRate",   source = "initialImpact")
    @Mapping(target = "affectedSystems",   source = "affectedServices")
    @Mapping(target = "startDate",         source = "plannedStartAt")
    @Mapping(target = "finishDate",        source = "plannedEndAt")
    @Mapping(target = "startDownDate",     source = "downStartAt")
    @Mapping(target = "finishDownDate",    source = "downEndAt")
    VmPlanInfoResponse mapToInfoResponse(Changes source);

    @Mapping(target = "name",              source = "description")
    VmPlanDescriptionResponse mapToDescriptionResponse(Changes source);

    @Mapping(target = "manager.name",      source = "requestedBy")
    @Mapping(target = "workers",           source = "requestedFor")
    VmPlanWorkersResponse mapToVmPlanWorkersResponse(Changes source);

    @Mapping(target = "name",              source = "source.category.category")
    @Mapping(target = "id",                source = "source.category.id")
    VmPlanSectionsResponse mapToVmPlanSections(GroupedChanges source);

    @Mapping(target = "name",              source = "changeId")
    @Mapping(target = "startDate",         source = "plannedStartAt")
    VmPlanResponse mapToVmPlan(Changes source);

    void updateChange(Changes changes, @MappingTarget Changes updated);

    default List<VmWorker> mapToWorkers(String requestedFor) {
        if (requestedFor == null) {
            return null;
        }
        VmWorker worker = new VmWorker();
        worker.setName(requestedFor);
        return Collections.singletonList(worker);
    }

    default String arrayToPlainString(String[] value) {
        return value != null
                ? String.join(",", Arrays.asList(value))
                : null;
    }

    default List<String> stringToArray(String value) {
        return value != null
                ? Arrays.asList(value.split(","))
                : null;
    }

}
