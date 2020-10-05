const express = require('express');
const router = express.Router();
const userCtrl = require('../controller/userCtrl');
const affairCtrl = require('../controller/affairCtrl');
const recordCtrl = require('../controller/recordCtrl');

router.get('/getAllAffairs', affairCtrl.getAllAffairs);
router.get('/getDoneAffairs', affairCtrl.getDoneAffairs);
router.get('/getOneAffair', affairCtrl.getOneAffair);
router.get('/deleteOneAffair', affairCtrl.deleteOneAffair);
router.get('/getUsername', userCtrl.getUsername);
router.get('/getOneSignDate', recordCtrl.getOneSignDate);
router.get('/getOneSignTimes', recordCtrl.getOneSignTimes);
router.get('/getOneShareTimes', recordCtrl.getOneShareTimes);

router.post('/login', userCtrl.userLogin); // 会默认传递req resp进函数
router.post('/register', userCtrl.userRegister);
router.post('/updateUsername', userCtrl.updateUsername);
router.post('/addOneAffair', affairCtrl.addOneAffair);
router.post('/updateOneAffair', affairCtrl.updateOneAffair);
router.post('/initOneRecord', recordCtrl.initOneRecord);
router.post('/updateOneSignDate', recordCtrl.updateOneSignDate);
router.post('/updateSignTimes', recordCtrl.updateSignTimes);
router.post('/updateShareTimes', recordCtrl.updateShareTimes);


module.exports = router;