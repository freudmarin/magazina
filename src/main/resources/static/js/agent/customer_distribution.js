$(function ($) {
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
        getAllSuppliers(map);

    }

    function getAllSuppliers(map) {
        $.ajax({
            url: "/get/suppliers",
            type: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            data: ko.toJSON(self).toString(),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                console.log(result);
            }
        });
    }


    google.maps.event.addDomListener(window, 'load', initialize);


});