// 对数据库进行操作
const RecordModel = require('../models/Record')
module.exports = {
    // CRUD
    getOneSignDate(filter, callback){
        RecordModel.findOne(filter, function(err, res){
            callback(err, res);
        })
    },
    getOneSignTimes(filter, callback){
        RecordModel.findOne(filter, function(err, res){
            callback(err, res);
        })
    },
    getOneShareTimes(filter, callback){
        RecordModel.findOne(filter, function(err, res){
            callback(err, res);
        })
    },
    updateOneSignDate(filter, doc, callback){
        RecordModel.updateOne(filter, {$set:doc}, function(err){
            callback(err);
        })
    },
    updateSignTimes(filter, isInc, callback){
        if(isInc == true){
            RecordModel.updateOne(filter, {$inc: {"signTimes": 1}}, function(err){
                callback(err);
            })
        }else{
            RecordModel.updateOne(filter, {$set: {"signTimes": 1}}, function(err){
                callback(err);
            })
        }
    },
    updateShareTimes(filter, isInc, callback){
        if(isInc == true){
            RecordModel.updateOne(filter, {$inc: {"shareTimes": 1}}, function(err){
                callback(err);
            })
        }else{
            RecordModel.updateOne(filter, {$set: {"shareTimes": 1}}, function(err){
                callback(err);
            })
        }
    },
    initOneRecord(email, callback){
        RecordModel.create({
            email: email
        }, function(err){
            callback(err);
        });
    }
}