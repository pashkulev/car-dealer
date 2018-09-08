$(() => {

    let customersTable = $("#customers");
    let salesDetailsView = $("#sales");

    let customerName = $("#customer-name");
    let boughtCarsCount = $("#cars-count");
    let totalSpentMoney = $("#total-spent-money");

    $(window).resize(function(){
        customersTable.css("height", (window.innerHeight - 200) + "px");
    });

    $(".view-sales-btn").on("click", loadSalesDetails);
    $("#close-btn").on("click", closeSalesDetails);

    function loadSalesDetails(event) {
        let customerId = $(event.target).parent().parent().attr("data-id");

        let request = {
            method: "GET",
            url: "http://localhost:8080/customers/" + customerId
        };

        $.ajax(request).then(displaySalesDetails);

        function displaySalesDetails(salesDetails) {
            customerName.text(salesDetails.name);
            boughtCarsCount.text(salesDetails.boughtCarsCount);
            totalSpentMoney.text(salesDetails.totalSpentMoneyOnCars.toFixed(2) + " €");

            customersTable.removeClass("col-12").addClass("col-7 col-sm-8 col-md-9");
            salesDetailsView.find("td").addClass("text-center");
            salesDetailsView.show();
        }
    }

    function closeSalesDetails() {
        salesDetailsView.hide();
        customersTable.removeClass("col-7 col-sm-8 col-md-9").addClass("col-12");
    }
});