const myexpress = require('express');
const app = myexpress();
const logger = require('morgan');
const router = require('./routers/dataRouter')
const bodyParser = require('body-parser');

// 配置日志
app.use(logger('dev'));
// 配置post解析
app.use(bodyParser.urlencoded({extended:false}));
// 将数据转换成json
app.use(bodyParser.json());
app.use(router); // 使用路由文件，可以调用多次配置多个路由文件

// 静态资源的路径
app.use(myexpress.static(__dirname+'/src'));

// 404错误时返回404提示信息
app.use(function(req, resp){
    resp.status(404);
    resp.send("Server: 404 Error");
})

app.listen(8000, ()=>{
    console.log('TT服务器启动');
})