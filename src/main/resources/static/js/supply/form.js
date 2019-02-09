console.log("aaa");
$(function ($) {

    var ptus = [];
    var addUnitButton = $("#addUnits");
    console.log(addUnitButton);
    addUnitButton.on('click', function (e) {
        e.preventDefault();
        console.log("done");
        // $('#supplyUnitObject')
        //     .append('<tr ><td>' +
        //         '        <label>Produkti:</label>\n' +
        //         '                <select>\n' +
        //         '                <option th:each="product : ${products}"\n' +
        //         '                th:value="${product.id}"\n' +
        //         '                th:text="${product.name}"/>\n' +
        //         '                </select>\n' +
        //         '            </td>Price: <input type="number"/></td>\n' +
        //         '            <td> Amount: <input type="number"/></td></tr>') + '</br>';
        //

        // });
    });
});
