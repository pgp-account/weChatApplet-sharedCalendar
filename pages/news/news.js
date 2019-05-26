// pages/news/news.js
var globalApi = require("../../utils/apiUrl.js");
const app = getApp();

let noticeList = globalApi.noticeList
Page({

  /**
   * 页面的初始数据
   */
  data: {
    items:'',
    noticeNullHidden:true,
    noticeHidden:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.showLoading({
      title: '加载中',
    })
    let that = this;
    wx.request({
      url: noticeList,
      method: "GET",//默认GET
      header: {
        "Cookie": "SESSION=" + wx.getStorageSync('session_id'),
      },
      success: function (res) {
        if (res.statusCode === 200) {
          console.log(res.data.meta.message);
          console.log(res.data.data);
          wx.hideLoading();
          if (res.data.data.Total != 0) {
            that.setData2noticeHidden();
            that.setData2items(res.data.data.eventNotices);            
          }else{
            that.setData2noticeHiddenNO();
          }

        } else {
          console.log(res.statusCode);
          wx.hideLoading();
        }

      }, fail: function (res) {
        console.log("error");
        wx.hideLoading();
      }
    });
  },
  setData2noticeHidden: function () {
    this.setData({
      noticeNullHidden: true,
      noticeHidden: false
    })
  },
  setData2noticeHiddenNO:function(){
    this.setData({
      noticeNullHidden: false,
      noticeHidden: true
    })
  },
  setData2items: function (data) {
  this.setData({
    items: data,
  });
  console.log(this.data.items);
},
  // eventDetail: function (e) {
  //   var nowidx = e.currentTarget.dataset.idx;
  //   var items = this.data.items[nowidx];
  //   //var eventInfo = items[nowidx];
  //   var eventId = items.id.toString();
  //   console.log(items);
  //   console.log(eventId);
  //   app.globalData.date = this.data.date;
  //   wx.setStorageSync('eventId', eventId);
  //   //wx.setStorageSync('eventDetail', items);
  //   wx.navigateTo({
  //     url: "../eventDetail/eventDetail"
  //   })
  // },

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