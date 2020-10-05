const mongoose = require('../config/conn_mongoose')

var Schema = mongoose.Schema;
var affairSchema = new Schema({
  email: String,
  affairID: String,
  title: String,
  content: {type: String, default: ""},
  date: String,
  status: {type: Boolean, default: false}
});

const AffairModel = mongoose.model('affair', affairSchema);

module.exports = AffairModel;