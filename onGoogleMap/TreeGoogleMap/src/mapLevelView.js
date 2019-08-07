
/**********************************************************************
*initialize():
*페이지를 최초에 로드했을 때 실행되는 함수
***********************************************************************/
function initialize() {

	var tree = JSON.parse(list);
	//HilbertPackTree
	showStart(tree);
}

/**********************************************************************
*showStart()
*지도위에 사각형을 보여주는 작업을 시작하는 함수이다.
*Root Node가 가지고있는 entry의 수만큼 rectangle 배열에 저장하고 그린다.
*저장된 rectangle들에는 event handler를 달아서
*click 된  사각형의 child node entry들을 display할 수 있도록 한다.
***********************************************************************/
function showStart(tree) {

	var seoul = new google.maps.LatLng(36.314141, 127.992776);
	
	var map = reMap();
	map.setCenter(seoul);
	
	drawingTool(tree, map);	  
	
	stroke = fill = randColor();
	
	var rectangle = new Array();

	for(var i=0; i<tree.length; i++){			
	
		var mbr = getMyMBR(tree, i);

		rectangle[i] = drawRectangle(map, mbr, stroke, fill);
		
		var cData = tree[i];
		
		Listener(cData, rectangle[i]);
		
	}
}

function Listener(Data, clickedRect){

	google.maps.event.addListener(clickedRect, 'click', 
			
			function(){ 
				
				if(Data.level <= 1){
					alert("this is leaf node\n");
					return;
				}
				
				var map = reMap();	

				var centerX = ((Data.Xl+Data.Xh)/2);
				var centerY = ((Data.Yl+Data.Yh)/2);
				var newCenter = reCentering(centerX, centerY);

				map.setCenter(newCenter);
				
				drawingTool(Data.child, map);
				
				zoomLevel = zoomLevel + 1;
				
				map.setZoom(zoomLevel);
				
				stroke = fill = randColor();
				
				var rect = new Array();

				for(var j=0; j<Data.child.length; j++){
					
					var mbr = getChildMBR(Data, j);
					 
					rect[j] = drawRectangle(map, mbr, stroke, fill);
					
					var cData = Data.child[j];
					
					Listener(cData, rect[j]);
				}								
			});
}

//window가 load되면 initialize 함수 실행
google.maps.event.addDomListener(window, 'load', initialize);