var globalApi = require("../../utils/apiUrl.js");
const app = getApp();
let signUp = globalApi.signUp
Page({
  data: {
    userName: '',
    password: ''
  },
  addUserName: function(e) {
    this.setData({
      userName: e.detail.value
    });
  },
  addUserPassword: function () {
    this.setData({
      password: e.detail.value
    });
  },
  signUp: function() {
    wx.request({
      url: signUp,
      data: {
        userName: this.data.userName,
        password: this.data.password
      },
      success: function (res) {
        console.log(res);
      }
    })
  }
})