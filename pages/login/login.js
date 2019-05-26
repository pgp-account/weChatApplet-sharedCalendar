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
                      wx.switchTab({
                        url: '../index/index',
                      });
                    }
                    else {
                      wx.showToast({
                        title: '授权失败，请重新操作！',
                        duration: 2000,
                        icon: "success"
                      })
                    }
                  }
                },
                error: function (res) {
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
                    wx.switchTab({
                      url: '../index/index',
                    });
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

  //   //查看是否授权
  //   wx.getSetting({
  //     success: function (res) {
  //       if (res.authSetting['scope.userInfo']) {
  //         // 已经授权，可以直接调用 getUserInfo 获取头像昵称
  //         wx.getUserInfo({
  //           success: function (res) {
  //             console.log(res.userInfo);
  //             // wx.setStorageSync('nickName', res.userInfo.nickName);
  //             // wx.setStorageSync('avatarUrl', res.userInfo.avatarUrl);
  //             wx.request({
  //               url: 'http://39.96.188.220:8080/public/updateUserInfo',
  //               method: "POST",
  //               data: {
  //                 nickName: res.userInfo.nickName,
  //                 avatarUrl: res.userInfo.avatarUrl
  //               },
  //               header: {
  //                 'content-type': 'application/x-www-form-urlencoded'
  //               },
  //               success: function (res) {
  //                 // 登录成功
  //                 if (res.statusCode === 200) {
  //                   console.log(res.data.meta.message);
                    
  //                 }
  //               },
  //               error: function (res) {
  //                 console.log(123);
  //               }
  //             })
  //           }
  //         })
  //       }
  //     }
  //   })
   },
})
