//global variable declaration
var NUM_OF_DATA = 6;
var NUM_OF_PACK =  10;
var maxLevel = 1000;

var data = JSON.parse(HilbertPackTree);

var map, mapOption;
var stroke, fill;

var zoomLevel = 7;

var LEVELDATA = new Array();
var levelCount = new Array();
var rectangle = new Array();
var rect = new Array();


function initialize() {

	//최초 지도의 속성정보(option) assign
	mapOption = {
		zoom: zoomLevel,
		center: new google.maps.LatLng(36.314141, 127.992776),	//seoul
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};

	showStart();

}

function reMap(){
	return new google.maps.Map(document.getElementById('map-canvas'), mapOption);
}

function getRootMBR(i){
	return new google.maps.LatLngBounds(
			      new google.maps.LatLng(data[i].LX, data[i].LY),
			      new google.maps.LatLng(data[i].UX, data[i].UY));
}

function getMBR(i, j){
	return new google.maps.LatLngBounds(
					  new google.maps.LatLng(data[i].child[j].LX, data[i].child[j].LY),
					  new google.maps.LatLng(data[i].child[j].UX, data[i].child[j].UY));
}

function drawRectangle(map, mbr, stroke, fill){
	return new google.maps.Rectangle({
			strokeColor: stroke,
			strokeOpacity: 0.35,
			strokeWeight: 2,
			fillColor: fill,
			fillOpacity: 0.2,
			map: map,
			bounds: mbr
		});
}
function showStart() {

	map = reMap();
	
	stroke = fill = randColor();
	
	for(var i=0; i<NUM_OF_DATA; i++){			
	
		var mbr = getRootMBR(i);

		rectangle[i] = drawRectangle(map, mbr, stroke, fill);
		
		firstListner(i);
		
	}
}

function firstListner(i){
	
	google.maps.event.addListener(rectangle[i], 'click', 
			
			function(){ 
				
				map = reMap();
				
				var newCenter = new google.maps.LatLng(((data[i].LX+data[i].UX)/2), ((data[i].LY+data[i].UY)/2));
				map.setCenter(newCenter);
				
				zoomLevel = zoomLevel + 1;
				map.setZoom(zoomLevel);
				
				stroke = fill = randColor();

				for(var j=0; j<NUM_OF_PACK; j++){
					
					var mbr = getMBR(i, j);
					  
					rect[j] = drawRectangle(map, mbr, stroke, fill);
					
					var cData = data[i].child[j];
					addListener(cData, j);
					
				}								
			});
}

function addListener(Data, i){
	
	google.maps.event.addListener(rect[i], 'click', 
		
			function(){ 
			
				if(Data.level <= 0){
					alert("this is leaf node");
					return
				}
				
				map = reMap();
				
				var newCenter = new google.maps.LatLng(((Data.LX+Data.UX)/2), ((Data.LY+Data.UY)/2));
				map.setCenter(newCenter);
				
				zoomLevel = zoomLevel + 1;
				map.setZoom(zoomLevel);
				
				stroke = fill = randColor();

				for(var j=0; j<NUM_OF_PACK; j++){
					
					var mbr = new google.maps.LatLngBounds(
					  new google.maps.LatLng(Data.child[j].LX, Data.child[j].LY),
					  new google.maps.LatLng(Data.child[j].UX, Data.child[j].UY));
					  
					rect[j] = new google.maps.Rectangle({
						strokeColor: stroke,
						strokeOpacity: 0.35,
						strokeWeight: 2,
						fillColor: fill,
						fillOpacity: 0.2,
						map: map,
						bounds: mbr
					});
					
					var cData = Data.child[j];
					addListener(cData, j);
				}								
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

google.maps.event.addDomListener(window, 'load', initialize);