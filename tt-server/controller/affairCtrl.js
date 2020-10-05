const affairDao = require('../dao/affairDao');

module.exports = {
    // GET
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
    getDoneAffairs(req, resp){
        console.log('路由getDoneAffairs成功');
        var email = req.query.email;

        affairDao.getAffairs({email: email, status: true}, function(err, docs){
            resp.send(docs);
        })
    },
    getOneAffair(req, resp){
        console.log('路由getOneAffair成功');
        var email = req.query.email;
        var affairID = req.query.affairID;

        affairDao.getOneAffair({email: email, affairID: affairID}, function(err, doc){
            resp.send(doc);
        })
    },
    deleteOneAffair(req, resp){
        console.log('路由deleteOneAffair成功');

        var email = req.query.email;
        var affairID = req.query.affairID;

        affairDao.deleteOneAffair({email: email, affairID: affairID}, function(err){
            resp.send('删除事务成功');
        })
    },


    // POST
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