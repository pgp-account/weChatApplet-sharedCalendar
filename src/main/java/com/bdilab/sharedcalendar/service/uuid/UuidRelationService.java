package com.bdilab.sharedcalendar.service.uuid;

import com.bdilab.sharedcalendar.model.UuidRelation;

public interface UuidRelationService {
    public UuidRelation generateShareCode(int typeId);
}
