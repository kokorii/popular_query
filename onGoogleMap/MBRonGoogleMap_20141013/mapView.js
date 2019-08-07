//global variable declaration
var NUM_OF_DATA = 5613;
var maxLevel = 1000;
var data = JSON.parse(HilbertTree);

var map, mapOption;
var stroke, fill;

var zoomLevel = 7;

var LEVELDATA = new Array();
var levelCount = new Array();
var rectangle = new Array();


function initialize() {

	//최초 지도의 속성정보(option) assign
	mapOption = {
		zoom: 7,
		center: new google.maps.LatLng(36.314141, 127.992776),	//seoul
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	
	maxLevel = findMaxLevel();
	
	
	//가장 최 상위 레벨부터 MBR 그리기
	showMBR(maxLevel);

}

function countEntry(){

	var clevel = maxLevel;
	
	for(var i=0; i<maxLevel; i++){
		//alert("clevel: " + clevel);
		
		if(clevel < 0){
			//return;
		}
			
		levelCount[clevel]=0;
		
		for(var j=0; j<NUM_OF_DATA; j++){
		
			if(data[j].level == clevel)
				levelCount[clevel]++;
		}
		//alert("levelCount[clevel] : " +levelCount[clevel]);
		clevel--;
	}
}

function readDATA(){
	for(var i=0; i<NUM_OF_DATA; i++){
		
	}
}
function showMBR(currentLevel) {

	//leaf node에 도달하면 그 다음 보여줄 MBR LEVEL이 없으므로 알림창을 띄워주고 nexLevel 함수를 종료함(return).
	if(currentLevel < 0){
		alert("this is leaf node Rectangle\n");
		return;
	}
	
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOption);
	
	stroke = fill = randColor();
	
	
	for(var i=0; i<NUM_OF_DATA; i++){			
		
		if(data[i].level == currentLevel){
			var mbr = getMBR(i);
			drawingMBR(i, mbr);	//투명하게 다 그려둠
			addListener(i, currentLevel);
		}	
	}
}

function getMBR(i){
	return new google.maps.LatLngBounds(
			      new google.maps.LatLng(data[i].LX, data[i].LY),
			      new google.maps.LatLng(data[i].UX, data[i].UY));
}

function addListener(i, level){

	google.maps.event.addListener(
			rectangle[i], 'click', function(){ 
												showMBR(level-1);
												map.setZoom(zoomLevel);
											 }
										);
}

function drawingMBR(i, mbr){

	rectangle[i] = new google.maps.Rectangle({
				strokeColor: stroke,
				strokeOpacity: 0.35,
				strokeWeight: 2,
				fillColor: fill,
				fillOpacity: 0.2,
				map: map,
				bounds: mbr,
		    });
		
}

//RGB 색상값 랜덤 생성을 위한 함수
//function getRandNum, function randColor
function getRandNum(value) {
	return Math.floor(Math.random() * (value + 1));	
}

function randColor() {
	var rgbHexadecimal = 0;

	var red = 0;
	var green = 0;
	var blue = 0;
	
	red = getRandNum(255).toString(16);
	green = getRandNum(255).toString(16);
	blue = getRandNum(255).toString(16);
	
	if(red.length < 2) 	red = "0" + red;	
	if(red.length < 2) 	green = "0" + green;
	if(red.length < 2) 	blue = "0" + blue;
	
	rgbHexadecimal = "#" + red + green + blue;
	
	return rgbHexadecimal;
}

//입력으로 들어오는 트리의 max level을 찾아 주는 함수
function findMaxLevel(){
	var max=0;
	var loop = NUM_OF_DATA;
	
	while(loop--){
	
		if(max < data[loop].level){
			max = data[loop].level;
		}
	}
	
	return max;
}

google.maps.event.addDomListener(window, 'load', initialize);