// pages/scheduleList/scheduleList.js
var globalApi = require("../../utils/apiUrl.js");
const app = getApp();
let eventListUrl = globalApi.eventListUrl
let evenDeleteUrl = globalApi.evenDeleteUrl
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // eventName: "IG VS SKT",
    // eventContent: "MSI Openning Game, IG VS SKT",
    startTime: "2019-05-01 00:00:00",
    endTime: "2019-05-31 23:59:59",
    // eventFrequency: 0,
    // eventEndCondition: 0,
    // repeatEndTime: null,
    // repeatTimes: 1,
    // currentRepeatTimes: 1,
    // noticeTime: "2019-05-12 11:30:28",
    // creatorName: "wyr",
    // eventTypeName: "MSI IG比赛",
    hiddenCondition:true,
    hiddenNoticeTime:true,
    array: ['不重复', '每日', '每周', '每年'],
    endarray: ['0-重复次数', '1-重复截止时间', '2-无限重复'],
    items:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    console.log(this.data.startTime);
    //that.setData2time(myDate);
    wx.request({
      url: eventListUrl,
      method: "POST",//默认GET
      data: {
        "startTime":new Date(this.data.startTime),
        "endTime": new Date(this.data.endTime)        
      },
      header: {
        "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
        //以表单形式提交
        "content-type": "application/x-www-form-urlencoded"

        //以json形式提交
        //"content-type": "application/json"
      },
      success: function (res) {
        if (res.statusCode === 200) {
          console.log(res.data.meta.message);
          console.log(res.data.data);
          //此方法报错
          // this.setData({ 
          //   items = res.data.data.EventTypes
          // })
          //让异步方法变成同步
          that.setData2items(res.data.data.EventVOs);
          var typeNamearr = new Array();
          var idarr = new Array();
          for (var i = 0; i < res.data.data.EventVOs.length; i++) {
            typeNamearr.push(res.data.data.EventVOs[i].typeName);
            idarr.push(res.data.data.EventVOs[i].id);
          }
          wx.setStorageSync('typeNamearr', typeNamearr);
          wx.setStorageSync('idarr', idarr);
          console.log(typeNamearr);
          console.log(idarr);
          if (res.data.data.EventVOs.eventFrequency != 0){
            that.setData2hiddCondition();            
          }
          if (res.data.data.EventVOs.noticeTime != ''){
            that.setData2hiddNoticeTime();
            
          }
        } else {
          console.log(res.statusCode)
        }
        // this.setData({
        //   reset: ''
        // })
      }, fail: function (res) {
        console.log("error");
      }
    })
  },
  
  setData2items: function (data) {
    this.setData({
      items: data,
    });
  },
  setData2hiddCondition: function (data) {
    this.setData({
      hiddenCondition: false,
    })
  },
  setData2hiddNoticeTime: function(data) {
    this.setData({
      hiddenNoticeTime: false,
    })
  },

  //修改日程
  editEvent: function (e) {
    var nowidx = e.currentTarget.dataset.idx;
    var items = this.data.items;
    var eventInfo = items[nowidx];
    wx.setStorageSync('eventInfo', eventInfo);
    console.log(wx.getStorageSync('eventInfo'));
    
    wx.navigateTo({
      url: "../scheduleInfo/scheduleInfo"
    })
  },

  //删除日程
  deleteEvent: function (e) {
    var that = this;
    //var newEventType = '';
    var nowidx = e.currentTarget.dataset.idx;
    var olditems = this.data.items;
    var id = olditems[nowidx].id.toString();
    wx.request({
      url: evenDeleteUrl,
      method: "POST",//默认GET
      data: {
        "eventIds": id,
      },
      header: {
        "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if (res.statusCode === 200) {
          console.log(res.data.meta.message);
          olditems.splice(nowidx, 1);
          that.setData2items(olditems);
          console.log(olditems);
          // wx.navigateTo({
          //   url: "../createSchedule/createSchedule"
          // })
        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    })
  },
  // setData2time:function(data){
  //   this.setData({
  //     startTime: myDate,
  //     endTime: myDate,
  //   })
  // },
  

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

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