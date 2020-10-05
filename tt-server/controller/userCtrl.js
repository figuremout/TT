const userDao = require('../dao/userDao');

module.exports = {
    // GET
    getUsername(req, resp){
        var email = req.query.email;

        userDao.getOneUser({email: email}, 'username', function(err, doc){
            resp.send(doc.username);
        })
    },


    // POST
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