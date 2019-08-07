//global variable declaration


//stroke, fill : 사각형의 색깔 정보
var stroke, fill;

//zoomLevel : 초기 zoom Level.
var zoomLevel = 7;

//사용자가 질의한 사각형에 대한 결과 MBR들을 저장하는 배열
var queryResult = new Array();
//queryResult의 index
var qIndex = 0;

//query Window 속성 정보를 표현
var queryWindow;
var HighCoord,LowCoord;
var queryXh, queryYh;
var queryXl, queryYl;