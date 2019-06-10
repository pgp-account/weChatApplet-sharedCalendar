// pages/eventDetail/eventDetail.js
var globalApi = require("../../utils/apiUrl.js");
var app = getApp()
let evenDetailUrl = globalApi.evenDetailUrl;
let evenDeleteUrl = globalApi.evenDeleteUrl;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    eventId:'',
    eventName: '',
    eventContent: '',
    typeName: '',
    repeatTimes: '',
    hidden: true,
    ratearray: ['不重复', '每日', '每周', '每年'],
    eventFrequency: '',
    conditionarray: ['重复次数', '重复截止时间', '无限重复'],
    eventEndCondition: '',
    remindarray: ['不设置提醒', '提前十分钟', '提前半小时'],
    noticeChoice: '0',
    hiddenTime: true,
    hiddenDate: true,
    repeatEndTime: null,
    // typeNamearr: '',
    // idarr: '',
    // typeindex: 0,
    // typearray: '',
    // typeIdarray: '',
    startTime: '',
    endTime: '',
    time:''
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
    // console.log('option', options);
    // let sendedItem = options.item;
    // let eventId = sendedItem.id;
    var date = app.globalData.date;
    this.setData({
      time: date
    })
    let eventId = wx.getStorageSync('eventId');
    // console.log(eventId);
    this.setData({
      eventId: eventId,
    })
    let that = this;
    wx.request({
      url: evenDetailUrl,
      method: "POST",//默认GET
      data: {
        "eventId": eventId.toString(),
      },
      header: {
        "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
        //以表单形式提交
        "content-type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        if (res.statusCode === 200) {
          console.log(res.data.meta.message);
          console.log(res.data.data);
          var item = res.data.data;
          wx.setStorageSync('eventInfo', item);
          // var eventName = item.eventName;
          // var startTime = item.startTime;
          // var endTime = item.endTime;
          // var fkTypeId = item.fkTypeId;
          //   // typeName: item.eventTypeName,
          // var eventContent = item.eventContent;
          // var eventFrequency = item.eventFrequency;
          // var eventEndCondition = item.eventEndCondition;
          // var repeatTimes = item.repeatTimes;
          // var repeatEndTime = item.repeatEndTime;
          // var noticeChoice = item.noticeChoice;
          that.setData2data(item.eventName, item.startTime, item.endTime, item.eventContent, item.eventFrequency, item.eventEndCondition, item.repeatTimes, item.repeatEndTime, item.noticeChoice, item.eventTypeName);
          if (item.eventFrequency != 0) {
            that.setData2hidden();
            if (item.eventEndCondition == 0){
              that.setData2hidden0();
            } else if (item.eventEndCondition == 1){
              that.setData2hidden1();
            }else{
              that.setData2hidden2();
            }
          } else {
            that.setData2HD();
          }
          
        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    })

    // if (this.data.eventFrequency != 0) {
    //   that.setData2hidden();
    // } else {
    //   this.setData({
    //     hidden: true,
    //     eventEndCondition: '',
    //     date: null
    //   });
    // }

  },

  setData2data: function (eventName, startTime, endTime,  eventContent, eventFrequency, eventEndCondition, repeatTimes, repeatEndTime, noticeChoice, eventTypeName) {
    this.setData({
      eventName: eventName,
      startTime: startTime,
      endTime: endTime,
      typeName: eventTypeName,
      eventContent: eventContent,
      eventFrequency: eventFrequency,
      eventEndCondition: eventEndCondition,
      repeatTimes: repeatTimes,
      repeatEndTime: repeatEndTime,
      noticeChoice: noticeChoice,
    })
  },
  setData2hidden: function () {
    this.setData({
      hidden: false,
    })
  },
  setData2hidden0:function(){
    this.setData({
      hiddenTime: false,
    })
  },
  setData2hidden1:function(){
    this.setData({
      hiddenDate: false,
    })
  },
  setData2hidden2:function(){
    this.setData({
      hiddenTime: true,
      hiddenDate: true,
    })
  },
  setData2HD:function(){
    this.setData({
      hidden: true,
      eventEndCondition: '',
      date: null,
      hiddenDate:true,
      hiddenTime:true
    });
  },
  //修改日程
  editEvent: function (e) {
    // var nowidx = e.currentTarget.dataset.idx;
    // var items = this.data.items;
    // var eventInfo = items[nowidx];
    // wx.setStorageSync('eventInfo', eventInfo);
    // console.log(wx.getStorageSync('eventInfo'));

    wx.navigateTo({
      url: "../scheduleInfo/scheduleInfo"
    })
  },

  //删除日程
  deleteEvent: function (e) {
    var that = this;
    //var newEventType = '';
    // var nowidx = e.currentTarget.dataset.idx;
    // var olditems = this.data.items;
    //var id = this.data.eventIds;
    wx.request({
      url: evenDeleteUrl,
      method: "POST",//默认GET
      data: {
        "eventIds": this.data.eventId.toString(),
      },
      header: {
        "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if (res.statusCode === 200) {
          console.log(res.data.meta.message);
          // olditems.splice(nowidx, 1);
          // that.setData2items(olditems);
          // console.log(olditems);
          // wx.navigateTo({
          //   url: "../createSchedule/createSchedule"
          // })
          wx.navigateBack({
            delta: 1
          })
        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.onLoad();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})