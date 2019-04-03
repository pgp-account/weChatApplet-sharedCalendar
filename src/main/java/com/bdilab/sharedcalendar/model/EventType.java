package com.bdilab.sharedcalendar.model;

import java.util.Date;

public class EventType {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_type.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_type.type_name
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private String typeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_type.type_transparency
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private int typeTransparency;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_type.fk_creator_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer fkCreatorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_type.create_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_type.subscriber_num
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer subscriberNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_type.type_descrption
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private String typeDescrption;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_type.id
     *
     * @return the value of event_type.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_type.id
     *
     * @param id the value for event_type.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_type.type_name
     *
     * @return the value of event_type.type_name
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_type.type_name
     *
     * @param typeName the value for event_type.type_name
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_type.type_transparency
     *
     * @return the value of event_type.type_transparency
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public int getTypeTransparency() {
        return typeTransparency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_type.type_transparency
     *
     * @param typeTransparency the value for event_type.type_transparency
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setTypeTransparency(int typeTransparency) {
        this.typeTransparency = typeTransparency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_type.fk_creator_id
     *
     * @return the value of event_type.fk_creator_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getFkCreatorId() {
        return fkCreatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_type.fk_creator_id
     *
     * @param fkCreatorId the value for event_type.fk_creator_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setFkCreatorId(Integer fkCreatorId) {
        this.fkCreatorId = fkCreatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_type.create_time
     *
     * @return the value of event_type.create_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_type.create_time
     *
     * @param createTime the value for event_type.create_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_type.subscriber_num
     *
     * @return the value of event_type.subscriber_num
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getSubscriberNum() {
        return subscriberNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_type.subscriber_num
     *
     * @param subscriberNum the value for event_type.subscriber_num
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setSubscriberNum(Integer subscriberNum) {
        this.subscriberNum = subscriberNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_type.type_descrption
     *
     * @return the value of event_type.type_descrption
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public String getTypeDescrption() {
        return typeDescrption;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_type.type_descrption
     *
     * @param typeDescrption the value for event_type.type_descrption
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setTypeDescrption(String typeDescrption) {
        this.typeDescrption = typeDescrption;
    }
}