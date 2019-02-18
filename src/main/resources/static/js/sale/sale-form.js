$(function ($) {
    getProducts();
    getCustomers();

    function getProducts() {
        $.ajax({
            url: "/get/sale/products",
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
                    var last = sale.ptus()[sale.ptus().length - 1];
                    last.products.push({id: product.id, name: product.name, mu: product['measuringUnit'], amount: product['amount']});
                    sale.ptus().forEach(function (ptu) {
                        last.measuringUnit(product['measuringUnit']);
                        last.availableAmount(product['amount']);
                        last.productId(product.id +"");
                    });

                    changeValues();
                });
            }
        });
    }

    function getCustomers() {
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
                result.forEach(function (customer) {
                    console.log(customer);
                    sale.customers.push(customer);
                });
            }
        });
    }

    var SaleModel = function () {
        var self = this;
        self.date = ko.observable();
        self.invoiceNumber = ko.observable();
        self.ptus = ko.observableArray([]);
        self.ptus.push(new SaleUnit());
        self.customers = ko.observableArray([]);
        self.customer = ko.observable();
        self.saleTotal = ko.pureComputed(function () {
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
                alert("Ju duhet te pakten nje produkt per te shitur!");
            }

        };
        self.addSaleUnit = function () {
            self.ptus.push(new SaleUnit());
            getProducts();
            changeValues();
        };

        self.save = function () {
            if (self.invoiceNumber() == null) {
                alert("Fusha fature eshte e nevojshme!")

            } else if (self.date() == null) {
                alert("Data eshte nje fushe  nevojshme!")
            } else {
                $.ajax({
                    url: "/sales/create",
                    type: "POST",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
                    },
                    data: ko.toJSON(self).toString(),
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        window.location.replace("http://localhost:8080/sales");
                    }
                });

            }

        }

        // ko.toJSON(self).toString()
    };

    var SaleUnit = function () {
        var self = this;
        self.amount = ko.observable(1);
        self.availableAmount = ko.observable(0);
        self.price = ko.observable(1);
        self.product = ko.observable();
        self.measuringUnit = ko.observable("---");
        self.products = ko.observableArray([]);
        self.productId = ko.observable();
        self.totalAmount = ko.computed(function () {
            return self.amount() * self.price();
        });
    };
    sale = new SaleModel();

    function changeValues() {
        sale.ptus().forEach(function (ptu) {
            ptu.product.subscribe(function (obj, e) {
                console.log(obj);
                console.log(e);
                if (typeof obj === "undefined") {
                    ptu.measuringUnit("---");
                    ptu.productId("");
                    ptu.availableAmount(0);
                } else {
                    ptu.measuringUnit(obj["mu"]);
                    var id = obj['id'] + "";
                    ptu.productId(id);
                    ptu.availableAmount(obj['amount']);
                }
            });
        });
    }

    validateForm();

    function validateForm() {
        sale.ptus().forEach(function (ptu) {
            ptu.amount.subscribe(function (obj) {
                if (obj > ptu.availableAmount()) {
                    alert("Sasia e vendosur e tejkalon sasine ne magazine!")
                }
            })

        })
    }

    ko.applyBindings(sale);


});
