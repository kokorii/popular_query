var map;
function initialize() {
  map = new google.maps.Map(document.getElementById('map-canvas'), {
    zoom: 4,
    center: {lat: -28, lng: 137.883}
  });

  // Load GeoJSON.
  map.data.loadGeoJson('https://storage.googleapis.com/maps-devrel/google.json');

  // Color each letter gray. Change the color when the isColorful property
  // is set to true.
  map.data.setStyle(function(feature) {
    var color = 'gray';
    if (feature.getProperty('isColorful')) {
      color = feature.getProperty('color');
    }
    return ({fillColor: color, strokeColor: color, strokeWeight: 2});
  });

  // When the user clicks, set 'isColorful', changing the color of the letters.
  map.data.addListener('click', function(event) {
    event.feature.setProperty('isColorful', true);
  });
}

google.maps.event.addDomListener(window, 'load', initialize);