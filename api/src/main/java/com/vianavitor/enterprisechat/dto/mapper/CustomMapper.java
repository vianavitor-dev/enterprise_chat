package com.vianavitor.enterprisechat.dto.mapper;

public interface CustomMapper <Entity> {
    // map the createDTO to corresponding entity
    public Entity createDTOtoEntity(Record cDto);

    // map the updateDTO to corresponding entity
    public Entity updateDTOtoEntity(Record uDto);

    // map an entity to the corresponding respDTO
    public Record entityToRespDTO (Entity entity);
}
