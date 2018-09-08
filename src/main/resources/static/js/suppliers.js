$(document).ready(function () {
   $(".del-btn").on("click", function (event) {
        let supplierRow = $(event.target).parent().parent().parent();
        let supplierName = supplierRow.find("#supplier-name").text();

        return confirm(`Do you really want to delete supplier '${supplierName}'?`);
   });
});