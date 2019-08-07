
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

	var seoul = new google.maps.LatLng(37.6, 127);
	//var center = new google.maps.LatLng(37.53539, 126.99524);
	var map = reMap();
	
	var centerX = ((tree[0].mXl+tree[0].mXh)/2);
	var centerY = ((tree[0].mYl+tree[0].mYh)/2);
	var newCenter = reCentering(centerX, centerY);
	map.setCenter(newCenter);
	
	drawingTool(tree, map);	  

	var MBRrect = new Array();
	var MIRrect = new Array();
	
	for(var i=0; i<tree.length; i++){			
	
		var mbr = getMyMBR(tree, i);
		
		//mbr color
		stroke = fill = "#FFA873";
		MBRrect[i] = drawMBR(map, mbr, stroke, fill);
		
		var mir = getMyMIR(tree, i);
		
		//mir color
		stroke = fill = "#FF5E00";
		MIRrect[i] = drawMIR(map, mir, stroke, fill);
		
		var cData = tree[i];

		Listener(cData, MBRrect[i]);
		//Listener(cData, MIRrect[i]);
		
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
				
				var MIRarray = new Array();
				var MBRarray = new Array();
				
				for(var j=0; j<Data.child.length; j++){
					
					var mbr = getChildMBR(Data, j);
					
					//mbr color
					stroke = fill = "#FFA873";
					
					MBRarray[j] = drawMBR(map, mbr, stroke, fill);
					
					
					if(Data.level != 1){
						//mir color
						stroke = fill = "#FF5E00";
						
						var mir = getChildMIR(Data, j);
						 
						MIRarray[j] = drawMIR(map, mir, stroke, fill);
					}

					var cData = Data.child[j];
					
					Listener(cData, MBRarray[j]);
					
					//click의 대상이 MIR로 변경되면
					//마지막에 보여지는 사각형은 MBR임으로
					//Leaf Node임을 알려주는 alert listener를 따로 붙여주어야 한다.
					//alertListener(cData, MBRarray[j]);
				}								
			});
}

function alertListener(Data, clickedRect){

	google.maps.event.addListener(clickedRect, 'click', 
			
			function(){ 
				
				if(Data.level <= 1){
					alert("this is leaf node\n");
					return;
				}
							
			});

}

//window가 load되면 initialize 함수 실행
google.maps.event.addDomListener(window, 'load', initialize);