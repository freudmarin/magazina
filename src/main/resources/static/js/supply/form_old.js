$(function ($) {
    getProducts();
    getSuppliers();

    function getProducts() {
        $.ajax({
            url: "/get/suppliers/products",
            type: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
            },
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                var result = result['res'];
                result.forEach(function (product) {
                    vm.ptus().forEach(function (ptu) {
                        ptu.products.push({id: product.id, name: product.name, mu: product['measurinUnit']})
                        ptu.measuringUnit(product['measurinUnit']);
                        ptu.productId(product.id)

                    });
                    changeValues();

                });
            }
        });
    }

    function getSuppliers() {
        $.ajax({
            url: "/get/all/suppliers",
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
                result.forEach(function (supplier) {
                    console.log(supplier);
                    vm.suppliers.push(supplier);
                });
            }
        });
    }

    var SupplyModel = function () {
        var self = this;
        self.date = ko.observable().extend({
                required: {message: 'Zgjidhni nje date'}
            }
        );
        ;
        self.invoiceNumber = ko.observable().extend(
            {maxLength: 12},
            {required: {message: 'Gjatesia e barkodit eshte 12'}}
        );
        self.ptus = ko.observableArray([]);
        self.ptus.push(new SupplyUnit());
        self.suppliers = ko.observableArray([]);
        self.supplier = ko.observable();
        self.total = ko.computed(function () {
            self.ptus().forEach(function (ptu) {
                var sum = 0;
                sum += ptu.amount() * ptu.price();
                console.log(sum);
                return sum;
            })
        });
        self.addSupplyUnit = function () {
            self.ptus.push(new SupplyUnit());
            getProducts();
            changeValues();

        };

        self.save = function () {
            if (self.invoiceNumber.length <= 0) {
                alert("Fusha fature eshte e nevojshme!")

            } else if (self.date().length == 0) {
                alert("Data eshte nje fushe  nevojshme!")
            } else {
                $.ajax({
                    url: "/supplies/add",
                    type: "POST",
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

        }


    };

    var SupplyUnit = function () {
        var self = this;
        self.amount = ko.observable(0);
        self.price = ko.observable(1);
        self.product = ko.observable();
        self.measuringUnit = ko.observable('');
        self.products = ko.observableArray([]);
        self.productId = ko.observable();
        self.totalAmount = ko.computed(function () {
            return self.amount() * self.price();
        });


    };
    vm = new SupplyModel();

    function changeValues() {
        vm.ptus().forEach(function (ptu) {
            ptu.product.subscribe(function (obj, e) {
                ptu.measuringUnit(obj["mu"]);
                var id = obj['id'] + "";
                ptu.productId(id);
            })
        });
    }

    ko.applyBindings(vm);


});
