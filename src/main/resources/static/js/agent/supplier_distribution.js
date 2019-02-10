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
        map = new google.maps.Map(document.getElementById("map"), mapProp);
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
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                JSON.parse(result['res']).forEach(function (supplier) {

                    // if (supplier['latitude'].length > 0 && supplier['longitude'].length > 0) {
                        var latlng = new google.maps.LatLng(supplier['latitude'],supplier['longitude']);
                        var marker = new google.maps.Marker({
                            position: latlng ,
                            map: map
                        });
                    // }

                });
                console.log(JSON.parse(result['res']));
            }
        });
    }


    google.maps.event.addDomListener(window, 'load', initialize);


});