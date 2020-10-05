const recordDao = require('../dao/recordDao');

module.exports = {
    // GET
    getOneSignDate(req, resp){
        console.log('路由getOneSignDate成功');

        var email = req.query.email;

        recordDao.getOneSignDate({email: email}, function(err, res){
            resp.send(res.signDate);
        })
    },
    getOneSignTimes(req, resp){
        console.log('路由getOneSignTimes成功');

        var email = req.query.email;

        recordDao.getOneSignTimes({email: email}, function(err, res){
            console.log('test 1')
            resp.send(res.signTimes.toString()); // 不能直接send数字
        })
    },
    getOneShareTimes(req, resp){
        console.log('路由getOneShareTimes成功');

        var email = req.query.email;

        recordDao.getOneShareTimes({email: email}, function(err, res){
            resp.send(res.shareTimes.toString());
        })
    },

    // POST
    initOneRecord(req, resp){
        console.log('路由initOneRecord成功');

        var email = req.body.email;

        recordDao.initOneRecord(email, function(err){
            resp.send('初始化record成功');
        })
    },
    updateOneSignDate(req, resp){
        console.log('路由updateOneSignDate成功');

        var email = req.body.email;
        var signDate = req.body.signDate;

        recordDao.updateOneSignDate({email: email}, {signDate: signDate}, function(err){
            resp.send('更新signDate成功');
        })
    },
    updateSignTimes(req, resp){
        console.log('路由updateSignTimes成功');

        var email = req.body.email;
        var isInc = (req.body.isInc == 'true');

        recordDao.updateSignTimes({email: email}, isInc, function(err){
            console.log(err);
            resp.send('更新updateSignTimes成功');
        })
    },
    updateShareTimes(req, resp){
        console.log('路由updateShareTimes成功');

        var email = req.body.email;
        var isInc = req.body.isInc;

        recordDao.updateShareTimes({email: email}, isInc, function(err){
            console.log(err);
            resp.send('更新updateShareTimes成功');
        })
    }
}

// 如何获取值
// get请求方式 req.query.username方式获取 因为请求数据在url上呈现了
// post请求方式 用body-parser模块的req.body.username获取