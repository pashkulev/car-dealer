$(() =>  {
    $(".nav-item").removeClass("active");
    let contextPath = window.location.pathname;

    let menuPath;
    if (contextPath.lastIndexOf("/") > 0) {
        menuPath = contextPath.substring(1, contextPath.indexOf("/", 1));
    } else {
        menuPath = contextPath.substring(1);
    }

    switch (menuPath) {
        case "": $("#home").addClass("active"); break;
        case "customers": $("#customersMenu").addClass("active"); break;
        case "sales": $("#salesMenu").addClass("active"); break;
        case "cars":
            $("#carsMenu").addClass("active");
            $("#carsLink").addClass("active");
            break;
        case "parts":
            $("#partsMenu").addClass("active");
            $("#partsLink").addClass("active");
            break;
        case "suppliers": $("#suppliersMenu").addClass("active"); break;
    }
});