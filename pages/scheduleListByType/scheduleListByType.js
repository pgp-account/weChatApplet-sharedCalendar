// pages/scheduleListByType/scheduleListByType.js
var globalApi = require("../../utils/apiUrl.js");
const app = getApp();

let eventListByTypeUrl = globalApi.eventListByTypeUrl
let evenDeleteUrl = globalApi.evenDeleteUrl

Page({

  /**
   * 页面的初始数据
   */
  data: {
    startTime: "",
    endTime: "",
    hiddenCondition: true,
    hiddenNoticeTime: true,
    array: ['不重复', '每日', '每周', '每年'],
    endarray: ['重复次数', '重复截止时间', '无限重复'],
    items: '',
    typeId:'', 
    typeName:'',
    date:'',
    num:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    console.log('option', options);
    let typeId = options.typeId;
    console.log('typeId', typeId);
    let typeName = options.typeName;
    console.log('typeName', typeName);
    let date = options.date;
    let startTime = date + "-01 00:00:00";
    let endTime = date + "-31 23:59:59"
    that.setData2IdtypeName(typeId, typeName);
    that.setData2Time(date, startTime, endTime);
    console.log(this.data.startTime);
    // wx.showToast({
    //   title: '加载中',
    //   icon: 'loading',
    //   duration: 10000
    // })
    //that.setData2time(myDate);
    that.getScheduleList().then(item => that.setData({ items: item })).catch(console.log);
    console.log(that.data.items);    
    console.log(that.data.items.Total);  
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
  setData2hiddNoticeTime: function (data) {
    this.setData({
      hiddenNoticeTime: false,
    })
  },
  setData2IdtypeName: function (typeId, typeName) {
    this.setData({
      typeId:typeId, 
      typeName:typeName
    })
    console.log(this.data.typeId);
    console.log(this.data.typeName);
  },
  setData2Time:function(date, startTime, endTime){
    this.setData({
      date: date,
      startTime: startTime,
      endTime: endTime
    })
  },
  setData2Num:function(num){
    this.setData({
      num:num
    })
  },

  //修改日程
  editEvent: function (e) {
    var nowidx = e.currentTarget.dataset.idx;
    var items = this.data.items;
    var eventInfo = items[nowidx];
    wx.setStorageSync('eventInfo', eventInfo);
    console.log(wx.getStorageSync('eventInfo'));
    let date = eventInfo.startTime.slice(0, 10);
    console.log(date);
    app.globalData.date = date;
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
    wx.showModal({
      title: '提示',
      content: '确认删除该日程(重复日程会一起删除)吗',
      success: function (res) {
        if (res.confirm) {
          
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
                // olditems.splice(nowidx, 1);
                // that.setData2items(olditems);
                // console.log(olditems);
                that.getScheduleList().then(item => that.setData({ items: item })).catch(console.log);
                wx.showToast({
                  title: '成功',
                  icon: 'success',
                  duration: 1000
                })
                // that.onLoad();
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
        } else {
          console.log('弹框后点取消')
        }
      }
    })
  },
  //下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    let month = parseInt(this.data.date.slice(5,7))-1;
    if(month < 10){
      var monthString = "0" + month.toString();
    }
    let date = this.data.date.slice(0, 5) + monthString;
    console.log(date);
    let startTime = date + "-01 00:00:00";
    let endTime = date + "-31 23:59:59";
    let that = this;
    //that.setData2IdtypeName(typeId, typeName);
    that.setData2Time(date, startTime, endTime);
    //模拟加载
    setTimeout(function () {
      that.getScheduleList().then(item => that.setData({ items: item })).catch(console.log);
      console.log(that.data.items);
      var items = that.data.items;
      var typeNamearr = new Array();
      var idarr = new Array();
      for (var i = 0; i < items.length; i++) {
        typeNamearr.push(items[i].typeName);
        idarr.push(items[i].id);
      }
      wx.setStorageSync('typeNamearr', typeNamearr);
      wx.setStorageSync('idarr', idarr);
      console.log(typeNamearr);
      console.log(idarr);
      if (items.eventFrequency != 0) {
        that.setData2hiddCondition();
      }
      if (items.noticeTime != '') {
        that.setData2hiddNoticeTime();

      }
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    // 显示加载图标
    
    // 页数+1
    // page = page + 1;
    let month = parseInt(this.data.date.slice(5, 7)) + 1;
    if (month < 10) {
      var monthString = "0" + month.toString();
    }
    let date = this.data.date.slice(0, 5) + monthString;
    console.log(date);
    let startTime = date + "-01 00:00:00";
    let endTime = date + "-31 23:59:59";
    that.setData2Time(date, startTime, endTime);
    
    wx.showLoading({
      title: '玩命加载中',
    })
    
    that.getScheduleList().then(item => that.setData({ items: item })).catch(console.log);
    console.log(that.data.items);
    
      //wx.hideNavigationBarLoading() //完成停止加载
      //wx.stopPullDownRefresh() //停止下拉刷新
      //隐藏加载框
    
      
    wx.pageScrollTo({
      scrollTop: 0
    })
    
     

  },

  getScheduleList: function () {
    let typeId = this.data.typeId;
    let startTime = this.data.startTime;
    let endTime = this.data.endTime;
    let that = this;
    wx.showLoading({
      title: '加载中',
    })
    return new Promise(function (resolve, reject) {
      wx.request({
        url: eventListByTypeUrl,
        method: "POST",//默认GET
        data: {
          "startTime": new Date(startTime),
          "endTime": new Date(endTime),
          "typeId": typeId
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
            that.setData2Num(res.data.data.Total);
            //console.log(that.data.items);
            resolve(res.data.data.EventVOs);
            var items = res.data.data.EventVOs;
            var typeNamearr = new Array();
            var idarr = new Array();
            for (var i = 0; i < items.length; i++) {
              typeNamearr.push(items[i].typeName);
              idarr.push(items[i].id);
            }
            wx.setStorageSync('typeNamearr', typeNamearr);
            wx.setStorageSync('idarr', idarr);
            console.log(typeNamearr);
            console.log(idarr);
            if (items.eventFrequency != 0) {
              that.setData2hiddCondition();
            }
            if (items.noticeTime != '') {
              that.setData2hiddNoticeTime();

            }
            wx.hideLoading();
          } else {
            console.log(res.statusCode);
            reject('error');
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
    })
  },



  onShow: function () {
    var that = this;
    var query = wx.createSelectorQuery()//创建节点查询器 query
    query.select('#affix').boundingClientRect()//这段代码的意思是选择Id= the - id的节点，获取节点位置信息的查询请求
    query.exec(function (res) {
      // console.log(res[0].top); // #affix节点的上边界坐
      that.setData({
        menuTop: res[0].top
      })
    });
  },
  // 2.监听页面滚动距离scrollTop
  onPageScroll: function (e) {
    // console.log(e.scrollTop);
    var that = this;
    // 3.当页面滚动距离scrollTop > menuTop菜单栏距离文档顶部的距离时，菜单栏固定定位
    if (e.scrollTop > that.data.menuTop) {
      that.setData({
        menuFixed: true
      })
    } else {
      that.setData({
        menuFixed: false
      })
    }
  }
})