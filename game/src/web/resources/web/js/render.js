var map;
var cell = 32;
var mini = 2;
var _x, _y;
var renderIntercal;
var repaint = true;
function loadMap(context) {
	console.log(context['basic'])
	map = context['basic']

	startRender()
}

function getAMap() {
	$.ajax({
		url : '/resource/maps',
		success : loadMap
	});
}

function startRender() {
	_x = 0;
	_y = 0;
	resize()
	renderIntercal = setInterval(renderMap, 100);
}

function renderMap() {
	var i = 0;
	var j = 0;

	var canvas = document.getElementById('canvas');

	if (repaint) {
		var ctx = canvas.getContext('2d');
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		repaint = false;
	}
	renderMapTiles();
	
	renderMinimap();
}

function renderMapTiles(){
	for (j = 0; j < map['tiles'].length; j++) {
		for (i = 0; i < map['tiles'][j].length; i++) {
			var tile = map['tiles'][j][i];

			if (canvas.getContext) {
				if (tile['normal'] != null) {
					drawImage(tile, i, j, _x, _y);
				} else {
					drawColor(tile, i, j, _x, _y);
				}
			}
		}
	}
}

function renderMinimap(){
	for (j = 0; j < map['tiles'].length; j++) {
		for (i = 0; i < map['tiles'][j].length; i++) {
			var tile = map['tiles'][j][i];

			if (canvas.getContext) {
				if (tile['avgColor'] != null) {
					var ctx = canvas.getContext('2d');
					ctx.fillStyle = tile['avgColor'];
					ctx.fillRect(i * mini, j * mini, mini, mini);
				}
			}
		}
	}
}

function resize() {
	var canvas = document.getElementById('canvas');
	var ctx = canvas.getContext('2d');
	ctx.canvas.width = $('#gameMap').innerWidth();
	ctx.canvas.height = $('#gameMap').innerHeight();
}

function drawImage(tile, i, j, _x, _y) {
	var ctx = canvas.getContext('2d');

	var img = new Image();
	img.onload = function() {
		ctx.drawImage(img, i * cell + _x, j * cell + _y, cell, cell);
	};
	img.onerror = function() {
		drawColor(tile, i, j, _x, _y)
	};
	img.src = '/resources/' + tile['normal'];
}

function drawColor(tile, i, j, _x, _y) {
	if (tile['avgColor'] != null) {
		var ctx = canvas.getContext('2d');
		ctx.fillStyle = tile['avgColor'];
		ctx.fillRect(i * cell + _x, j * cell + _y, cell, cell);
	}
}

var lastX = null, lastY = null;
function dragMap(event) {

	if (lastX != null && event.x > 0) {
		_x = _x - lastX + event.x;
		repaint = true;
	}

	if (lastY != null && event.y > 0) {
		_y = _y - lastY + event.y;
		repaint = true;
	}

	lastX = event.x;
	lastY = event.y;
	console.log(event)
}

function dragStartMap(event) {
	var canvas = document.createElementNS("http://www.w3.org/1999/xhtml",
			"canvas");
	canvas.width = canvas.height = 1;
	var ctx = canvas.getContext("2d");
	event.dataTransfer.setDragImage(canvas, 0, 0);

	lastX = event.x;
	lastY = event.y;
}

function dragendMap(event) {
	console.log(event)

	lastX = event.x;
	lastY = event.y;
}