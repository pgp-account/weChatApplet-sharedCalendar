//index.js
let ApiUrl = "http://39.96.188.220:8080/"
let eventListUrl = ApiUrl + "event/getEventListByTime"
let getMessagesUrl = ApiUrl + "message/getMessages"
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    dayStyle: [
      { month: 'current', day: new Date().getDate(), color: 'white', background: '#0090F0'}, 
      { month: 'current', day: new Date().getDate(), color: 'white', background: '#0090F0'}
    ],
    items:'',
    year:'',
    month:'',
    day:'',
    startTime: "",
    endTime: "",
    date:'',
    defaultItems:'',
    messageNum:0,
    accessToken:''
  },
  //给点击的日期设置一个背景颜色
  dayClick: function (event) {
    console.log(event.detail);
    let clickDay = event.detail.day;
    let changeDay = 'dayStyle[1].day';
    let changeBg = 'dayStyle[1].background';
    this.setData({
      ['dayStyle[0].color']: "#0090F0",
      ['dayStyle[0].background']: "#FFF",
      [changeDay]: clickDay,
      [changeBg]: "#0090F0",
      year: event.detail.year,
      month: event.detail.month,
      day: event.detail.day
    })
    var date = this.data.year+"-" + this.data.month + "-" + this.data.day;
    console.log(date);
    this.setData({
      date:date,
    })
    console.log(date);
   
    this.getSchedule(this.data.date).then(item => this.setData({ items: item })).catch(console.log);
    console.log(this.data.items);
    
  },
  //上个月
  prev: function (event) {
    console.log(this.data.dayStyle[0].day);
    console.log(this.data.dayStyle[1].day);
    this.setData({
      year: event.detail.currentYear,
      month: event.detail.currentMonth,
      day: this.data.dayStyle[1].day
    })
    var date = this.data.year + "-" + this.data.month + "-" + this.data.day;
    console.log(date);
    this.setData({
      date: date,
    })
    this.getSchedule(this.data.date).then(item => this.setData({ items: item })).catch(console.log);
    console.log(this.data.items);
    var currentMonth = event.detail.currentMonth;   
    if ((currentMonth == (new Date().getMonth() + 1)) && (event.detail.currentYear == new Date().getFullYear())){
      this.setData({
        ['dayStyle[0].color']: "#0090F0",
        ['dayStyle[0].background']: "#FFF",
        ['dayStyle[1].color']: "#FFF",
        ['dayStyle[1].background']: "#0090F0",
      })   
    } else {
      this.setData({
        ['dayStyle[0].color']: "#000",
        ['dayStyle[0].background']: "#FFF",
        ['dayStyle[1].color']: "#FFF",
        ['dayStyle[1].background']: "#0090F0",
        items:''
      })
    }
    
  },
  //下个月
  next: function (event) {
    console.log(this.data.dayStyle[0].day);
    console.log(this.data.dayStyle[1].day);
    console.log(event.detail);
    this.setData({
      year: event.detail.currentYear,
      month: event.detail.currentMonth,
      day: this.data.dayStyle[1].day
    })
    var date = this.data.year + "-" + this.data.month + "-" + this.data.day;
    console.log(date);
    this.setData({
      date: date,
    })
    this.getSchedule(this.data.date).then(item => this.setData({ items: item })).catch(console.log);
    console.log(this.data.items);
    var currentMonth = event.detail.currentMonth;
    
    // console.log(currentMonth);
    // console.log(this.data.month);
    // console.log(event.detail.currentYear);
    // console.log(this.data.year);
    // }
    if ((currentMonth == (new Date().getMonth() + 1)) && (event.detail.currentYear == new Date().getFullYear())) {
      this.setData({
        ['dayStyle[0].color']: "#0090F0",
        ['dayStyle[0].background']: "#FFF",
        ['dayStyle[1].color']: "#FFF",
        ['dayStyle[1].background']: "#0090F0",
      })
    } else {
      this.setData({
        ['dayStyle[0].color']: "#000",
        ['dayStyle[0].background']: "#FFF",
        ['dayStyle[1].color']: "#FFF",
        ['dayStyle[1].background']: "#0090F0",
        items: ''
      })
    }
  },

  eventDetail:function(e){
    var nowidx = e.currentTarget.dataset.idx;
    var items = this.data.items[nowidx];
    //var eventInfo = items[nowidx];
    var eventId = items.id.toString();
    console.log(items);
    console.log(eventId);
    app.globalData.date = this.data.date;
    wx.setStorageSync('eventId', eventId);
    //wx.setStorageSync('eventDetail', items);
    wx.navigateTo({
      url: "../eventDetail/eventDetail" 
    })
  },


  onLoad: function (options) {
    //this.pushDate2db();
    let that = this;
    //检查是否在登录态
    wx.checkSession({
      success: function () {
        // 存在登录态
        console.log('登录未过期')
        //console.log(that.data.dayStyle[0].day);
        //console.log(that.data.dayStyle[1].day);
        if (that.data.date == '') {
          that.setData({
            year: new Date().getFullYear(),
            month: new Date().getMonth() + 1,
            day: new Date().getDate()
          })
          var date = that.data.year + "-" + that.data.month + "-" + that.data.day;
          that.setData({
            date: date
          })
        }
        that.getSchedule(that.data.date).then(item => that.setData({ items: item })).catch(console.log);
        console.log(that.data.items);

      },
      fail: function () {
        // 不存在登录态
        wx.redirectTo({
          url: '../../pages/login/login',
        });
      }
    });
    // var num =  app.globalData.messageNum;
    
    app.getMessageNum();
  },
  
  setData2items: function (data) {
    this.setData({
      items: data,
      // defaultItems:data
    });
  },
  addSchedule:function(){
    //var date = this.data.year + "-" + this.data.month + "-" + this.data.day;
    app.globalData.date = this.data.date;
    wx.navigateTo({
      url: "../createSchedule/createSchedule"
    })
  },
  onShow: function () {
    this.onLoad();
  },

  //获取事件   使用Promise()解决request请求异步的问题,传值问题
  getSchedule:function(date){
    var date = date;
    var startTime = date + " 00:00:00";
    var endTime = date + " 23:59:59";
    var result ;
    console.log(wx.getStorageSync('session_id'));
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
          } else {
            console.log(res.statusCode)
          }
        }, fail: function (res) {
          console.log("error");
          reject('error');
        }
      })
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    //this.pushDate2db();
  },

})

