const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // nickName:'',
    // avatarUrl:''
    messageNum:'',
    haveMessage:false
  },

  navigateToMessage:function(){
    wx.navigateTo({
      url: '../message/message'
    });
  },
  navigateToTypeList:function(){
    wx.navigateTo({
      url: '../scheduleTypeList/scheduleTypeList'
    });
  },
  navigateToCreateType:function(){
    wx.navigateTo({
      url: '../createScheduleType/createScheduleType'
    });
  },
  navigateToNews:function(){
    wx.navigateTo({
      url: '../news/news'
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // var nickName = wx.getStorageSync('nickName');
    // var avatarUrl = wx.getStorageSync('avatarUrl');
    // this.setData({
    //   nickName: nickName,
    //   avatarUrl: avatarUrl
    // })
    app.getMessageNum();
    var num = app.globalData.messageNum;
    this.setData({
      messageNum: num
    })
    if (num != 0){
      this.setData({
        haveMessage: false
      })
      
    }else{
      this.setData({
        haveMessage: true
      })
    }
    
    // wx.showTabBarRedDot({
    //   index: 3
    // })
    
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