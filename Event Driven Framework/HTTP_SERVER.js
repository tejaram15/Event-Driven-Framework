const path = require('path');
const http = require('http');
const fs = require('fs');
const url = require('url');
const exec = require('child_process').exec;

var dir = path.join(__dirname, 'public');

var mime = {
    html: 'text/html',
    txt: 'text/plain',
    css: 'text/css',
    gif: 'image/gif',
    jpg: 'image/jpeg',
    png: 'image/png',
    svg: 'image/svg+xml',
    js: 'application/javascript'
};

var server = http.createServer(function (req, res) {
    if(req.method == "POST"){
        console.log("POST");
        var body = '';
        var result = '';
        res.writeHead(200, {'Content-Type': 'text/html'});
        req.on('data', function (data) {
            body += data;
            //Show for Debugging
            //console.log("Body: " + body);
            var str = body;
            if (str.charAt(0) === '"' && str.charAt(str.length -1) === '"')
            {
                //console.log(str.substr(1,str.length -2));
                body = str.substr(1,str.length -2);
            }            
            result = body.split(",");
            console.log(result);
            if(result[0]=="signup"){
                var username = result[1];
                var pass = result[2];
                var check = "curl localhost:9090/query -sS -XPOST -d '" + "{me(func:allofterms(username,\"" + username + "\")){username _uid_}" + "}\'";
                exec(check, function(error, stdout, stderr) {
                    console.log(stdout);
                    if(stdout=="{}"){
                        console.log("Ready to Signup!!");
                        var query = "curl localhost:9090/query -sS -XPOST -d '";
                        query += "mutation{set{_:" + username + " <username> \"" + username + "\" .";
                        query += "_:" + username + " <password> \"" + pass + "\" .";
                        query += "}schema{username: string @index(term) .}}'";
                        //console.log(query);
                        exec(query, function(error, stdout, stderr) {
                          // command output is in stdout
                            console.log(stdout);
                            var obj = JSON.parse(stdout);
                            if(obj["code"]=="Success")
                                res.end("ok");
                            else res.end("notok");
                        });                                        
                    }
                    else res.end("existing");
                });
            }
            else if(result[0]=="login"){
                var username = result[1];
                var pass = result[2];
                var query = "curl localhost:9090/query -sS -XPOST -d '" + "{log(func:allofterms(username,\"" + username + "\")){username password}" + "}\'";
                exec(query, function(error, stdout, stderr) {
                  // command output is in stdout
                    console.log(stdout);
                    if(stdout!="{}")
                        res.end("match");
                });                
            }
            else if(result[0]=="query"){
                var start_date = result[1];
                var end_date = result[2];
                console.log(start_date);
                console.log(end_date);
                var query = "curl localhost:9090/query -sS -XPOST -d '{ me(func:allofterms(name,\"Vijaya Bank\")){ associates(orderasc:start_date) @filter(ge(start_date,\""+start_date+"\") and le(start_date,\""+end_date+"\")){start_date percent_paid paid } } }'";
                //console.log(query);
                exec(query, function(error, stdout, stderr) {
                  // command output is in stdout
                    console.log(stdout);
                    if(stdout!="{}"){
                        res.end("Query Successful!!");
                        fs.writeFile("Resources/Data/send.json", stdout, function(err) {
                            if(err) {
                                return console.log(err);
                            }
                            console.log("The file was saved Successfully!");
                        });                         
                    }
                });                
            }
            else if(result[0]=="check_conn"){
                console.log("Checking Database Connection.....");
                 var check = "curl localhost:9090/query -sS -XPOST -d '" + "{me(func:allofterms(username,\"" + "123" + "\")){username _uid_}" + "}\'";
                  exec(check, function(error, stdout, stderr) {
                    if(stderr=="" && error==null){
                        console.log("Connection Ok...!!");
                        res.end("ok");
                    }
                    else{
                        console.log("Connection Not Ok...");
                        res.end("not ok");
                    }
                });             
            }
        });        
    }
    else{
        var q = url.parse(req.url,true);
        var filename = "."+q.pathname;
        var type = mime[path.extname(filename).slice(1)] || 'text/plain';
        fs.readFile(filename,function (err,data){
            if(err){
                res.writeHead(404,{'Content-Type':'text/html'});
                return res.end("404 Not Found");
            }
            res.writeHead(200,{'Content-Type':type});
            res.write(data);
            return res.end();
        });
    }
});

server.listen(8080, function () {
    console.log('Listening on http://localhost:8080/');
});