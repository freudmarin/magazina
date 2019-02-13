$(function ($) {

    getTopProducts();
    function getTopProducts() {
        $.ajax({
            url: "/get/top/products/",
            type: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            data: ko.toJSON(self).toString(),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                console.log(result)
            }
        });

    }



    var ctx = document.getElementById("myChart");
    var myDoughnutChart = new Chart(ctx, {
        type: 'doughnut',
        data: data,
        options: options
    });

    data = {
        datasets: [{
            data: [10, 20, 30]
        }],

        labels: [
            'Red',
            'Yellow',
            'Blue'
        ]
    };

});