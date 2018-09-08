$(() => {

    // --- DECLARE DOM ELEMENTS ---

    // Brands
    let brands = $("#brands");
    let brandSelectorDiv = $("#brand-selector-div");
    let brandSelector = $("#brand-selector");
    let brandDiv = $("#brand-div");
    let brand = $("#brand");
    let brandError = $("#brand-error");

    // Models
    let models = $("#models");
    let modelSelectorDiv = $("#model-selector-div");
    let modelSelector = $("#model-selector");
    let modelDiv = $("#model-div");
    let model = $("#model");
    let modelError = $("#model-error");
    let modelSmallElement = $("#model-small");

    // Suppliers
    let supplierSelector = $("#supplier-selector");

    // Parts
    let parts = $("#parts");
    let partSelector = $("#part-selector");
    let selectedParts = $("#selected-parts");

    // ATTACH EVENT LISTENERS
    brandSelector.on("change", processBrandChange);
    modelSelector.on("change", processModelChange);
    supplierSelector.on("change", processSupplierChange);

    $("#add-btn").on("click", addPart);
    $("#show-select-brand-link").on("click", displayBrandSelector);
    $("#show-brand-input-link").on("click", displayNewBrandInput);
    $("#show-select-model-link").on("click", displayModelSelector);
    $("#show-model-input-link").on("click", displayNewModelInput);
    $("#cancel-btn").on("click", () => history.back());

    processCarFormAfterFailedSubmit();

    // ACTIONS
    function processBrandChange() {
        let selectedBrand = brandSelector.val();

        if (selectedBrand === "") {
            models.fadeOut();
            return;
        }

        brandError.hide();
        modelError.hide();
        brand.val(selectedBrand);

        let request = {
            method: "GET",
            url: `http://localhost:8080/cars/${selectedBrand}/models`
        };

        $.ajax(request).then(displayModels);

        function displayModels(modelsData) {
            modelSelector.empty();

            for (let i = 0; i < modelsData.length; i++) {
                let currentModel = modelsData[i];
                modelSelector.append($(`<option value='${currentModel}'>${currentModel}</option>`))
            }

            model.val(modelSelector.val());
            models.fadeIn();
        }
    }

    function processModelChange() {
        model.val(modelSelector.val());
    }

    function processSupplierChange() {
        let supplierId = supplierSelector.val();

        if (supplierId === "") {
            parts.fadeOut();
            return;
        }

        let request = {
            method: "GET",
            url: `http://localhost:8080/parts/${supplierId}/suppliers`
        };

        $.ajax(request).then(displayParts);

        function displayParts(partsData) {
            partSelector.empty();
            for (let i = 0; i < partsData.length; i++) {
                partSelector.append($(`<option value="${partsData[i].id}">${partsData[i].name}</option>`));
            }
            parts.fadeIn();
        }
    }

    function addPart() {
        let partId = partSelector.val();
        let text = " " + partSelector.find(`option[value='${partId}']`).text() + " ";

        let selectedPartsCheckboxes = selectedParts.find("input");
        for (let i = 0; i < selectedPartsCheckboxes.length; i++) {
            let currentPartId = $(selectedPartsCheckboxes[i]).val().split(" ")[0];
            if (partId === currentPartId) {
                alert(`Part '${text.trim()}' is already selected!`);
                return;
            }
        }

        let partRow = $("<div class='row' style='display: none'>")
            .append($("<div class='col-8'>")
                .append($("<input type='checkbox' name='parts' checked>").val(partId + " " + text.trim()))
                .append($("<span>").text(text)))
            .append($("<div class='col-4 text-right'>")
                .append($("<a class='btn btn-light btn-sm'>Remove</a>")
                    .on("click", removePart)));

        selectedParts.append(partRow);
        partRow.fadeIn();
    }

    function removePart() {
        let elementToRemove = $(event.target).parent().parent();
        elementToRemove.fadeOut(1000, () => elementToRemove.remove());
    }

    function displayBrandSelector() {
        brandDiv.hide();
        modelDiv.hide();

        brand.val("");
        model.val("");

        brandSelector.val("");
        brands.show();
    }

    function displayNewBrandInput() {
        brands.hide();
        brandError.hide();
        models.hide();
        modelError.hide();

        brand.val("");
        model.val("");

        brands.find("option:selected").removeAttr("selected");
        brands.find("option[value='']").attr("selected", "selected");

        brandDiv.show();
        modelSmallElement.hide();
        modelDiv.show();
    }

    function displayModelSelector() {
        modelDiv.hide();
        if (!modelSelector || modelSelector.text().trim() === "") {
            processBrandChange();
        }

        models.show();
    }

    function displayNewModelInput() {
        models.hide();
        model.val("");
        brands.is(":visible") ? modelSmallElement.show() : modelSmallElement.hide();
        modelDiv.show();
    }

    function processCarFormAfterFailedSubmit() {
        if (brands.is(":visible") && brandError.text() !== "") {
            brandSelectorDiv.append(brandError);
            return;
        }

        let newBrand = $("#new-brand");
        if (newBrand && newBrand.text() !== "") {
            brands.hide();
            modelSmallElement.hide();
            brand.val(newBrand.text());
            brandDiv.show();
            modelDiv.show();

            return;
        }

        let selectedBrand = $("#selected-brand");
        if (selectedBrand && selectedBrand.text() !== "") {
            brands.hide();
            brandSelector.val(selectedBrand.text());
            brands.show();
            modelDiv.show();

            return;
        }

        if (models.is(":visible") && modelError.text() !== "") {
            modelSelectorDiv.append(modelError);
        }
    }
});