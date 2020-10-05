const mongoose = require('../config/conn_mongoose')

var Schema = mongoose.Schema;
var recordSchema = new Schema({
  email: String,
  signDate: {type: String, default: ""},
  signTimes: {type: Number, default: 0},
  shareTimes: {type: Number, default: 0},
});

const RecordModel = mongoose.model('record', recordSchema);

module.exports = RecordModel;