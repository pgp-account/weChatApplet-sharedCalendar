var util = require('../../utils/util.js');
var globalApi = require("../../utils/apiUrl.js");

var app = getApp()
let eventUrl = globalApi.eventUrl
let eventypeListUrl = globalApi.eventypeListUrl
let getFormIdList = globalApi.getFormIdList

Page({
  data: {
    name: '',
    type: '',
    describe: '',
    repetition: 1,
    reset: '',
    hidden:true,
    ratearray: ['不重复', '每日', '每周', '每年'],
    rateindex: 0,
    conditionarray: ['重复次数', '重复截止时间', '无限重复'],
    conditionindex: 0,
    remindarray: ['不设置提醒', '提前十分钟', '提前半小时'],
    remindindex: 0,
    hiddenTime: false,
    hiddenDate: true,
    date:null,
    time:'',
    typeindex:0,
    typearray:'',
    typeIdarray:'',
    startime: '',
    endtime: '',
    counterId:'',
    openid:'',
    isNoticed:null,
    execTime:'',
    noticedID:'',
    eventFrequency:'',
    formId:'',
    oldStartTime:'',
    oldEndTime:'',
    formIdArray:[]
  },

  onLoad: function (options) {
    var startime = util.formatTime(new Date()).slice(11,16);
    var endtime = '';
    if (parseInt(startime.slice(0, 2)) <= 22){
      endtime = (parseInt(startime.slice(0, 2)) + 1).toString() + startime.slice(2, 5);
    }else(
      endtime = '23:59'
    )
    this.setData({
      startime: startime,
      endtime: endtime,
      oldStartTime: startime,
      oldEndTime: endtime,
    })
    //wx.getStorageSync('date');
    
    var date = app.globalData.date;
    this.setData({
      time: date
    })
    // console.log('options', options);
    // var time = options.date;
     console.log(this.data.time);
    // that.setDate2time(time);
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
         
          //让异步方法变成同步
          //that.setData2items(res.data.data.EventTypes);
          var typeNamearr = new Array();
          var idarr = new Array();
          for (var i = 0; i < res.data.data.EventTypes.length; i++) {
            typeNamearr.push(res.data.data.EventTypes[i].typeName);
            idarr.push(res.data.data.EventTypes[i].id);
          }

          typeNamearr.push("创建新类型");
          // console.log(typeNamearr);
          // console.log(idarr);
           that.setData2data(typeNamearr, idarr);
          // resolve(typeNamearr, idarr);

        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    });
    
  },
  setData2data:function(typeNamearr, idarr){
    this.setData({
      typearray: typeNamearr,
      typeIdarray: idarr
    })
    console.log(this.data.typearray);
    console.log(this.data.idarr);
  },
  setDate2time:function(time){
    this.setData({
      time:time
    })
  },
  switchChange(e) {
    
    //console.log('switch1 发生 change 事件，携带值为', e.detail.value);
    if (e.detail.value) {
      this.setData({
        startime: '0:00',
        endtime: '23:59'
      })
    } else {
      this.setData({
        startime: this.data.oldStartTime,
        endtime: this.data.oldEndTime
      })
    }
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
    this.setData({
      typeindex: e.detail.value
    });

    if (e.detail.value == this.data.typearray.length-1){
      wx.navigateTo({
        url: "../createScheduleType/createScheduleType"
      })
    }
    //console.log(this.data.typeIdarray[this.data.typeindex]);
  },

  //日程频率
  bindPickerChange(e) {
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      rateindex: e.detail.value
    })
    if (e.detail.value != 0){
      this.setData({
        hidden:false
      })
    }else{
      this.setData({
        hidden: true,
        eventEndCondition:'' ,
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
        date:this.data.time
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

  //提醒时间
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
      if(a === -1){
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

  isEmpty:function(data){
    if (data == ''){
      return false;
    }else{
      return true;
    }
  },

  formSubmit: function (e) {
    this.saveFormId(e);
    console.log("formIds:" + this.data.formIdArray);
    // let formIDJSON= JSON.stringify(this.data.formIdArray);
    this.getFormIdList(this.data.formIdArray).then().catch(console.log);

    var sTime = this.data.time+" "+this.data.startime+":00";
    console.log(sTime);
    var eTime = this.data.time + " "+ this.data.endtime + ":00";
    console.log(eTime);
    console.log(this.data.repetition);
    let that = this;
    if (this.isEmpty(this.data.name)){
      if (this.isEmpty(this.data.describe)){
        wx.request({
          url: eventUrl,
          method: "POST",//默认GET
          data: {
            "eventName": this.data.name,
            "startTime": new Date(sTime),
            "endTime": new Date(eTime),
            "fkTypeId": this.data.typeIdarray[this.data.typeindex],
            "eventContent": this.data.describe,
            "eventFrequency": this.data.rateindex,
            "eventEndCondition": this.data.conditionindex,
            "repeatTimes": this.data.repetition,
            "repeatEndTime": new Date(this.data.date),
            "noticeChoice": this.data.remindindex
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
              console.log("seccess");
              console.log(res.data);
              if (res.data.meta.success) {
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
        });
      }else{
        wx.showToast({
          title: '请填写日程描述！',
          icon: 'none',
          duration: 2000//持续的时间
        })
      }
      
    }else{
      wx.showToast({
        title: '请填写日程名称！',
        icon: 'none',
        duration: 2000//持续的时间
      })
    }      
  },
  setData2db: function (counterId, execTime, noticedID, eventFrequency, formId){
    this.setData({
      counterId: counterId,
      execTime: execTime,
      noticedID: noticedID,
      eventFrequency: eventFrequency,
      formId: formId
    })
    
  },
  onShow: function () {
    let that = this;
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

          //让异步方法变成同步
          //that.setData2items(res.data.data.EventTypes);
          var typeNamearr = new Array();
          var idarr = new Array();
          for (var i = 0; i < res.data.data.EventTypes.length; i++) {
            typeNamearr.push(res.data.data.EventTypes[i].typeName);
            idarr.push(res.data.data.EventTypes[i].id);
          }

          typeNamearr.push("创建新类型");
          // console.log(typeNamearr);
          // console.log(idarr);
          that.setData2data(typeNamearr, idarr);
          // resolve(typeNamearr, idarr);

        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    });
  },
})
