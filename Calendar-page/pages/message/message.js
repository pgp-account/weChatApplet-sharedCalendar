// pages/message/message.js
let ApiUrl = "http://39.96.188.220:8080/"
let getMessagesUrl = ApiUrl + "message/getMessages"
let deleteMessagesUrl = ApiUrl + "message/deleteMessages"
let setMessagesReadUrl = ApiUrl + "message/setMessagesRead"
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    items:'',
    hiddenCheck:true,
    arr: [],
    arrStatus: [],
    messageNullHidden: false,
    messageHidden: true,
    num:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      items: '',
      hiddenCheck: true,
      arr: [],
      arrStatus: [],
      messageNullHidden:false,
      messageHidden:true
    })
    var that = this;
    var arr = new Array();
    wx.request({
      url: getMessagesUrl,
      method: "GET",//默认GET
      header: {
        "Cookie": "SESSION=" + wx.getStorageSync('session_id')
      },
      success: function (res) {
        if (res.statusCode === 200) {
          console.log(res.data.meta.message);
          console.log(res.data.data);
          var item = res.data.data.Messages;
          if (res.data.data.Total !=0){
            that.setData2MessageHidden();
          }
          var num = 0;
          for (var i  in res.data.data.Messages){
            arr[i] = false;
            
          }
          for(var j = 0;j<item.length;j++){
            if (item[j].isRead == 0){
              num++;
            }
          }
          console.log(num);
          app.globalData.messageNum = num;
          
          that.setData2num(num);
          that.setData2arrStatus(arr);
          
          that.setData2items(res.data.data.Messages);
          
        } else {
          console.log(res.statusCode)
        }

      }, fail: function (res) {
        console.log("error");
      }
    })
  },

  setData2num:function(num){
    this.setData({
      num:num,
    })
  },
  
  setData2items:function(data){
    this.setData({
      items:data,
    })
  },
  setData2arrStatus:function(arr){
    this.setData({
      arrStatus:arr
    })
  },
  setData2MessageHidden:function(){
    this.setData({
      messageNullHidden: true,
      messageHidden: false
    })
  },

  showCheckbox:function(e){
    console.log(e.currentTarget.dataset.idx);
    this.setData({
      hiddenCheck:false,   
    })
    //this.checkboxChange(e);
    //this.data.items[e.currentTarget.dataset.idx].checked = true;
  },

  checkboxChange: function (e) {

    //console.log('checkbox发生change事件，携带value值为：', e.detail.value)

    //获取当前选中的值
    var checkValue = e.currentTarget.dataset.val;
    //获取当前选中的下标
    var checkIndex = e.currentTarget.dataset.index;
    //当前选中的取反值
    this.data.arrStatus[checkIndex] = !this.data.arrStatus[checkIndex];
    if (this.data.arrStatus[checkIndex]) {
      //如果当前为选中状态则将值插入进数组中
      this.data.arr.push(checkValue);
    } else {
      //如果当前为未选中状态则将值从数组中删除并返回一个新的数组
      for (var i in this.data.arr) {
        if (this.data.arr[i] == checkValue) {
          this.data.arr.splice(i);
        }
      }
    }
    //打印当前所选中的数据
    console.log(this.data.arr);
  },
  // itemSelected: function (e) {
  //   var index = e.currentTarget.dataset.index;
  //   var item = this.data.itemList[index];
  //   item.isSelected = !item.isSelected;
  //   this.setData({
  //     itemList: this.data.itemList,
  //   });
  // },

  //标为已读
  setMessagesRead:function(e){
    var that = this;
    console.log(this.data.arr);
    var arr = this.data.arr;
    var msgIds = arr.join(',');
    console.log(msgIds);
    wx.request({
      url: setMessagesReadUrl,
      method: "POST",//默认GET
      data: {
        "msgIds": msgIds
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
          that.refresh();          
        } else {
          console.log(res.statusCode)
        }
      }, fail: function (res) {
        console.log("error");
      }
    })
  },

  //删除
  deleteMessages: function (e) {
    var  that = this;
    var arr = this.data.arr;
    var msgIds = arr.join(',');
    console.log(msgIds);
    wx.request({
      url: deleteMessagesUrl,
      method: "POST",//默认GET
      data: {
        "msgIds": msgIds
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
          that.refresh(); 
        } else {
          console.log(res.statusCode)
        }
      }, fail: function (res) {
        console.log("error");
      }
    })
  },

  //刷新
  refresh:function(){
    this.onLoad();
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