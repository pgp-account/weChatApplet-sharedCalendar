// pages/createScheduleType/createScheduleType.js
var util = require('../../utils/util.js');
var globalApi = require("../../utils/apiUrl.js");

var app = getApp()

let eventypeUrl = globalApi.eventypeUrl
let getFormIdList = globalApi.getFormIdList
Page({

  /**
   * 页面的初始数据
   */
  data: {
    typeName:'',
    typeDescrption:'',
    array: ['仅自己可见', '分享可见'],
    index: 0,
    time:'',
    formIdArray:[]
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

  saveFormId: function (v) {
    if (v.detail.formId != 'the formId is a mock one') {
      var formIdArray = this.data.formIdArray;
      var newFormID = v.detail.formId;
      var a = formIdArray.indexOf(newFormID);
      if (a === -1) {
        formIdArray.push(v.detail.formId);
      }
    }
  },

  //获取formID
  getFormIdList: function (formIDArray) {
    return new Promise(function (resolve, reject) {
      wx.request({
        url: getFormIdList,
        method: "POST",//默认GET
        data: {
          "formIds": formIDArray
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
            resolve("true");

          } else {
            console.log(res.statusCode);

          }
        }, fail: function (res) {
          console.log("error");
          reject('error');
        }
      })
    })
  },

  isEmpty: function (data) {
    if (data == '') {
      return false;
    } else {
      return true;
    }
  },

  formSubmit: function (e) {
    this.saveFormId(e);
    console.log("formIds:" + this.data.formIdArray);
    // let formIDJSON= JSON.stringify(this.data.formIdArray);
    this.getFormIdList(this.data.formIdArray).then().catch(console.log);

    //var index = this.data.index;
    var header = wx.getStorageSync('session_id');
    console.log(header);
    if (this.isEmpty(this.data.typeName)) {
      if (this.isEmpty(this.data.typeDescrption)){
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

          }, fail: function (res) {
            console.log("error");
          }
        })
      }else{
        wx.showToast({
          title: '请填写日程类型描述！',
          icon: 'none',
          duration: 2000//持续的时间
        })
      }
    } else {
      wx.showToast({
        title: '请填写日程类型名称！',
        icon: 'none',
        duration: 2000//持续的时间
      })
    }
    
  },
})