$(() => {
    let heading = $("#heading");
    let brandSelector = $("#brand-selector");
    let carRows = $("#car-rows");

    $("#show-btn").on("click", displayCarsByBrand);

    function displayCarsByBrand() {
        let brand = brandSelector.val();

        if (brand === "") {
            return;
        }

        let request = {
            method: "GET",
            url: `http://localhost:8080/cars/${brand}`
        };

        $.ajax(request).then(displayCars);

        function displayCars(carsData) {
            carRows.hide();
            carRows.empty();

            for (let i = 0; i < carsData.length; i++) {
                let car = carsData[i];
                carRows.append($("<tr>")
                    .append($(`<td>${i + 1}</td>`))
                    .append($(`<td>${car.make}</td>`))
                    .append($(`<td>${car.model}</td>`))
                    .append($(`<td>${car.travelledDistance} km</td>`))
                    .append($(`<td>${car.price} &euro;</td>`))
                    .append($("<td>")
                        .append($(`<a class="btn btn-info float-right" href="/cars/${car.id}/parts">Parts Details</a>`))));
            }

            heading.text(`${carsData[0].make} Cars`);
            carRows.fadeIn();
        }
    }
});