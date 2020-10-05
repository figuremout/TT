const { clearLine } = require('readline');
// 对数据库进行操作
const UserModel = require('../models/User')
module.exports = {
    // CRUD
    addUser(username, email, pwd, callback){
        UserModel.create({
            username: username,
            email: email,
            pwd: pwd
          }, function(err){ // 必须要这样嵌套吗
            callback(err);
          })
    },
    updateUsername(email, new_username, callback){
        UserModel.updateOne({email:email}, {$set:{username:new_username}}, function(err){
            callback(err);
        })
    },
    isUserExist(filter, callback){
        UserModel.exists(filter, function(err, res){
            callback(err, res);
        })
    },
    getOneUser(filter, projection, callback){
        UserModel.findOne(filter, projection, function(err, res){
            callback(err, res);
        })
    }
}