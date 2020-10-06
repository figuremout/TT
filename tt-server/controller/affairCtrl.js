const affairDao = require('../dao/affairDao');

module.exports = {
    // GET
    /**
     * @api {get} /getAllAffairs? 请求所有事务
     * @apiName getAllAffairs
     * @apiGroup Affair
     *
     * @apiParam {String} email 用户邮箱
     *
     * @apiSuccess {String} docs 所有事务的JSONArray字符串形式
     */
    getAllAffairs(req, resp){
        console.log('路由getAllAffairs成功');
        var email = req.query.email;
        
        affairDao.getAffairs({email: email}, function(err, docs){
            if(!err){
                console.log("全部事务获取成功")
                resp.send(docs);
            }
        })
    },
    /**
     * @api {get} /getDoneAffairs? 请求所有已完成事务
     * @apiName getDoneAffairs
     * @apiGroup Affair
     *
     * @apiParam {String} email 用户邮箱
     *
     * @apiSuccess {String} docs 所有事务的JSONArray字符串形式
     */
    getDoneAffairs(req, resp){
        console.log('路由getDoneAffairs成功');
        var email = req.query.email;

        affairDao.getAffairs({email: email, status: true}, function(err, docs){
            resp.send(docs);
        })
    },
    /**
     * @api {get} /getOneAffair? 请求一件事务
     * @apiName getOneAffair
     * @apiGroup Affair
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} affairID 事务ID
     *
     * @apiSuccess {String} doc 该事务的JSONObject字符串形式
     */
    getOneAffair(req, resp){
        console.log('路由getOneAffair成功');
        var email = req.query.email;
        var affairID = req.query.affairID;

        affairDao.getOneAffair({email: email, affairID: affairID}, function(err, doc){
            resp.send(doc);
        })
    },
    /**
     * @api {get} /deleteOneAffair? 删除一件事务
     * @apiName deleteOneAffair
     * @apiGroup Affair
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} affairID 事务ID
     *
     */
    deleteOneAffair(req, resp){
        console.log('路由deleteOneAffair成功');

        var email = req.query.email;
        var affairID = req.query.affairID;

        affairDao.deleteOneAffair({email: email, affairID: affairID}, function(err){
            resp.send('删除事务成功');
        })
    },


    // POST
    /**
     * @api {post} /addOneAffair 增加一件事务
     * @apiName addOneAffair
     * @apiGroup Affair
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} affairID 事务ID
     * @apiParam {String} title 事务标题
     * @apiParam {String} content 事务内容
     * @apiParam {String} date 事务日期
     * @apiParam {Boolean} status 事务状态
     *
     */
    addOneAffair(req, resp){
        console.log('路由addOneAffair成功');
        var email = req.body.email;
        var affairID = req.body.affairID;
        var title = req.body.title;
        var content = req.body.content;
        var date = req.body.date;
        var status = req.body.status;

        affairDao.addOneAffair(email, affairID, title, content, date, status, function(err){
            if(!err){
                console.log('添加事务成功')
                resp.send('添加事务成功')
            }
        })
    },
    /**
     * @api {post} /updateOneAffair 更新一件事务
     * @apiName updateOneAffair
     * @apiGroup Affair
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} affairID 事务ID
     * @apiParam {String} title 事务标题（可选）
     * @apiParam {String} content 事务内容（可选）
     * @apiParam {Boolean} status 事务状态（可选）
     *
     */
    updateOneAffair(req, resp){
        console.log('路由updateOneAffair成功');

        var email = req.body.email;
        var affairID = req.body.affairID;
        var title = req.body.title;
        var content = req.body.content;
        var status = req.body.status;

        // 更新任意数值，无需传入全部参数
        var params = {title: title, content: content, status: status};
        var doc = {};
        for (var key in params){
            if(params[key]){
                doc[key] = params[key];
            }
        }

        affairDao.updateOneAffair({email: email, affairID: affairID}, doc, function(err){
            resp.send('更新事务成功');
        })
    }
}

// 如何获取值
// get请求方式 req.query.username方式获取 因为请求数据在url上呈现了
// post请求方式 用body-parser模块的req.body.username获取