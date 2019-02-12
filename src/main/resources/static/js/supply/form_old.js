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
                var results = result['res'];
                console.log(result);
                results.forEach(function (product) {
                    console.log(product);
                    vm.ptus().forEach(function (ptu) {
                        ptu.products.push({id: product.id, name: product.name, mu: product['measurinUnit']});
                        // ptu.measuringUnit(product['measurinUnit']);
                        // ptu.productId(product.id +"");
                        // console.log("ptu " + ptu);

                    });
                    changeValues();

                });
            }
        });
    }

    function getSuppliers() {
        $.ajax({
            url: "/get/all/customers",
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
        self.date = ko.observable();
        self.invoiceNumber = ko.observable();
        self.ptus = ko.observableArray([]);
        self.ptus.push(new SupplyUnit());
        self.suppliers = ko.observableArray([]);
        self.supplier = ko.observable();
        self.supplyTotal = ko.pureComputed(function () {
            var sum = 0;
            self.ptus().forEach(function (ptu) {
                console.log(ptu);
                sum += ptu.amount() * ptu.price();
            }, self);
            return sum + " Lek";
        });
        self.remove = function (obj, e) {
            if (self.ptus().length > 1) {
                self.ptus.remove(this)
            } else {
                alert("Ju duhet te pakten nje produkt per furnizimin!");
            }

        };
        self.addSupplyUnit = function () {
            self.ptus.push(new SupplyUnit());
            getProducts();
            changeValues();


        };


        self.save = function () {
            if (self.invoiceNumber().length == 0) {
                alert("Fusha fature eshte e nevojshme!")

            } else if (self.date() == null) {
                alert("Data eshte nje fushe  nevojshme!")
            } else {
                console.log(self);
                console.log("ptus " + self.ptus());
                $.ajax({
                    url: "/supplies/create",
                    type: "POST",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
                    },
                    data: ko.toJSON(self).toString(),
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        window.location.replace("http://localhost:8080/supplies");
                    }
                });

            }

        }


    };

    var SupplyUnit = function () {
        var self = this;
        self.amount = ko.observable(1);
        self.price = ko.observable(0);
        self.product = ko.observable();
        self.measuringUnit = ko.observable("---");
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
                console.log(obj);
                console.log(e);
                if (obj != null) {
                    ptu.measuringUnit(obj["mu"]);
                    var id = obj['id'] + "";
                    ptu.productId(id);
                } else {
                    ptu.measuringUnit("---");
                    ptu.productId("");
                }

            })
        });
    }

    ko.applyBindings(vm);


});
