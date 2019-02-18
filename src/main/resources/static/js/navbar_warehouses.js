
$(function () {
    // getWarehouses();
    function getWarehouses() {
        $.ajax({
            url: "/get/warehouses",
            type: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                var result = result['result'];
                console.log(result);
            }
        });
    }
});