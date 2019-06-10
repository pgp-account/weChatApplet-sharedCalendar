// pages/scheduleTypeList/scheduleTypeList.js
var globalApi = require("../../utils/apiUrl.js");
const app = getApp();
let eventypeListUrl = globalApi.eventypeListUrl
let subEventTypeList = globalApi.subEventTypeList
let allEventTypeList = globalApi.allEventTypeList
let eventypeDeleteUrl = globalApi.eventypeDeleteUrl
let generateShareCode = globalApi.generateShareCode
let cancelSubscribeType = globalApi.cancelSubscribeType
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentData: 0,
    id: '',
    // typeName:'科研任务',
    array: ['仅自己可见', '分享可见'],
    // typeTransparency:1,
    // createTime:'2019-04-16 07:05:53',
    // subscriberNum:0,
    // typeDescrption:'科研相关的日程安排',
    items: '',
    //createItems:'',
    //subscribeItems:'',
    typeArray: ['删除', '转至另一个日程类型下'],
    arrindex: 0,
    operationType: 1,
    newEventType: '',
    createNullHidden: false,
    createHidden: true,
    subscribeNullHidden: false,
    subscribeHidden: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //var items = this.data.items;
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
          if (res.data.data.Total != 0) {
            that.setData2createHidden();
            //此方法报错
            // this.setData({ 
            //   items = res.data.data.EventTypes
            // })
            //让异步方法变成同步
            that.setData2items(res.data.data.EventTypes);
            var typeNamearr = new Array();
            var idarr = new Array();
            for (var i = 0; i < res.data.data.EventTypes.length; i++) {
              typeNamearr.push(res.data.data.EventTypes[i].typeName);
              idarr.push(res.data.data.EventTypes[i].id);
            }
            wx.setStorageSync('typeNamearr', typeNamearr);
            wx.setStorageSync('idarr', idarr);
            console.log(typeNamearr);
            console.log(idarr);
          }

        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    });

  },
  setData2createHidden: function () {
    this.setData({
      createNullHidden: true,
      createHidden: false
    })
  },
  setData2subscribeHidden: function () {
    this.setData({
      subscribeNullHidden: true,
      subscribeHidden: false
    })
  },
  setData2items: function (data) {
    this.setData({
      items: data,
    });
    console.log(this.data.items);
  },
  // setData2createItems: function (data) {
  //   this.setData({
  //     createItems: data,
  //   });
  // },
  // setData2subscribeItems: function (data) {
  //   this.setData({
  //     subscribeItems: data,
  //   });
  // },


  //获取当前滑块的index
  bindchange: function (e) {
    const that = this;
    that.setData({
      currentData: e.detail.current
    })
    if (e.detail.current == 1) {
      wx.request({
        url: subEventTypeList,
        method: "GET",//默认GET
        header: {
          "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
        },
        success: function (res) {
          if (res.statusCode === 200) {
            console.log(res.data.meta.message);
            console.log(res.data.data);
            if (res.data.data.Total != 0) {
              that.setData2subscribeHidden();
              //此方法报错
              // this.setData({ 
              //   items = res.data.data.EventTypes
              // })
              //让异步方法变成同步
              that.setData2items(res.data.data.SubscribedEventTypes);
              var typeNamearr = new Array();
              var idarr = new Array();
              for (var i = 0; i < res.data.data.SubscribedEventTypes.length; i++) {
                typeNamearr.push(res.data.data.SubscribedEventTypes[i].typeName);
                idarr.push(res.data.data.SubscribedEventTypes[i].id);
              }
              wx.setStorageSync('typeNamearr', typeNamearr);
              wx.setStorageSync('idarr', idarr);
              console.log(typeNamearr);
              console.log(idarr);
            }
          } else {
            console.log(res.statusCode)
          }

        }, fail: function (res) {
          console.log("error");
        }
      });

    } else if (e.detail.current == 0) {
      this.onLoad();
    }
  },
  //点击切换，滑块index赋值
  checkCurrent: function (e) {
    const that = this;

    if (that.data.currentData === e.target.dataset.current) {
      return false;
    } else {

      that.setData({
        currentData: e.target.dataset.current
      })
    }
  },

  typeShowEvent: function (e) {
    var nowidx = e.currentTarget.dataset.idx;
    var items = this.data.items;
    var date = items[nowidx].createTime.slice(0,7);
    console.log(date);
    var id = items[nowidx].id;
    var typeName = items[nowidx].typeName;
    wx.navigateTo({
      url: "../scheduleListByType/scheduleListByType?typeId=" + id + "&typeName=" + typeName + "&date=" + date
    })
  },

  //修改类型
  editEventTypes: function (e) {
    var nowidx = e.currentTarget.dataset.idx;
    var items = this.data.items;
    console.log(items);
    var subscriberNum = items[nowidx].subscriberNum;
    var id = items[nowidx].id;
    wx.setStorageSync('id', id);
    var name = items[nowidx].typeName;
    wx.setStorageSync('typeName', name);
    var description = items[nowidx].typeDescrption;
    wx.setStorageSync('typeDescrption', description);
    var transparency = items[nowidx].typeTransparency;
    console.log(transparency);
    wx.setStorageSync('typeTransparency', transparency);
    wx.navigateTo({
      url: "../scheduleTypeInfo/scheduleTypeInfo?subscriberNum=" + subscriberNum
    })
  },

  //分享类型

  shareEventTypes: function (e) {
    var nowidx = e.currentTarget.dataset.idx;
    var items = this.data.items;
    console.log(items);
    var shareId = items[nowidx].id;
    if (items[nowidx].typeTransparency == 0){
      wx.showToast({

        title: '仅自己可见的类型不能分享哦，自己的隐私要注意保护呦',

        icon: 'none',

        duration: 3000//持续的时间

      })
    } else if (items[nowidx].typeTransparency == 1){
      wx.request({
        url: generateShareCode,
        method: "POST",//默认GET
        data: {
          "typeId": shareId
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
            var shareCode = res.data.data.share_code
            //此方法报错
            // this.setData({ 
            //   items = res.data.data.EventTypes
            // })
            //让异步方法变成同步
            // that.setData2items(res.data.data.EventVOs);
            wx.navigateTo({
              url: "../typeDetails/typeDetails?typeId=" + shareId + "&shareCode=" + shareCode
            })

          } else {
            console.log(res.statusCode)
          }
        }, fail: function (res) {
          console.log("error");
        }
      })
    }
    
  },


  //删除类型
  deleteEventTypes: function (e) {
    var that = this;
    var newEventType = '';
    var nowidx = e.currentTarget.dataset.idx;
    var olditems = this.data.items;
    var id = olditems[nowidx].id.toString();
    var typeNamearr = new Array();
    var idarr = new Array();
    for (var i = 0; i < olditems.length; i++) {
      typeNamearr.push(olditems[i].typeName);
      idarr.push(olditems[i].id);
    }
    typeNamearr.splice(nowidx, 1);
    idarr.splice(nowidx, 1);
    //console.log(typeNamearr);

    wx.showActionSheet({
      itemList: ['删除', '转至另一个日程类型下'],
      success: function (res) {
        console.log(res.tapIndex);
        if (res.tapIndex === 0) {
          //that.setData2operationType1();
          wx.showModal({
            title: '提示',
            content: '确认删除该日程类型吗，删除后该类型下的所有日程都会被删除',
            success: function (res) {
              if (res.confirm) {
                wx.request({
                  url: eventypeDeleteUrl,
                  method: "POST",//默认GET
                  data: {
                    "operationType": 1,
                    "eventTypeIds": id,

                  },
                  header: {
                    "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
                    'content-type': 'application/x-www-form-urlencoded'
                  },
                  success: function (res) {
                    if (res.statusCode === 200) {
                      console.log(res.data);
                      console.log(res.data.meta.message);
                      if (res.data.meta.success == true){
                        wx.showToast({
                          title: '删除成功',
                          icon: 'success',
                          duration: 2000
                        })
                        olditems.splice(nowidx, 1);
                        that.setData2items(olditems);
                        console.log(olditems);
                      }else{
                        let message = res.data.meta.message;
                        wx.showToast({

                          title: message,

                          icon: 'none',

                          duration: 2000//持续的时间

                        })
                      }
                      
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
          
        } else if (res.tapIndex === 1) {
          //that.setData2operationType2();
          wx.showActionSheet({
            itemList: typeNamearr,
            success(res) {
              var tapIndex = res.tapIndex;
              newEventType = idarr[tapIndex];
              wx.request({
                url: eventypeDeleteUrl,
                method: "POST",//默认GET
                data: {
                  "operationType": 2,
                  "eventTypeIds": id,
                  "newEventType": newEventType
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
            }
          })
        }

      },
      fail: function (res) {
        console.log(res.errMsg)
      }
    });

  },


  //删除订阅类型，取消订阅
  deleteSubscribeTypes: function (e) {
    var that = this;
    var newEventType = '';
    var nowidx = e.currentTarget.dataset.idx;
    var olditems = this.data.items;
    var id = olditems[nowidx].id;
    var typeNamearr = new Array();
    var idarr = new Array();
    for (var i = 0; i < olditems.length; i++) {
      typeNamearr.push(olditems[i].typeName);
      idarr.push(olditems[i].id);
    }
    typeNamearr.splice(nowidx, 1);
    idarr.splice(nowidx, 1);
    wx.showModal({
      title: '提示',
      content: '确认取消订阅该日程类型吗',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: cancelSubscribeType,
            method: "POST",//默认GET
            data: {
              "typeId": id,

            },
            header: {
              "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
              'content-type': 'application/x-www-form-urlencoded'
            },
            success: function (res) {
              if (res.statusCode === 200) {
                console.log(res.data);
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
          });
        } else {
          console.log('弹框后点取消')
        }
      }
    })
  },
  onShow: function () {
    this.onLoad();
  },
})