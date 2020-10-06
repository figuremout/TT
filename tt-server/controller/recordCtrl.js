const recordDao = require('../dao/recordDao');

module.exports = {
    // GET
    /**
     * @api {get} /getOneSignDate? 请求签到日期
     * @apiName getOneSignDate
     * @apiGroup Record
     *
     * @apiParam {String} email 用户邮箱
     *
     * @apiSuccess {String} res.signDate 该用户的最近签到日期
     */
    getOneSignDate(req, resp){
        console.log('路由getOneSignDate成功');

        var email = req.query.email;

        recordDao.getOneSignDate({email: email}, function(err, res){
            resp.send(res.signDate);
        })
    },
    /**
     * @api {get} /getOneSignTimes? 请求连续签到次数
     * @apiName getOneSignTimes
     * @apiGroup Record
     *
     * @apiParam {String} email 用户邮箱
     *
     * @apiSuccess {String} res.signTimes.toString() 该用户的连续签到次数
     */
    getOneSignTimes(req, resp){
        console.log('路由getOneSignTimes成功');

        var email = req.query.email;

        recordDao.getOneSignTimes({email: email}, function(err, res){
            console.log('test 1')
            resp.send(res.signTimes.toString()); // 不能直接send数字
        })
    },
    /**
     * @api {get} /getOneShareTimes? 请求分享次数
     * @apiName getOneShareTimes
     * @apiGroup Record
     *
     * @apiParam {String} email 用户邮箱
     *
     * @apiSuccess {String} res.shareTimes.toString() 该用户的分享次数
     */
    getOneShareTimes(req, resp){
        console.log('路由getOneShareTimes成功');

        var email = req.query.email;

        recordDao.getOneShareTimes({email: email}, function(err, res){
            resp.send(res.shareTimes.toString());
        })
    },

    // POST
    /**
     * @api {post} /initOneRecord 初始化一项记录
     * @apiName initOneRecord
     * @apiGroup Record
     *
     * @apiParam {String} email 用户邮箱
     *
     */
    initOneRecord(req, resp){
        console.log('路由initOneRecord成功');

        var email = req.body.email;

        recordDao.initOneRecord(email, function(err){
            resp.send('初始化record成功');
        })
    },
    /**
     * @api {post} /updateOneSignDate 更新签到日期
     * @apiName updateOneSignDate
     * @apiGroup Record
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} signDate 签到日期
     */
    updateOneSignDate(req, resp){
        console.log('路由updateOneSignDate成功');

        var email = req.body.email;
        var signDate = req.body.signDate;

        recordDao.updateOneSignDate({email: email}, {signDate: signDate}, function(err){
            resp.send('更新signDate成功');
        })
    },
    /**
     * @api {post} /updateSignTimes 更新签到次数
     * @apiName updateSignTimes
     * @apiGroup Record
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} isInc 是否签到次数增1
     *
     */
    updateSignTimes(req, resp){
        console.log('路由updateSignTimes成功');

        var email = req.body.email;
        var isInc = (req.body.isInc == 'true');

        recordDao.updateSignTimes({email: email}, isInc, function(err){
            console.log(err);
            resp.send('更新updateSignTimes成功');
        })
    },
    /**
     * @api {post} /updateShareTimes 更新分享次数
     * @apiName updateShareTimes
     * @apiGroup Record
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} isInc 是否分享次数增1
     *
     */
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