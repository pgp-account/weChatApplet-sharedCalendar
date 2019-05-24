// pages/createScheduleType/createScheduleType.js
var util = require('../../utils/util.js');
let ApiUrl = "http://39.96.188.220:8080/"
let eventypeUrl = ApiUrl + "eventtype/createEventTpye"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    typeName:'',
    typeDescrption:'',
    array: ['0-仅自己可见', '1-分享可见'],
    index: 0,
    time:'',
  },

  bindPickerChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var TIME = util.formatTime(new Date());
    this.setData({
      time: TIME,
    });
  },

  typeNameInput: function (e) {
    this.setData({
      typeName: e.detail.value
    })
  },
  typeDescrptionInput: function (e) {
    this.setData({
      typeDescrption: e.detail.value
    })
  },

  formSubmit: function (e) {
    //var index = this.data.index;
    var header = wx.getStorageSync('session_id');
    console.log(header);
    wx.request({
      url: eventypeUrl,
      method: "POST",//默认GET
      data: {
        "typeName": this.data.typeName,
        "typeDescrption": this.data.typeDescrption,
        "typeTransparency": this.data.index,
        "createTime": this.data.time,
      },
      header: {
        //以表单形式提交
        //"Cookie": "JSESSIONID=" + wx.getStorageSync('session_id'),
        "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
        "content-type": "application/x-www-form-urlencoded"
        

        //以json形式提交
        //"content-type": "application/json"
      },
      success: function (res) {
        if (res.statusCode === 200) {
          console.log("seccess");
          if (res.data.meta.message) {
            wx.showToast({

              title: '成功',

              icon: 'success',

              duration: 3000//持续的时间

            })
            wx.navigateBack({
              delta: 1
            })
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
})