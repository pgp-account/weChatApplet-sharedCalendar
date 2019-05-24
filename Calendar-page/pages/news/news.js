// pages/news/news.js
let ApiUrl = "http://39.96.188.220:8080/"
let noticeList = ApiUrl + "eventNotice/getNoticeList"
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
            //此方法报错
            // this.setData({ 
            //   items = res.data.data.EventTypes
            // })
            //让异步方法变成同步
            that.setData2items(res.data.data.eventNotices);
            // var typeNamearr = new Array();
            // var idarr = new Array();
            // for (var i = 0; i < res.data.data.EventTypes.length; i++) {
            //   typeNamearr.push(res.data.data.EventTypes[i].typeName);
            //   idarr.push(res.data.data.EventTypes[i].id);
            // }
            // wx.setStorageSync('typeNamearr', typeNamearr);
            // wx.setStorageSync('idarr', idarr);
            // console.log(typeNamearr);
            // console.log(idarr);
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