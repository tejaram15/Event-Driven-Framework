var path = require('path');
var http = require('http');
var fs = require('fs');
var url = require('url');

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
});

server.listen(8080, function () {
    console.log('Listening on http://localhost:8080/');
});

/*var http = require('http');
var url = require('url');
var fs = require('fs');

console.log('HTTP Server Started');
http.createServer(function(req, res){
    console.log(req.url);
    var ctype;
    switch(req.url)
    {
        case "/logo%20(1).png":
            console.log("Fetching Photo");
            ctype = "img/png";
            break;
        case "/w3.css":
            ctype = "text/css";
            break;
        default:
            ctype = "text/html";
    }
    var q = url.parse(req.url,true);
    var filename = "."+q.pathname;
    fs.readFile(filename,function (err,data){
        if(err){
            res.writeHead(404,{'Content-Type':'text/html'});
            return res.end("404 Not Found");
        }
        res.writeHead(200,{'Content-Type':ctype});
        res.write(data);
        return res.end();
    });
}).listen(8080);
*/