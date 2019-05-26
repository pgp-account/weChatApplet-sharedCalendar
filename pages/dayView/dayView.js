var util = require("../../utils/time-utils.js")
var globalApi = require("../../utils/apiUrl.js");
var message = require("../../utils/message-utils.js");

var app = getApp()

let eventListUrl = globalApi.eventListUrl

Page({

  /**
   * 页面的初始数据
   * selectWeek 0代表的本周  1代表下一周  -1代表上一周   
   * timeBean 传递给组件的数据，数据的格式在一开始的工具类中明确
   */
  data: {
    selectWeek: 0,
    timeBean: {},
    date:'',
    startTime:'',
    endTime:'',
    items:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    if (this.data.date == '') {

      this.setData({
        year: new Date().getFullYear(),
        month: new Date().getMonth() + 1,
        day: new Date().getDate()
      })
      var date = this.data.year + "-" + this.data.month + "-" + this.data.day;
      this.setData({
        date: date
      })
    }
    console.log(date);
    
    //var that = this;
    this.getSchedule(this.data.date).then(item => this.setData({ items: item })).catch(console.log);
    console.log(this.data.items);
    message.getMessageNum();
  },
  // setData2items: function (data) {
  //   this.setData({
  //     items: data,
  //   });
  // },

  /**
   * 点击了上一周，选择周数字减一，然后直接调用工具类中一个方法获取到数据
   */
  lastWeek: function (e) {
    var selectWeek = --this.data.selectWeek;
    var timeBean = this.data.timeBean
    timeBean = util.getWeekDayList(selectWeek)

    if (selectWeek != 0) {
      timeBean.selectDay = 0;
    }

    this.setData({
      timeBean,
      selectWeek
    })
    var newyearMonth = timeBean.yearMonth;
    if (timeBean.weekDayList[timeBean.selectDay].day < timeBean.weekDayList[0].day) {
      var newMonth = parseInt(timeBean.yearMonth.slice(5, 6)) + 1;
      newyearMonth = timeBean.yearMonth.slice(0, 5) + newMonth.toString();
    }
    var date = newyearMonth + "-" + timeBean.weekDayList[timeBean.selectDay].day;
    console.log(date);
    this.setData({
      date: date,
    })
    this.getSchedule(this.data.date).then(item => this.setData({ items: item })).catch(console.log);
    console.log(this.data.items);
  },

  /**
   * 点击了下一周，选择周数字加一，然后直接调用工具类中一个方法获取到数据
   */
  nextWeek: function (e) {
   
    var selectWeek = ++this.data.selectWeek;
    var timeBean = this.data.timeBean
    timeBean = util.getWeekDayList(selectWeek)

    if (selectWeek != 0) {
      timeBean.selectDay = 0;
    }

    this.setData({
      timeBean,
      selectWeek
    })
    console.log(this.data.timeBean);
    var newyearMonth = timeBean.yearMonth;
    if (timeBean.weekDayList[timeBean.selectDay].day < timeBean.weekDayList[0].day) {
      var newMonth = parseInt(timeBean.yearMonth.slice(5, 6)) + 1;
      newyearMonth = timeBean.yearMonth.slice(0, 5) + newMonth.toString();
    }
    var date = newyearMonth + "-" + timeBean.weekDayList[timeBean.selectDay].day;
    console.log(date);
    this.setData({
      date: date,
    })
    this.getSchedule(this.data.date).then(item => this.setData({ items: item })).catch(console.log);
    console.log(this.data.items);
  },

  /**
   * 选中了某一日，改变selectDay为选中日
   */
  dayClick: function (e) {
    var timeBean = this.data.timeBean
    timeBean.selectDay = e.detail;
    this.setData({
      timeBean,
    })
    var newyearMonth = timeBean.yearMonth;
    if (timeBean.weekDayList[timeBean.selectDay].day < timeBean.weekDayList[0].day) {
      var newMonth = parseInt(timeBean.yearMonth.slice(5, 6)) + 1;
      newyearMonth = timeBean.yearMonth.slice(0, 5) + newMonth.toString();
    }
    var date = newyearMonth + "-" + timeBean.weekDayList[timeBean.selectDay].day;
    console.log(date);
    this.setData({
      date: date,
    })
    this.getSchedule(this.data.date).then(item => this.setData({ items: item })).catch(console.log);
    console.log(this.data.items);
  },

  addSchedule: function () {
    app.globalData.date = this.data.date;
    wx.navigateTo({
      url: "../createSchedule/createSchedule" 
    })
  },

  eventDetail: function (e) {
    var nowidx = e.currentTarget.dataset.idx;
    var items = this.data.items[nowidx];
    //var eventInfo = items[nowidx];
    var eventId = items.id.toString();
    console.log(items);
    console.log(eventId);
    //wx.setStorageSync('eventInfo', items);
    app.globalData.date = this.data.date;
    wx.setStorageSync('eventId', eventId);
    //wx.setStorageSync('eventDetail', items);
    wx.navigateTo({
      url: "../eventDetail/eventDetail"
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.setData({
      timeBean: util.getWeekDayList(this.data.selectWeek)
    })
    console.log(this.data.timeBean);
  },
  onShow: function () {
    this.onLoad();
  },

  //获取事件   使用Promise()解决request请求异步的问题,传值问题
  getSchedule: function (date) {
    var date = date;
    var startTime = date + " 00:00:00";
    var endTime = date + " 23:59:59";
    var result;
    wx.showLoading({
      title: '加载中',
    })
    return new Promise(function (resolve, reject) {
      wx.request({
        url: eventListUrl,
        method: "POST",//默认GET
        data: {
          "startTime": new Date(startTime),
          "endTime": new Date(endTime)
        },
        // async: false,
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
            //that.setData2items(res.data.data.EventVOs);
            resolve(res.data.data.EventVOs);
            wx.hideLoading();
          } else {
            console.log(res.statusCode);
            wx.hideLoading();
          }
        }, fail: function (res) {
          console.log("error");
          reject('error');
          wx.hideLoading();
        }
      })
    })
  },


})