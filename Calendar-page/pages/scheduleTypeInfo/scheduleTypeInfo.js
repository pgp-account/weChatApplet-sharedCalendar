// pages/scheduleTypeInfo/scheduleTypeInfo.js
let ApiUrl = "http://39.96.188.220:8080/"
let eventypeUpdateUrl = ApiUrl + "eventtype/updateEventTypeInfo"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    typeDescrption:'',
    typeName:'',
    index:'',
    array: ['0-仅自己可见', '1-分享可见'],
    subscriberNum:'',
    operationType:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      subscriberNum: options.subscriberNum
    })
    var id = wx.getStorageSync('id');
    var name = wx.getStorageSync('typeName');
    var description = wx.getStorageSync('typeDescrption');
    var transparency = wx.getStorageSync('typeTransparency');
    console.log(transparency);
    var that = this;
    that.setData2data(id,name,description,transparency);

  },

  setData2data: function (id, name, description, transparency) {
    this.setData({
      id: id,
      typeName:name,
      typeDescrption: description,
      index: transparency
    });
  },

  bindPickerChange(e) {
    console.log(this.data.index);
    console.log('picker发送选择改变，携带值为', e.detail.value);
    let newindex = e.detail.value;
    let that = this;
    if (this.data.index == 1 && newindex){
      if(this.data.subscriberNum != 0){
        wx.showModal({
          title: '该类型已被其他用户订阅',
          content: '更改能见度后订阅过该类型的用户能否继续查看类型下日程？',
          showCancel: true,//是否显示取消按钮
          cancelText: "不可以",//默认是“取消”
          cancelColor: '#179B16',//取消文字的颜色
          confirmText: "可以",//默认是“确定”
          confirmColor: '#179B16',//确定文字的颜色
          success: function (res) {
            if (res.cancel) {
              //点击取消,默认隐藏弹框
              that.setData({
                operationType: 1,
                index: newindex
              })
            } else {
              //点击确定
              
              that.setData({
                operationType: 2,
                index: newindex
              })
            }
          },
          fail: function (res) { },//接口调用失败的回调函数
          complete: function (res) { },//接口调用结束的回调函数（调用成功、失败都会执行）
        })
      }
    }else{
      this.setData({
        index: e.detail.value
      })
    }
    
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
    //console.log(header);
    wx.request({
      url: eventypeUpdateUrl,
      method: "POST",//默认GET
      data: {
        "typeId":this.data.id,
        "typeName": this.data.typeName,
        "typeDescrption": this.data.typeDescrption,
        "typeTransparency": this.data.index,
        "operationType": this.data.operationType
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
          console.log(res.data.meta.message);
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
    wx.navigateBack({
      delta: 1
    })
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