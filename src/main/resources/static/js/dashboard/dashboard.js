
$(function () {
   getData();
   function getData() {
       $.ajax({
           url: "/get/supplies",
           type: "GET",
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json',
               'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
           },
           contentType: "application/json; charset=utf-8",
           success: function (result) {
               var result = result['result'];
               chart(result);
               console.log(result);
               // result.forEach(function (customer) {
               //     console.log(customer);
               //     sale.customers.push(customer);
               // });
           }
       });
   }
});

function chart(ajaxdata){
    google.charts.load('current', {'packages':['corechart']});
    google.charts.load('current', {'packages':['line']});
    google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        ajaxdata = Object.values(ajaxdata);
        ajaxdata[0].pop();
        // var data = google.visualization.arrayToDataTable(ajaxdata);
        var data = google.visualization.arrayToDataTable([
            ['Produkt', '00001', '00002', '00003'],
            ['Molle', 40, 5, 0],
            ['Dardha', 25, 10, 70],
            ['Fanta Ekzotike', 5, 50, 0],
            ['Coca-cola', 90, 50, 0],
            ['Pepsi', 25, 0, 80]
        ]);

        var options = {
            legend: { position: 'bottom' },
            backgroundColor: '#fff',
            interpolateNulls: true
        };

        var chart = new google.charts.Bar(document.getElementById('chart'));
        chart.draw(data, google.charts.Bar.convertOptions(options));
    }
}