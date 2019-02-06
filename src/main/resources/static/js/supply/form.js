console.log("aaa");
$(function ($) {
    var addUnitButton = $("#addUnits");
    console.log(addUnitButton);
    var nr = 0;
    addUnitButton.on('click', function (e) {
        nr++;
        e.preventDefault();
        $('#supplyUnitObject')
            .append('<tr th:object="${supplyUnit}"><td>Price: <input type="number" th:value="" th:field="*{prod.price}"/></td>\n'+'Amount: <input type="number" th:value="" th:field="*{product.amount}"/></td></tr>') + '</br>';


    })
});

