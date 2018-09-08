$(document).ready(function () {
   $(".del-btn").on("click", function (event) {
        let partName = $(event.target).parent().parent().parent().find(".partName").text();
        return confirm(`Do you really want to delete part '${partName}'?`);
   });
});