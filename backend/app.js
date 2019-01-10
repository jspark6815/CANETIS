const WebSocket = require("ws")
const firebase = require("firebase")

const config = {
	apiKey: "AIzaSyDqpN9M0OWICMxWLiervQkhe5KjlAvmGwc",
	authDomain: "white-stick.firebaseapp.com",
	databaseURL: "https://white-stick.firebaseio.com",
	storageBucket: "white-stick.appspot.com"
}
firebase.initializeApp(config)
var database = firebase.database();
const ws = new WebSocket.Server({
	port: 8080
})

/*const ref = database.ref('data')
ref.on('child_added', (snapshot) => {
	const v = snapshot.val()
	console.log("added " + v.code + " / " + v.name)
})
ref.on('child_changed', (snapshot) => {
	const v = snapshot.val()
	console.log("changed " + v.code + " / " + v.name)
})*/

function heartbeat() {
  this.isAlive = true
}

const interval = setInterval(() => {
  ws.clients.forEach(socket => {
    if (!socket.isAlive) return socket.terminate();

    socket.isAlive = false;
    socket.ping(() => {});
  });
}, 5000);

ws.on('connection', (socket, req) => {

  socket.isAlive = true;
  socket.on('pong', heartbeat);

	const key = req.url.slice(1)
	var isAlive = false
	var init = false
	var ref = database.ref('data/' + key)
	ref.update({ip: req.connection.remoteAddress, connectionTime: +new Date(), status: true})
	console.log(key + "] SOCKET CONNECTED")
	ref.on('value', (snapshot) => {
		process.stdout.write(key + "] Received data -> ");
		const v = snapshot.val()
		if (!isAlive && !init) {
			process.stdout.write("connected, ")
			init = true
			if(v){
				isAlive = true
				if(v.isDanger) console.log("device is DANGER"), socket.send(0x01), ref.update({isCalled: false, connectionTime: +new Date()})
				else console.log("device is NORMAL"), socket.send(0x00), v.isCalled && (socket.send(0x10), v.callNum++, ref.update({isCalled: false, callNum: v.callNum}))
			} else console.log("device is not registered"), socket.send(0x0F)
		} else if (isAlive && v.isCalled) {
			process.stdout.write("called, ")
			socket.send(0x10)
			v.callNum++
			console.log("call device / " + v.callNum)
			ref.update({isCalled: false, callNum: v.callNum})
		}else if(isAlive){
			console.log("just change");
		}else{
			console.log("closed;;");
		}
	})
	socket.on('error', (err) => {
		console.log(key + "] SOCKET ERROR / " + err)
	})
	socket.on('close', (code) => {
		isAlive = false
		console.log(key + "] SOCKET CLOSE / " + code)
		ref.update({status: false, connectionTime: +new Date()})
		ref.off()
		ref = undefined
		socket = undefined
	})
	socket.on('message', (msg) => {
		console.log(key + "] SOCKET MESSAGE / " + msg)
	})
})
ws.on('error', (err) => {

})
