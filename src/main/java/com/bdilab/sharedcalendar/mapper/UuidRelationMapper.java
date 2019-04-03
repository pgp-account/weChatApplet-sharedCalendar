package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.UuidRelation;
import org.apache.ibatis.annotations.Param;

public interface UuidRelationMapper {
    int insertUuidRelation(@Param("uuidRelation")UuidRelation uuidRelation);
}
