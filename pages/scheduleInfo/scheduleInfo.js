// pages/scheduleInfo/scheduleInfo.js
var globalApi = require("../../utils/apiUrl.js");
const app = getApp();

let eventypeListUrl = globalApi.eventypeListUrl

let evenUpdateUrl = globalApi.evenUpdateUrl

let getFormIdList = globalApi.getFormIdList

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    name: '',
    type: '',
    describe: '',
    fkTypeId:'',
    typeName:'',
    repetition: '',
    hidden: true,
    ratearray: ['0 - 不重复', '1 - 每日', '2 - 每周', '3 - 每年'],
    rateindex: '',
    conditionarray: ['0-重复次数', '1-重复截止时间', '2-无限重复'],
    conditionindex: '',
    remindarray: ['0-不设置提醒', '1-提前十分钟', '2-提前半小时'],
    remindindex: '0',
    hiddenTime: true,
    hiddenDate: true,
    date: null,
    typeNamearr:'',
    idarr:'',
    typeindex: 0,
    typearray: '',
    typeIdarray: '',
    startime: '',
    endtime: '',
    time:'',
    formIdArray:[]
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var date = app.globalData.date;
    this.setData({
      time: date
    })
    var eventInfo = wx.getStorageSync('eventInfo');
    console.log(eventInfo);
    var id = eventInfo.id;
    var eventName = eventInfo.eventName;
    var startTime = eventInfo.startTime.substring(11, 16); 
    var endTime = eventInfo.endTime.substring(11, 16);    
    var fkTypeId = eventInfo.fkTypeId;
    var typeName = eventInfo.eventTypeName;
    var eventContent = eventInfo.eventContent;
    var eventFrequency = eventInfo.eventFrequency;
    var eventEndCondition = eventInfo.eventEndCondition;
    var repeatTimes = eventInfo.repeatTimes;
    var repeatEndTime = eventInfo.repeatEndTime;   
    var noticeChoice = eventInfo.noticeChoice;

    var that = this;

    wx.request({
      url: eventypeListUrl,
      method: "GET",//默认GET
      header: {
        "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
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
          //that.setData2items(res.data.data.EventTypes);
          var typeNamearr = new Array();
          var idarr = new Array();
          for (var i = 0; i < res.data.data.EventTypes.length; i++) {
            typeNamearr.push(res.data.data.EventTypes[i].typeName);
            idarr.push(res.data.data.EventTypes[i].id);
          }
          //wx.setStorageSync('typeNamearr', typeNamearr);
          //wx.setStorageSync('idarr', idarr);
          //typeNamearr.push("创建新类型");
          //console.log(typeNamearr);
          //console.log(idarr);
          typeNamearr.push("创建新类型");
          that.setData2array(typeNamearr, idarr);
          for (var i = 0; i < typeNamearr.length;i++){
            if (typeName == typeNamearr[i]){
              that.setData2typeindex(i);
              //var typeindex = i;
            }
          }

          that.setData2data(id, eventName, startTime, endTime, fkTypeId, typeName, eventContent, eventFrequency, eventEndCondition, repeatTimes, repeatEndTime, noticeChoice);
          if (eventFrequency != 0) {
            that.setData2hidden();
            if (eventEndCondition == 0) {
              that.setData2hidden0();
            } else if (eventEndCondition == 1) {
              that.setData2hidden1();
            } else {
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
    });

    

    // if (eventFrequency !=0){
    //   that.setData2hidden();      
    // } else {
    //   this.setData({
    //     hidden: true,
    //     eventEndCondition: '',
    //     date: null
    //   });
    // }
  },
  setData2data: function (id, eventName, startTime, endTime, fkTypeId, typeName, eventContent, eventFrequency, eventEndCondition, repeatTimes, repeatEndTime, noticeChoice){
    this.setData({
      id:id,
      name: eventName,
      describe: eventContent,
      fkTypeId: fkTypeId,
      typeName: typeName,
      repetition: repeatTimes,
      rateindex: eventFrequency,
      conditionindex: eventEndCondition,
      remindindex: noticeChoice,
      date: repeatEndTime,
      startime: startTime,
      endtime: endTime,
    })
  },
  setData2hidden:function(){
    this.setData({
      hidden:false,
    })
  },
  setData2hidden0: function () {
    this.setData({
      hiddenTime: false,
    })
  },
  setData2hidden1: function () {
    this.setData({
      hiddenDate: false,
    })
  },
  setData2hidden2: function () {
    this.setData({
      hiddenTime: true,
      hiddenDate: true,
    })
  },
  setData2HD: function () {
    this.setData({
      hidden: true,
      eventEndCondition: '',
      date: null,
      hiddenDate: true,
      hiddenTime: true
    });
  },
  setData2typeindex: function (i) {
    this.setData({
      typeindex: i,
    });
    console.log(this.data.typeindex);
    console.log(this.data.typeNamearr[this.data.typeindex]);
  },
  setData2array: function (typeNamearr, idarr) {
    this.setData({
      typeNamearr: typeNamearr,
      typeIdarray: idarr
    })
    //console.log(this.data.typearray);
    //console.log(this.data.idarr);
  },

  bindStartTimeChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      startime: e.detail.value
    })
  },
  bindEndTimeChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      endtime: e.detail.value
    })
  },

  //日程类型
  bindTypePickerChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    if (e.detail.value == this.data.typeNamearr.length - 1) {
      wx.navigateTo({
        url: "../createScheduleType/createScheduleType"
      })
    }
    var typeIdarray = this.data.typeIdarray;
    var typeNamearr = this.data.typeNamearr;
    this.setData({
      typeindex: e.detail.value,
      fkTypeId: typeIdarray[e.detail.value],
      typeName: typeNamearr[e.detail.value]
    });
    //console.log(this.data.fkTypeId);
    //console.log(this.data.typeName);
  },

  //日程频率
  bindPickerChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      rateindex: e.detail.value
    })
    if (e.detail.value != 0) {
      this.setData({
        hidden: false
      })
    } else {
      this.setData({
        hidden: true,
        eventEndCondition: '',
        date: null
      });

    }
    console.log(this.data.repetition);
  },

  //重复终止条件
  bindConPickerChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      conditionindex: e.detail.value
    })
    if (e.detail.value == 1) {
      this.setData({
        hiddenDate: false,
        hiddenTime: true,
        repetition: '',
        date: new Date
      })
    } else if (e.detail.value == 0) {
      this.setData({
        repetition: 1,
        hiddenDate: true,
        hiddenTime: false,
        date: null
      })
    }
    console.log(this.data.repetition);
  },

  //重复截止时间
  bindDateChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },
  bindRemPickerChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      remindindex: e.detail.value
    })
  },
  nameInput: function (e) {
    this.setData({
      name: e.detail.value
    })
  },
  typeInput: function (e) {
    this.setData({
      type: e.detail.value
    })
  },
  describeInput: function (e) {
    this.setData({
      describe: e.detail.value
    })
  },
  repetitionInput: function (e) {
    this.setData({
      repetition: e.detail.value
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

  formSubmit: function (e) {
    this.saveFormId(e);
    console.log("formIds:" + this.data.formIdArray);
    // let formIDJSON= JSON.stringify(this.data.formIdArray);
    this.getFormIdList(this.data.formIdArray).then().catch(console.log);

    // var rateindex = this.data.rateindex;
    var Time = this.data.time;
    var sTime = Time + " " + this.data.startime + ":00";
    console.log(sTime);
    var eTime = Time + " " + this.data.endtime + ":00";
    console.log(this.data.repetition);
    var typeIdarray = this.data.typeIdarray;
    var typeindex = this.data.typeindex;
    wx.request({
      url: evenUpdateUrl,
      method: "POST",//默认GET
      data: {
        eventId:this.data.id,
        eventName: this.data.name,
        startTime: new Date(sTime),
        endTime: new Date(eTime),
        fkTypeId: typeIdarray[typeindex],
        eventContent: this.data.describe,
        eventFrequency: this.data.rateindex,
        eventEndCondition: this.data.conditionindex,
        repeatTimes: this.data.repetition,
        repeatEndTime: new Date(this.data.date),
        noticeChoice: this.data.remindindex
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
          wx.navigateBack({
            delta: 1
          })
        } else {
          console.log(res.statusCode)
        }
        // this.setData({
        //   reset: ''
        // })
      }, fail: function (res) {
        console.log("error");
      }
    });

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