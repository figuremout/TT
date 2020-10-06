const userDao = require('../dao/userDao');

module.exports = {
    // GET
    /**
     * @api {get} /getUsername? 请求用户名
     * @apiName getUsername
     * @apiGroup User
     *
     * @apiParam {String} email 用户邮箱
     *
     * @apiSuccess {String} doc.username 该用户的用户名
     */
    getUsername(req, resp){
        var email = req.query.email;

        userDao.getOneUser({email: email}, 'username', function(err, doc){
            resp.send(doc.username);
        })
    },


    // POST
    /**
     * @api {post} /userLogin 用户登录
     * @apiName userLogin
     * @apiGroup User
     *
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} pwd 用户密码
     * 
     * @apiSuccess {String} isSuccess 是否成功
     */
    userLogin(req, resp){
        console.log('路由userLogin成功');

        var email = req.body.email;
        var pwd = req.body.pwd;
        console.log(req.body);

        userDao.isUserExist({email:email, pwd:pwd}, function(err, isExist){
            if(isExist){
                resp.send(true);
            }else{
                resp.send(false);
            }
        })
    },
    /**
     * @api {post} /userRegister 用户注册
     * @apiName userRegister
     * @apiGroup User
     *
     * @apiParam {String} username 用户名
     * @apiParam {String} email 用户邮箱
     * @apiParam {String} pwd 用户密码
     * 
     * @apiSuccess {String} isSuccess 是否成功
     */
    userRegister(req, resp){
        console.log("路由userRegister成功");

        var username = req.body.username;
        var email = req.body.email;
        var pwd = req.body.pwd;

        userDao.isUserExist({email:email}, function(err, isExist){
            if(isExist){
                // 用户已存在
                resp.send(false);
            }else{
                // 用户不存在
                userDao.addUser(username, email, pwd, function(err){
                    if(!err){
                        console.log("用户插入成功");
                        resp.send(true);
                    }
                });
            }
        })
    },
    /**
     * @api {post} /updateUsername 更新用户名
     * @apiName updateUsername
     * @apiGroup User
     *
     * @apiParam {String} username 用户名
     * @apiParam {String} email 用户邮箱
     * 
     */
    updateUsername(req, resp){
        console.log("路由updateUsername成功");

        var username = req.body.username;
        var email = req.body.email;

        userDao.updateUsername(email, username, function(err){
            if(!err){
                console.log("用户名修改成功");
                resp.send("用户名修改成功");
              }
        })
    }
}

// 如何获取值
// get请求方式 req.query.username方式获取 因为请求数据在url上呈现了
// post请求方式 用body-parser模块的req.body.username获取