/*
RestFul Service By NodeJS
Author : Ali Rahimi
 */

var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//connect to mysql
var con = mysql.createConnection({
    host: 'localhost',
    user: 'your username',
    password: 'your password',
    database: 'user'
});

function KeyHashPassword(user_password){
    var key = getRandomString(16);
    var password_data = SHA512(user_password, key);
    return password_data;
}
var getRandomString = function (length){
    return crypto.randomBytes(Math.ceil(length/2))
        .toString('hex')
        .slice(0, length);
};

var SHA512 = function (password, key){

    var hash = crypto.createHmac('SHA512', key)
        .update(password)
        .digest('hex');

    return {
        key: key,
        password_hash:hash

    };

};

function check_hash_password(user_password, key){
    var password_data = SHA512(user_password, key);
    return password_data;
}

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.post('/register/', (req, res, next) =>{
    var post_data = req.body;
    var user_code = post_data.user_code;
    var first_name = post_data.first_name;
    var last_name = post_data.last_name;
    var age = post_data.age;
    var gender = post_data.gender;
    var marital_status = post_data.marital_status;
    var education_degree = post_data.education_degree;
    var email = post_data.email;
    var raw_password = post_data.password;
    var hash_data = KeyHashPassword(raw_password);
    var password = hash_data.password_hash;
    var key = hash_data.key;



    con.query('SELECT * FROM user_table where email=?', [email], function (err, result, fields){
        con.on('error', function (err){
            console.log('[MYSQL ERROR]', err);
        });

        if (result && result.length)
            res.json('User already exists !!!');
        else {
           con.query('INSERT INTO `user_table`(`user_code`, `first_name`, `last_name`, `age`, `email`, `password`, `encrypted_key`, `education_degree`, `marital_status`, `gender`) VALUES (?,?,?,?,?,?,?,?,?,?)',
               [user_code, first_name, last_name, age, email, password, key, education_degree, marital_status, gender],
               function (err, result, fields){
                   con.on('error', function (err){
                       console.log('[MYSQL ERROR]', err);
                       res.json('Register error: ', err);
                   });
                   res.json('Register successful !!!');
               });

        }

    });

});

app.post('/login/', (req, res, next)=>{
    var post_data = req.body;
    var user_password = post_data.password;
    var email = post_data.email;


    con.query('SELECT * FROM user_table WHERE email=?', [email], function (err, result, fields){
        con.on('error', function (error){
            console.log('[mysql error] '+ error);
        });

        if (result && result.length){

            var key = result[0].encrypted_key;
            var password = result[0].password;

            var hashed_password = check_hash_password(user_password, key).password_hash;
            if (hashed_password == password){
                res.end(JSON.stringify('login successful ... !'));
            }else {
                res.end(JSON.stringify('wrong password ...!'));
            }

        }else {
            res.json('user not exist ... !');
        }

    });

});

app.post('/dashboard/', (req, res, next)=>{
    var post_data = req.body;
    var email = post_data.email;
    con.query('SELECT * FROM user_table WHERE email=?', [email], function (err, result, fields){
        con.on('error', function(error){
            console.log('[mysql error]', error);
        });
        if (result && result.length){
            res.end(JSON.stringify(result));
        }else{
            res.end((JSON.stringify("error in user dashboard ... !!!")))
        }
    })
})

app.listen(3000, ()=>{
    console.log("RestFul Service Running On Port 3000 !!!");
});