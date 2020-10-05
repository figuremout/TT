const mongoose = require('../config/conn_mongoose')

var Schema = mongoose.Schema;
var userSchema = new Schema({
  username: String,
  email: String,
  pwd: String
});

const UserModel = mongoose.model('user',// 注意这里集合名会自动变为users
  userSchema
);

module.exports = UserModel;