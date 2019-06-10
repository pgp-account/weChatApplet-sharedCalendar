var globalApi = require("../../utils/apiUrl.js");
const app = getApp();
let signIn = globalApi.signIn
let updateUserInfo = globalApi.updateUserInfo
Page({
  data: {
    //判断小程序的API，回调，参数，组件等是否在当前版本可用。
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  tapLogin: function () {
    wx.login({
      success: function (res) {
        if (res.code) {
          console.log(res.code);
          let code = res.code;
          wx.getUserInfo({
            success: function (res) {
              console.log(res.userInfo);
              wx.showLoading({
                title: '登录中',
              })
              // wx.setStorageSync('nickName', res.userInfo.nickName);
              // wx.setStorageSync('avatarUrl', res.userInfo.avatarUrl);
              wx.request({
                url: signIn,
                method: "POST",
                data: {
                  rescode: code
                },
                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                success: function (res) {
                  console.log(res);
                  // 登录成功
                  if (res.statusCode === 200) {
                    console.log(res.data);

                    if (res.data.meta.code === "001") {
                      console.log(res.data.data.session_id)// 服务器回包内容
                      wx.setStorageSync('session_id', res.data.data.session_id);
                      // 获取用户信息
                      //跳转到TabBar中要使用wx.switchTab
                      wx.hideLoading();
                      wx.switchTab({
                        url: '../index/index',
                      });
                    }
                    else {
                      wx.hideLoading();
                      wx.showToast({
                        title: '授权失败，请重新操作！',
                        duration: 2000,
                        icon: "success"
                      })
                    }
                  }
                },
                error: function (res) {
                  wx.hideLoading();
                  console.log(123);
                }
              }),
              wx.request({
                url: updateUserInfo,
                method: "POST",
                data: {
                  nickName: res.userInfo.nickName,
                  avatarUrl: res.userInfo.avatarUrl
                },
                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                success: function (res) {
                  // 更新用户信息成功
                  if (res.statusCode === 200) {
                    console.log(res.data.meta.message);
                    // wx.switchTab({
                    //   url: '../index/index',
                    // });
                  }
                },
                error: function (res) {
                  console.log(123);
                }
              })
            }
          })
          
        } 
        else {
          console.log('获取用户登录态失败！' + res.errMsg)
        }
      }
    })
  },
  onLoad: function () {
  },
})
