// example marker:
marker = new google.maps.Marker({
                    map: map, 
                    position: new google.maps.LatLng(-20.3,30.3)
                });)


// add the double-click event listener
google.maps.event.addListener(marker, 'dblclick', function(event){
    map = marker.getMap();

    map.setCenter(overlay.getPosition()); // set map center to marker position
    smoothZoom(map, 12, map.getZoom()); // call smoothZoom, parameters map, final zoomLevel, and starting zoom level
})


// the smooth zoom function
function smoothZoom (map, max, cnt) {
    if (cnt >= max) {
            return;
        }
    else {
        z = google.maps.event.addListener(map, 'zoom_changed', function(event){
            google.maps.event.removeListener(z);
            smoothZoom(map, max, cnt + 1);
        });
        setTimeout(function(){map.setZoom(cnt)}, 80); // 80ms is what I found to work well on my system -- it might not work well on all systems
    }
}  