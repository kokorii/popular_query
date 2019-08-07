/******************************************************
*drawingTool(map)
*map에 사용자가 직접 query window를 지정할 수 있도록
*drawingTool을 추가해준다.
*******************************************************/
function drawingTool(root, map){

	stroke = fill = randColor();
	
	 var drawingManager = new google.maps.drawing.DrawingManager({
		drawingControl: true,
		drawingControlOptions: {
		  position: google.maps.ControlPosition.TOP_CENTER,
		  drawingModes: [google.maps.drawing.OverlayType.RECTANGLE]
		},
		rectangleOptions:{
			strokeColor: stroke,
			strokeOpacity: 0.35,
			strokeWeight: 2,
			fillColor: fill,
			fillOpacity: 0.2,
			editable: false
		}
	 });
	 
	 drawingManager.setMap(map);
	 queryListener(root, drawingManager);
}

function queryResultDrawing(root){

	var map = reMap();
	
	drawingTool(root, map);
	
	var temp = new Array();
	var centerX = (queryXh + queryXl)/2;
	var centerY = (queryYh + queryYl)/2;
	
	var newCenter = reCentering(centerX, centerY);
	map.setCenter(newCenter);
	
	stroke = fill = randColor();
	
	for(var i=0; i<queryResult.length; i++){
		var mbr = getMyMBR(queryResult, i); 
		temp[i] = drawRectangle(map, mbr, stroke, fill);
	}
}
function setQueryWindowInfo(queryWindow){

//lat -> 위도 , long -> 경도
	 HighCoord = queryWindow.getBounds().getNorthEast();
	 queryXh = HighCoord.lat();
	 queryYh = HighCoord.lng();
	
	 LowCoord = queryWindow.getBounds().getSouthWest();
	 queryXl = LowCoord.lat();
	 queryYl = LowCoord.lng();
	
}
/******************************************************
*queryListener(drawingManager)
*사용자가 그린 사각형을 query window로 지정하고 
*query window에 해당하는 MBR search를 시작한다.
*******************************************************/
function queryListener(root, drawingManager){

	 google.maps.event.addListener(drawingManager, 'overlaycomplete', function(e) {
            
		//새로운 사각형을 그리고 마우스를 손가락 선택도구(기본)로 변경한다.
		//아래 구문이 없다면 그대로 계속 사각형을 그리는 모양의 십자 포인터가 유지된다.
		drawingManager.setDrawingMode(null);

		//query window: 사용자가 새로 그린 사각형 
		queryWindow = e.overlay;
		queryWindow.type = e.type;
		
		setQueryWindowInfo(queryWindow);
		
		google.maps.event.addListener(queryWindow, 'click', search(root, queryWindow));
		
		queryResultDrawing(root);
    });

}
