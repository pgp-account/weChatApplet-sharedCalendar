// pages/typeDetails/typeDetails.js
let ApiUrl = "http://39.96.188.220:8080/"
let typeDetailUrl = ApiUrl + "eventtype/getEventTypeInfo"
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    typeName:'',
    createTime:'',
    subscriberNum:'',
    typeDescrption:'',
    creatorName:'',
    shareCode:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    console.log('option',options);
    let typeId = options.typeId;
    console.log('typeId', typeId);
    let shareCode = options.shareCode;
    console.log('shareCode', shareCode);
    that.setData2IdshareCode(typeId,shareCode);
    wx.request({
      url: typeDetailUrl,
      method: "GET",//默认GET
      data:{
        typeId: typeId
      },
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
          that.setData2data(res.data.data.EventType);
          
        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    });
  },
  setData2IdshareCode: function (data,shareCode){
    this.setData({
      id: data,
      shareCode: shareCode
    })
    console.log(this.data.id);
    console.log(this.data.shareCode);
  },
  setData2data:function(data){
    this.setData({
      typeName: data.typeName,
      createTime: data.createTime,
      subscriberNum: data.subscriberNum,
      typeDescrption: data.typeDescrption,
      creatorName: data.creatorName
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  onShareAppMessage() {
    console.log(this.data.id);
    app.globalData.typeId = this.data.id;
    app.globalData.shareCode = this.data.shareCode;

    return {

      title: '快来看看我分享的日程吧',

      desc: '分享页面的内容',

      path: '/pages/sharedTypeDetails/sharedTypeDetails?typeId=' + this.data.id + "&shareCode=" + this.data.shareCode // 路径，传递参数到指定页面



    }

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
  
})