const ApiUrl = "http://39.96.188.220:8080/";
const eventUrl = ApiUrl + "event/createEvent"
const eventypeListUrl = ApiUrl + "eventtype/getEventTypeList"
const getFormIdList = ApiUrl + "public/getFormIdList"
const eventypeUrl = ApiUrl + "eventtype/createEventTpye"
const eventListUrl = ApiUrl + "event/getEventListByTime"
const evenDetailUrl = ApiUrl + "event/selectEvent"
const evenDeleteUrl = ApiUrl + "event/deleteEvent"
const signIn = ApiUrl + "public/signIn"
const updateUserInfo = ApiUrl + "public/updateUserInfo"
const getMessagesUrl = ApiUrl + "message/getMessages"
const deleteMessagesUrl = ApiUrl + "message/deleteMessages"
const setMessagesReadUrl = ApiUrl + "message/setMessagesRead"
const noticeList = ApiUrl + "eventNotice/getNoticeList"
const evenUpdateUrl = ApiUrl + "event/updateEvent"
const eventListByTypeUrl = ApiUrl + "event/getEventListByTypeAndTime"
const eventypeUpdateUrl = ApiUrl + "eventtype/updateEventTypeInfo"
const subEventTypeList = ApiUrl + "eventtype/getSubEventTypeList"
const allEventTypeList = ApiUrl + "eventtype/getAllEventTypeList"
const eventypeDeleteUrl = ApiUrl + "eventtype/deleteEventTypes"
const generateShareCode = ApiUrl + "uuid/generateShareCode"
const cancelSubscribeType = ApiUrl + "uuid/cancelSubscribe"
const typeDetailUrl = ApiUrl + "eventtype/getEventTypeInfo"
const subscribeTypeUrl = ApiUrl + "uuid/subscribeEventType"
const signUp = ApiUrl + "public/signUp"


module.exports = {
  eventUrl: eventUrl,
  eventypeListUrl: eventypeListUrl,
  getFormIdList: getFormIdList,
  eventypeUrl: eventypeUrl,
  eventListUrl: eventListUrl,
  evenDetailUrl: evenDetailUrl,
  evenDeleteUrl: evenDeleteUrl,
  signIn: signIn,
  updateUserInfo: updateUserInfo,
  getMessagesUrl: getMessagesUrl,
  deleteMessagesUrl: deleteMessagesUrl,
  setMessagesReadUrl: setMessagesReadUrl,
  noticeList: noticeList,
  evenUpdateUrl: evenUpdateUrl,
  eventListByTypeUrl: eventListByTypeUrl,
  eventypeUpdateUrl: eventypeUpdateUrl,
  subEventTypeList: subEventTypeList,
  allEventTypeList: allEventTypeList,
  eventypeDeleteUrl: eventypeDeleteUrl,
  generateShareCode: generateShareCode,
  cancelSubscribeType: cancelSubscribeType,
  typeDetailUrl: typeDetailUrl,
  subscribeTypeUrl: subscribeTypeUrl,
  signUp: signUp
}