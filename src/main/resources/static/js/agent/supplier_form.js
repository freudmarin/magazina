var map;
var myCenter = new google.maps.LatLng(41.3275, 19.8187);

function initialize() {
    var mapProp = {
        center: myCenter,
        zoom: 5,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });
    map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
    google.maps.event.addListener(map, 'click', function (event) {
        removeMarker(event.latLng, marker);
        getPosition(event.latLng['lat'](), event.latLng['lng'](), marker, map);
    });
}

function getPosition(lat, lng, marker, map) {
    document.getElementById("latitude").value = lat.toFixed(6);
    document.getElementById("longitude").value = lng.toFixed(6);

    var location = {lat: lat, lng: lng};
    marker.setPosition(location);
    marker.setMap(map)


}

function removeMarker(location, marker) {
    marker.setMap(null);
}

google.maps.event.addDomListener(window, 'load', initialize);