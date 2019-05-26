// pages/video/video.js
var globalApi = require("../../utils/apiUrl.js");
var util = require("../../utils/time-utils.js");
var message = require("../../utils/message-utils.js");
const app = getApp();
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
    items:'',
    listData0: '',
    listData1: '',
    listData2: '',
    listData3: '',
    listData4: '',
    listData5: '',
    listData6: '',
    length:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var year = new Date().getFullYear();
    var month = new Date().getMonth() + 1;
    var day = new Date().getDate();
    var date = year + "-" + month + "-" + day;
    this.setData({
      date: date
    })
    this.setData({
      timeBean: util.getWeekDayList(this.data.selectWeek)
    })
    //console.log(this.data.timeBean);
    var timeBean = this.data.timeBean;
    var newyearMonth = timeBean.yearMonth;
    var weekDayArr = new Array();
    console.log(timeBean);
    // console.log(timeBean.weekDayList[timeBean.selectDay]);
    // console.log(timeBean.weekDayList[timeBean.selectDay].day );
    // console.log(parseInt(timeBean.yearMonth.slice(5, 6))+1);
    if (timeBean.yearMonth.slice(5) < 10) {
      newyearMonth = timeBean.yearMonth.slice(0, 5) + "0" + timeBean.yearMonth.slice(5);
    }
    let length = timeBean.weekDayList.length;
    for(var i = 0;i<length;i++){
      var newMonth = parseInt(timeBean.yearMonth.slice(5, 7))
      if (timeBean.weekDayList[i].day > timeBean.weekDayList[length-1].day) {
        newMonth = parseInt(timeBean.yearMonth.slice(5, 7)) - 1;
        //var newMonth = parseInt(timeBean.yearMonth.slice(5, 7))
      }
      if (timeBean.yearMonth.slice(5) < 10) {
        newyearMonth = timeBean.yearMonth.slice(0, 5) + "0" + newMonth.toString();
      } else {
        newyearMonth = timeBean.yearMonth.slice(0, 5) + newMonth.toString();
      }
      if (timeBean.weekDayList[i].day < 10) {
        var weekDay = newyearMonth + "-0" + timeBean.weekDayList[i].day;
      }else{
        var weekDay = newyearMonth + "-" + timeBean.weekDayList[i].day;
      }
      
      weekDayArr.push(weekDay);
    }
    console.log(weekDayArr);
    //var listData = ['','','','','','',''];
    var listData0 = new Array();
    var listData1 = new Array();
    var listData2 = new Array();
    var listData3 = new Array();
    var listData4 = new Array();
    var listData5 = new Array();
    var listData6 = new Array();
    //for (var j = 0; j < weekDayArr.length;j++){
    var startTime = weekDayArr[0] + " 00:00:00";
    var endTime = weekDayArr[weekDayArr.length-1] + " 23:59:59";
      //console.log(startTime);
      //console.log(endTime);
      //console.log(weekDayArr[j]);
    wx.showLoading({
      title: '加载中',
    })

      var that = this;
      wx.request({
        url: eventListUrl,
        method: "POST",//默认GET
        data: {
          "startTime": new Date(startTime),
          "endTime": new Date(endTime)
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
            console.log(res.data.data.EventVOs);
            console.log(res.data.data);
            //此方法报错
            // this.setData({ 
            //   items = res.data.data.EventTypes
            // })
            //让异步方法变成同步
            that.setData2items(res.data.data.EventVOs);
            var items = res.data.data.EventVOs;
            var day = new Array();
            for(var i = 0;i<items.length;i++){
              day.push(items[i].startTime.slice(0, 10));
            }
            //var day = items.startTime.splice(0,10);
            //listData.push(res.data.data.EventVOs);
            console.log(weekDayArr);
            console.log(day);
             //for (var i = 0; i < weekDayArr.length;i++){
            for (var j = 0;j<day.length;j++){
              if (weekDayArr[0] == day[j]){
                listData0.push(items[j]);
              
              } else if (weekDayArr[1] == day[j]) {
                listData1.push(items[j]);
              } else if (weekDayArr[2] == day[j]) {
                listData2.push(items[j]);
              } else if (weekDayArr[3] == day[j]) {
                listData3.push(items[j]);
              } else if (weekDayArr[4] == day[j]) {
                listData4.push(items[j]);
              } else if (weekDayArr[5] == day[j]) {
                listData5.push(items[j]);
              } else if (weekDayArr[6] == day[j]) {
                listData6.push(items[j]);
              }
            }
            var length = Math.max(listData0.length, listData1.length, listData2.length, listData3.length, listData4.length, listData5.length, listData6.length)
            console.log(length);
            that.setData2length(length);
             //}
            that.setData2list(listData0,listData1,listData2,listData3,listData4,listData5,listData6);
            wx.hideLoading();
          } else {
            console.log(res.statusCode);
            wx.hideLoading();
          }
          // this.setData({
          //   reset: ''
          // })
          
        }, fail: function (res) {
          console.log("error");
          wx.hideLoading();
        }
      })
     
   
    console.log(this.data.items);
    message.getMessageNum();
  },
  setData2items: function (data) {
    this.setData({
      items: data,
    });
  },
  setData2length:function(data){
    this.setData({
      length:data
    })
  },
  setData2list:function(listData0, listData1,listData2, listData3, listData4, listData5, listData6){
    this.setData({
      listData0: listData0,
      listData1: listData1,
      listData2: listData2,
      listData3: listData3,
      listData4: listData4,
      listData5: listData5,
      listData6: listData6,
    });
  },

  eventDetail: function (e) {
    var nowidx = e.currentTarget.dataset.idx;
    var nowKey = e.currentTarget.dataset.key;
    console.log(nowidx);
    console.log(nowKey);
    let listDataArr = new Array();
    listDataArr.push(this.data.listData0);
    listDataArr.push(this.data.listData1);
    listDataArr.push(this.data.listData2);
    listDataArr.push(this.data.listData3);
    listDataArr.push(this.data.listData4);
    listDataArr.push(this.data.listData5);
    listDataArr.push(this.data.listData6);

    let listData = listDataArr[nowKey];
    let items = listData[nowidx]
    //var items = this.data.items[nowidx];
    //var eventInfo = items[nowidx];
    var eventId = items.id.toString();
    console.log(items);
   // console.log(eventId);
    wx.setStorageSync('eventInfo', items);
    wx.setStorageSync('eventId', eventId);
    //console.log(this.data.timeBean);
    let date = items.startTime.slice(0,10);
    console.log(date);
    app.globalData.date = date;
    wx.navigateTo({
      url: "../eventDetail/eventDetail"
    })
  },

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
    this.onLoad();
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
    this.onLoad();
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
    var newyearMonth = timeBean.yearMonth
     console.log(timeBean);
    // console.log(timeBean.weekDayList[timeBean.selectDay]);
    // console.log(timeBean.weekDayList[timeBean.selectDay].day );
    // console.log(parseInt(timeBean.yearMonth.slice(5, 6))+1);
    if (timeBean.weekDayList[timeBean.selectDay].day < timeBean.weekDayList[0].day){
      var newMonth = parseInt(timeBean.yearMonth.slice(5, 6))+1;
      newyearMonth = timeBean.yearMonth.slice(0, 5) + newMonth.toString();
    }
    var date = newyearMonth +"-"+ timeBean.weekDayList[timeBean.selectDay].day;
    console.log(date);
    this.setData({
      date:date,
    })
  },

  addSchedule: function () {
    app.globalData.date = this.data.date;
    //var date = this.data.date;
    wx.navigateTo({
      url: "../createSchedule/createSchedule" 
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },
  onShow:function(){
    this.onLoad();
  }

})