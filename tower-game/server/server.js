//to run use node server.js when in folder on command line

var http = require('http');
var server = http.createServer(function(req, res){
    res.end('hello');
});
server.listen(8001);
var io = require('socket.io').listen(server);
io.on('connection', function(socket){
	io.sockets.emit('newplayer', socket.id);
});