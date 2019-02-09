console.log("aaa");
$(function ($) {

    var SupplyModel = function () {
        var self = this;
        self.date = ko.observable();
        self.invoiceNumber = ko.observable();
        self.ptus = ko.observableArray([]);
        self.ptus.push(new SupplyUnit());
        self.addSupplyUnit = function () {
            console.log("aaa");
            console.log(new SupplyUnit());
            self.ptus.push(new SupplyUnit());
        };

        self.save = function () {
            console.log("ssssavva");
            console.log(ko.toJSON(self));

            $.ajax({
                url: "/supplies/add",
                type: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
                },
                data: ko.toJSON(vm),
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function (result) {
                    console.log("result");
                }
            });
        }

    };

    var SupplyUnit = function () {
        var self = this;
        self.amount = ko.observable();
        self.price = ko.observable();
        self.product = ko.observable();
    };
    vm = new SupplyModel();

    ko.applyBindings(vm);

    // function getAllProducts() {
    //     $.ajax({
    //         url: "/supplies/get/products",
    //         type: "GET",
    //         headers: {
    //             'Accept': 'application/json',
    //             'Content-Type': 'application/json',
    //             'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
    //         },
    //         data: ko.toJSON(vm),
    //         contentType: "application/json; charset=utf-8",
    //         async: false,
    //         success: function (result) {
    //             console.log("result");
    //         }
    //     });

    // }

});
