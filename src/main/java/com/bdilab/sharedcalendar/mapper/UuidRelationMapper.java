package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.SubscribedRelation;
import com.bdilab.sharedcalendar.model.UuidRelation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UuidRelationMapper {
    int insertUuidRelation(UuidRelation uuidRelation);
    UuidRelation selectUuidRelationByUuid(String shareCode);
    int insertSubscribeRelation(SubscribedRelation subscribedRelation);
    SubscribedRelation selectSubscribedRelationByUserIdAndTypeId(@Param("userId")int userId,@Param("typeId")int typeId);
    int deleteByUserIdAndTypeId(@Param("userId")int userId,@Param("typeId")int typeId);
    int deleteByTypeId(@Param("typeId")int typeId);
    List<SubscribedRelation> selectSubscribedRelationByUserId(int userId);
}
