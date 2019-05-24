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
      url: 'http://39.96.188.220:8080/public/signUp',
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