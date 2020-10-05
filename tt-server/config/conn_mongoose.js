const mongoose = require('mongoose');
const url = 'mongodb://admin:adminpwd@127.0.0.1:27017/tt?authSource=admin';

const dbConfig = {
    useNewUrlParser: true, 
    useUnifiedTopology: true
}

// 连接数据库
mongoose.connect(url, dbConfig).catch(
  err => {
    if(err){
      console.log("数据库连接失败：", err)
    }}
);
// 数据库连接成功事件
mongoose.connection.once("open",function(){
    console.log("数据库已连接");
});
// 数据库断开事件
mongoose.connection.once("close",function(){
    console.log("数据库连接已断开");
});

module.exports = mongoose;