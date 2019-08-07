

function search(cData, queryWindow){
	//R-tree_Search_pseudo_code 그대로 프로그래밍.
	//각 pseudo_code에 해당하는 부분을 주석으로 라인마다 표시
	//n <- T 
	//if( n == leaf node)

	if(cData[0].level == 1 ){
		//for(i=0; i<node.rectangles.size(); i++)
		for(var i=0; i<cData.length; i++){
			//if(checkIntersection(node.rectangles.get(i), queryWindow, true))
			if(checkIntersection(cData[i], queryWindow, true)){
				//result.add(node.rectangles.get(i))
				queryResult[qIndex] = cData[i];
				qIndex++;
			}
		}
	}
	else{
		//for(i=0; i<node.rectangles.size(); i++)
		for(var i=0; i<cData.length; i++){
			//if(checkIntersection(node.rectangles.get(i), queryWindow, false))
			if(checkIntersection(cData[i], queryWindow, false)){
				//result = Search(((NonLeafNode)node).childRectangles.get(i), queryWindow, result)
				search(cData[i].child, queryWindow);
			}
		}
	}
	
	//return result
}

function checkIntersection(rectangle, queryWidow, leafFlag){
	if(leafFlag){
		if((queryXl <= rectangle.Xl) &&(queryXh >= rectangle.Xh) && (queryYl <= rectangle.Yl) && (queryYh >= rectangle.Yh)){
			return true;
		}
		else{
			return false;
		}
	}
	else{
		if( ((rectangle.Xl <= queryXl) && (rectangle.Xh <= queryXl)) || ((queryXh <= rectangle.Xl) && (queryXh <= rectangle.Xh)) ){
			return false;
		}
		else if(((rectangle.Yl <= queryYl) && (rectangle.Yh <= queryYl)) || ((queryYh <= rectangle.Yl) && (queryYh <= rectangle.Yh))){
			return false;
		}
		else{
			return true;
		}
	}
}