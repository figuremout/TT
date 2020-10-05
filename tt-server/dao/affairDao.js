// 对数据库进行操作
const AffairModel = require('../models/Affair')
module.exports = {
    // CRUD
    addOneAffair(email, affairID, title, content, date, status, callback){
        AffairModel.create({
            email: email,
            affairID: affairID,
            title: title,
            content: content,
            date: date,
            status: status
          }, function(err){ // 必须要这样嵌套吗
              callback(err);
          })
    },
    getOneAffair(filter, callback){
        AffairModel.findOne(filter, function(err, res){
            callback(err, res);
        })
    },
    getAffairs(filter, callback){
        AffairModel.find(filter, null, null, function(err, res){
            callback(err, res);
        })
    },
    updateOneAffair(filter, doc, callback){
        AffairModel.updateOne(filter, {$set:doc}, function(err){
            callback(err);
        })
    },
    deleteOneAffair(filter, callback){
        AffairModel.deleteOne(filter, function(err){
            callback(err);
        })
    },
}