/**********************************************************************
*reMap()
*LEVEL을 거듭할 수록(RECTANGLE을 클릭 할때마다) map을 새로 지정한다.
***********************************************************************/
function reMap(){

	var mapOption = {
		zoom: zoomLevel,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};

	return new google.maps.Map(document.getElementById('map-canvas'), mapOption);
}

/**********************************************************************
*getRootMBR(i)
*페이지가 처음 로드되었을 때 지도 위에는 
*ROOT NODE의 Entry 개수만큼 Rectangle이 그려져야 한다.
*getRootMBR은 ROOT NODE 각각의 entry에 해당하는 사각형의 정보를 
*반환해주는 사각형이다.
*이때 i는 entry의 index로 
*getRootMBR(i)함수는 i번째 entry의 MBR 정보를 반환해주게 된다.
***********************************************************************/
function getMyMBR(tree, i){
	return new google.maps.LatLngBounds(
			      new google.maps.LatLng(tree[i].Xl, tree[i].Yl),
			      new google.maps.LatLng(tree[i].Xh, tree[i].Yh));
}

function getMyMIR(tree, i){
	return new google.maps.LatLngBounds(
		      new google.maps.LatLng(tree[i].mXl, tree[i].mYl),
		      new google.maps.LatLng(tree[i].mXh, tree[i].mYh));
}
/**********************************************************************
*getMBR(i, j)
*Root Node의 i번째 entry에 해당하는 사각형이 클릭되었을 때, 
*root node에 속한 i 번째 entry의 child node 중 
*j번째 entry 의 사각형 정보를 반환한다. 
***********************************************************************/
function getChildMBR(Data, j){
 return new google.maps.LatLngBounds(
					  new google.maps.LatLng(Data.child[j].Xl, Data.child[j].Yl),
					  new google.maps.LatLng(Data.child[j].Xh, Data.child[j].Yh));
}

function getChildMIR(Data, j){
	return new google.maps.LatLngBounds(
			  new google.maps.LatLng(Data.child[j].mXl, Data.child[j].mYl),
			  new google.maps.LatLng(Data.child[j].mXh, Data.child[j].mYh));	
}

/**********************************************************************
*drawRectangle(map, mbr, stroke, fill)
*bound가 mbr만큼인 사각형을 map에 그려준다.
*이때 storke, fill은 random하게 정의된 사각형의 색깔 정보를 나타낸다.
*******************************************j****************************/
function drawRectangle(map, mbr, stroke, fill){
	return new google.maps.Rectangle({
			strokeColor: stroke,
			strokeOpacity: 0.35,
			strokeWeight: 2,
			fillColor: fill,
			fillOpacity: 0.24,
			draggable: true,
			map: map,
			bounds: mbr
		});
}

function drawMBR(map, mbr, stroke, fill){
	return new google.maps.Rectangle({
			strokeColor: stroke,
			strokeOpacity: 0.35,
			strokeWeight: 0,
			fillColor: fill,
			fillOpacity: 0.5,
			draggable: true,
			map: map,
			bounds: mbr
		});
}

function drawMIR(map, mir, stroke, fill){
	return new google.maps.Rectangle({
			strokeColor: stroke,
			strokeOpacity: 0.8,
			strokeWeight: 5,
			fillColor: fill,
			fillOpacity: 1.0,
			draggable: true,
			map: map,
			bounds: mir
		});
}

function reCentering(x, y){
	return  new google.maps.LatLng(x, y);
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
	
	//origin source code - getRandNum(256)
	red = getRandNum(100).toString(16);
	green = getRandNum(100).toString(16);
	blue = getRandNum(100).toString(16);
	
	if(red.length < 2) 	red = "0" + red;	
	if(red.length < 2) 	green = "0" + green;
	if(red.length < 2) 	blue = "0" + blue;
	
	rgbHexadecimal = "#" + red + green + blue;
	
	return rgbHexadecimal;
}
