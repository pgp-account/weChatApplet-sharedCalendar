var app = getApp();
var globalApi = require("./apiUrl.js");
let getMessagesUrl = globalApi.getMessagesUrl
function getMessageNum () {
  let that = this;
  var num = 0;
  var massageNum = null;
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
        for (var j = 0; j < item.length; j++) {
          if (item[j].isRead == 0) {
            num++;
          }
        }
        //this.globalData.openid = num;
        //that.setData2num(num);
        console.log(num);
        app.globalData.messageNum = num;
        if (num != 0) {
          wx.showTabBarRedDot({
            index: 3
          })
        } else {
          wx.hideTabBarRedDot({
            index: 3
          })
        }
        
      } else {
        console.log(res.statusCode)
      }

    }, fail: function (res) {
      console.log("error");
    }
  })
  // console.log(massageNum);
  // return massageNum;
  
}
module.exports = {  //把方法共享，让引用的地方可以调用
  getMessageNum: getMessageNum
}